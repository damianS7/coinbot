/*
 * Copyright (C) 2013 by danjian <josepwnz@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.coinbot.core;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.coinbot.captcha.Captcha;
import com.coinbot.captcha.CaptchaDetector;
import com.coinbot.captcha.CaptchaService;
import com.coinbot.captcha.CaptchaTimer;
import com.coinbot.database.CaptchaHash;
import com.coinbot.exceptions.UnrecognizedCaptcha;
import com.coinbot.faucet.Claim;
import com.coinbot.proxy.Proxy;
import com.coinbot.ui.WorkerPanel;

/**
 * Esta clase hace todo el trabajo de claim
 * @author danjian
 */
public class Worker implements Runnable {
	private int workerId = 0;
	private WorkerPanel workerPanel;
	private Thread thread;
	private Proxy proxy;
	private List<Claim> faucets;
	
	public Worker(int workerId) {
		this.workerId = workerId;
		this.workerPanel = new WorkerPanel(Integer.toString(workerId));
		thread = new Thread(this);
	}
	
	public int getWorkerId() {
		return workerId;
	}
	
	public boolean hasFinished() {
		return thread.isInterrupted();
	}
	
	public void stop() {
		thread.interrupt();
	}
	
	public void start() {
		thread.start();
	}
	
	@Override
	public void run() {
		File pathToBinary = new File("/home/jian/Descargas/firefox46/bin/firefox");
		// Firefox 46 needed
		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
		FirefoxProfile profile = new FirefoxProfile();
		FirefoxDriver driver = new FirefoxDriver(ffBinary, profile);
		CoinbotApplication.ui.workerQueue.addWorker(workerPanel);
		
		while(CoinbotApplication.bot.isRunning()) {
			// Sacamos un "claim" de la cola
			Claim claim = CoinbotApplication.bot.getClaimQueue().next();
			if(claim == null) {
				continue;
			}
			
			claim.getPanel().reset();
			claim.getPanel().getProgressBar().setMaximum(10);
			workerPanel.addClaim(claim.getPanel());
			claim.getPanel().nextStep("Opening URL");
			
			try {
				driver.manage().timeouts().pageLoadTimeout(12, TimeUnit.SECONDS);
				driver.navigate().to(new URL(claim.getFaucet().getUrl()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				continue;
			} catch (TimeoutException e) {
				// Busca un elemento, si no lo encuentra que vuelva a cargar
				e.printStackTrace();
			}
			
			// Detectando captcha
			claim.getPanel().nextStep("Detecting Captcha");
			CaptchaDetector captchaDetector = new CaptchaDetector();
			CaptchaService captcha = null;
			
			try {
				captcha = captchaDetector.find(driver, 
						driver.findElement(By.tagName("body")));
			} catch(NoSuchElementException ex) {
				ex.printStackTrace();
				claim.getPanel().nextStep("Body not found!");
				continue;
			} catch(UnrecognizedCaptcha ex) {
				ex.printStackTrace();
				claim.getPanel().nextStep("Captcha not recognized.");
				continue;
			} catch(Exception ex) {
				continue;
			}
			
			claim.getPanel().nextStep("Trying auto resolving");
			// Intentamos buscar el hash en la DB 
			CaptchaHash ch = new CaptchaHash(captcha);
			String answer = CoinbotApplication.captchaDatabase.getAnswer(ch.getHash());
			
			// Si no enctramos la respuesta en la bd se la pedimos al usuario
			if(answer == null) {
				claim.getPanel().nextStep("Waiting for captcha answer ...");
				
				// Encolar captcha
				CoinbotApplication.bot.getCaptchaQueue().toQueue(captcha);
				
				// Esperar la resolucion del captcha
				CaptchaTimer timer = new CaptchaTimer(captcha, 35);
				timer.start();
				
				// Esperamos a la resolucion
				while(!timer.isExpired() && !captcha.resolved()) {
					CoinbotApplication.ui.captchaQueue.getCaptchaPanel(captcha)
					.setTimer(timer.getSecondsLeft());
				}
				
				if(!captcha.resolved()) {
					CoinbotApplication.captchaDatabase.addCaptcha(ch);
					continue;
				}
			}
			
			// Guardamos el captcha en la DB
			CoinbotApplication.captchaDatabase.addCaptcha(new CaptchaHash(captcha));
			
			// Y la imagen en un archivo
			try {
				ImageIO.write(captcha.getImage(), "png", 
						new File("coinbot/captchas/" + ch.getHash() + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Envia la respuesta al input
			captcha.answerToInput(driver);
			
			// Desencolar captcha
			CoinbotApplication.bot.getCaptchaQueue().deQueue(captcha);
			
			// Escribir address
			claim.getPanel().nextStep("Detecting input address");
			InputAddressDetector iad = new InputAddressDetector(driver);
			iad.insertAddress(claim.getBtcAddress());
			
			//claim.getPanel().nextStep("Detecting Antibot");
			// Detectar antibot (puzzle no soportado)
			// Resolver antibot
			
			// submit
			claim.getPanel().nextStep("Submiting ...");
			WebElement submit = driver.findElement(By.id("address"));
			submit.submit();
			
			claim.getPanel().nextStep("Checking response");
			//claim.getPanel().nextStep("Successfull claim!");
			/*WebElement out = null;
			
			try {
				out = driver.findElement(By.className("alert-success"));
			} catch (NoSuchElementException e) {
				
			}
			
			try {
				out = driver.findElement(By.className("alert-danger"));
			} catch (NoSuchElementException e) {
			}
			*/
			//claim.getPanel().nextStep("Failed claim!");
			
			claim.getPanel().done();
			workerPanel.removeClaim(claim.getPanel());
			claim.getTimer().done(2000, 1);
			CoinbotApplication.bot.getClaimQueue().toQueue(claim);
			
			try {
				Thread.sleep(25000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			driver.close();
		} catch (Exception e) {
			
		}
		CoinbotApplication.ui.workerQueue.removeWorker(workerPanel);
		System.out.println("Worker " + workerId + " end work!");
	}

}
