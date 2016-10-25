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


/**
 * Esta clase hace todo el trabajo para procesar los claims
 * @author danjian
 */
public class Worker implements Runnable {
	private boolean working = false;
	private int workerId = 0;
	private Thread thread;

	public Worker(int workerId) {
		this.workerId = workerId;
		thread = new Thread(this);
	}

	public int getWorkerId() {
		return workerId;
	}

	public boolean hasFinished() {
		return working;
	}

	public void stop() {
		working = false;
		thread.interrupt();
	}

	public void start() {
		working = true;
		thread.start();
	}

	@Override
	public void run() {
	}
}
/*
package com.coinbot.core;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.coinbot.antibot.AntibotPuzzle;
import com.coinbot.captcha.CaptchaService;
import com.coinbot.captcha.CaptchaTimer;
import com.coinbot.database.AntibotHash;
import com.coinbot.database.CaptchaHash;
import com.coinbot.detector.AntibotDetector;
import com.coinbot.detector.CaptchaDetector;
import com.coinbot.detector.FaucetDataDetector;
import com.coinbot.detector.ClaimResultDetector;
import com.coinbot.detector.InputAddressDetector;
import com.coinbot.exceptions.DetectionException;
import com.coinbot.exceptions.UnrecognizedCaptcha;
import com.coinbot.faucet.Claim;
import com.coinbot.ui.ClaimPanel;
import com.coinbot.ui.WorkerPanel;

public class Worker implements Runnable {
	private int workerId = 0;
	private WorkerPanel workerPanel;
	private Thread thread;

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
		File bin = new File("/home/jian/Descargas/firefox46/bin/firefox");
		FirefoxBinary ffBinary = new FirefoxBinary(bin);
		FirefoxProfile profile = new FirefoxProfile();
		FirefoxDriver driver = new FirefoxDriver(ffBinary, profile);
		CoinbotApplication.ui.workerQueue.addWorker(workerPanel);
		
		while (CoinbotApplication.bot.isRunning()) {
			// Sacamos un "claim" de la cola
			Claim claim = CoinbotApplication.bot.getClaimQueue().next();
			ClaimPanel cp = CoinbotApplication.ui.claimQueue.getClaimPanel(claim);
			if (claim == null) {
				continue;
			}
			
			cp.reset();
			cp.getProgressBar().setMaximum(10);
			workerPanel.addClaim(cp);
			cp.nextStep("Opening URL");

			try {
				driver.manage().timeouts()
						.pageLoadTimeout(12, TimeUnit.SECONDS);
				driver.navigate().to(new URL(claim.getFaucet().getUrl()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				continue;
			} catch (TimeoutException e) {
				// Busca un elemento, si no lo encuentra que vuelva a cargar
				e.printStackTrace();
			}

			// Detectando datos faucet
			cp.nextStep("Detecting faucet data");
			FaucetDataDetector fdd = new FaucetDataDetector(driver);
			
			try {
				fdd.detect();
			} catch (DetectionException e) {
				cp.finalStep("Faucet data detection failed!");
				continue;
			}
			
			// Comprueba que la faucet tenga suficiente balance
			if(fdd.getReward() > fdd.getBalance()) {
				cp.finalStep("Faucet dry.");
				continue;
			}
			
						
			// Detectando captcha
			cp.nextStep("Detecting Captcha");
			CaptchaDetector captchaDetector = new CaptchaDetector(driver);
			CaptchaService captcha = null;

			try {
				captchaDetector.detect();
				captcha = captchaDetector.getCaptcha();
			} catch (UnrecognizedCaptcha e) {
				cp.finalStep("Captcha not recognized.");
				continue;
			}
			
			// Intentamos buscar el hash en la DB
			cp.nextStep("Autoresolving captcha.");
			CaptchaHash ch = new CaptchaHash(captcha);
			captcha.setAnswer(CoinbotApplication.captchaDatabase.getAnswer(ch
					.getHash()));

			// Si el captcha no fue resuelto (answer is null)
			if(!captcha.resolved()) {
				// Si answer es null, no fue encontrado, probamos 2captcha
				if(CoinbotApplication.coinbotProperties.is2CaptchaEnabled()) {
					cp.nextStep("Trying 2Captcha ...");
				}
				
				// Si answer es null, no fue encontrado, probamos manualmente
				if (CoinbotApplication.coinbotProperties.isCaptchaQueueEnabled()) {
					cp.nextStep("Captcha to Queue ...");
					
					// Encolar captcha
					CoinbotApplication.bot.getCaptchaQueue().toQueue(captcha);
					
					// Esperar la resolucion del captcha
					CaptchaTimer timer = new CaptchaTimer(captcha, 35);
					timer.start();

					// Esperamos a la resolucion
					while (!timer.isExpired() && !captcha.resolved()) {
						CoinbotApplication.ui.captchaQueue.getCaptchaPanel(captcha)
								.setTimer(timer.getSecondsLeft());
					}
					
					// Desencolar captcha
					CoinbotApplication.bot.getCaptchaQueue().deQueue(captcha);
				}
				
				
				// Tanto si fue resuelto como si no, guardamos el captcha
				CoinbotApplication.captchaDatabase.addCaptcha(new CaptchaHash(
						captcha));
				
				// Y la imagen en un archivo
				try {
					ImageIO.write(captcha.getImage(), "png", new File(
							"coinbot/captchas/" + ch.getHash() + ".png"));
				} catch (IOException e) {
					
				}
			}

			// Si answer aun es null, pasamos a la siguiente pagina
			if(!captcha.resolved()) {
				cp.finalStep("Captcha resolution failed! ...");
				continue;
			}
			
			cp.nextStep("Captcha solved!");

			// Captcha resuelto, enviamos la  respuesta
			captcha.answerToInput(driver);
			
			cp.nextStep("Detecting antibot...");
			// Antibot detection
			AntibotDetector ad = new AntibotDetector(driver);
			try {
				ad.detect();
			} catch (DetectionException e) {
				cp.nextStep("No antibot detected.");
			}
			
			AntibotPuzzle ap = ad.getAntibot();
			// La faucet tiene antibot
			if(ap != null) {
				AntibotHash aph = new AntibotHash(ap);
				CoinbotApplication.antibotDatabase.getHash(aph.getPuzzleHash());
			}
			
			
			
			// Escribir address
			cp.nextStep("Detecting input address");
			
			try {
				InputAddressDetector iad = new InputAddressDetector(driver);
				iad.insertAddress(claim.getBtcAddress());
			} catch(NoSuchElementException e) {
				cp.nextStep("Address input not found.");
				continue;
			} catch(Exception e) {
				continue;
			}
			
			// cp.nextStep("Detecting Antibot");
			// Detectar antibot (puzzle no soportado)
			// Resolver antibot

			// Submit
			cp.nextStep("Submitting ...");
			try {
				WebElement submit = driver.findElement(By.id("address"));
				submit.submit();
			} catch(NoSuchElementException e) {
				e.printStackTrace();
				cp.nextStep("Submit failed!...");
				continue;
			} catch(Exception e) {
				continue;
			}

			cp.nextStep("Checking response");
			
			ClaimResultDetector fmd = new ClaimResultDetector(driver);
			// Si claim con exito...
			if(fmd.claimSuccess()) {
				cp.nextStep("Successfull claim!");
				claim.getTimer().done(fdd.getReward(), fdd.getTimer());
			} else {
				cp.nextStep("Unsuccessfull claim");
			}
			
			workerPanel.removeClaim(cp);
			cp.done();
			CoinbotApplication.bot.getClaimQueue().toQueue(claim);
			
			
			// dellll
			try {
				Thread.sleep(25000);
			} catch (InterruptedException e) {
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

*/