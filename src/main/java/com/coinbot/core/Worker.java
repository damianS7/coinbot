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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.coinbot.faucet.Claim;
import com.coinbot.proxy.Proxy;
import com.coinbot.ui.CaptchaPanel;
import com.coinbot.ui.ClaimPanel;
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
			Claim claim = CoinbotApplication.bot.getClaimQueue().next();
			if(claim == null) {
				continue;
			}
			
			claim.getPanel().reset();
			claim.getPanel().getProgressBar().setMaximum(5);
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
			
			claim.getPanel().nextStep("Opening URL");
			
			// Detectar captcha (throws captcha  no soportado)
			// Resolver captcha 
			// Detectar antibot (puzzle no soportado)
			// Resolver antibot
			// Detectar input address (input no encontrada)
			// poner address
			// submit
			// leer errores / notificaciones
			
			
			claim.getPanel().done();
			workerPanel.removeClaim(claim.getPanel());
			claim.getTimer().done(2000, 1);
			CoinbotApplication.bot.getClaimQueue().toQueue(claim);
			
		}
		
		try {
			driver.close();
		} catch (Exception e) {
			
		}
		CoinbotApplication.ui.workerQueue.removeWorker(workerPanel);
		System.out.println("Worker " + workerId + " end work!");
	}

}
