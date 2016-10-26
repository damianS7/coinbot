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
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.coinbot.antibot.Antibot;
import com.coinbot.captcha.CaptchaService;
import com.coinbot.detector.AntibotDetector;
import com.coinbot.detector.CaptchaDetector;
import com.coinbot.detector.ClaimDetector;
import com.coinbot.detector.FaucetDetector;
import com.coinbot.detector.InputAddressDetector;
import com.coinbot.exceptions.DetectionException;
import com.coinbot.exceptions.InputNotFoundException;
import com.coinbot.exceptions.InvalidAddressException;
import com.coinbot.exceptions.InvalidAntibotException;
import com.coinbot.exceptions.InvalidCaptchaException;
import com.coinbot.faucet.Claim;
import com.coinbot.ui.ClaimPanel;
import com.coinbot.ui.WorkerPanel;
import com.coinbot.utils.Image;


/**
 * Esta clase hace todo el trabajo para procesar los claims
 * @author danjian
 */
public class Worker implements Runnable {
	private WorkerPanel workerPanel;
	private boolean working = false;
	private int workerId = 0;
	private Thread thread;

	public Worker(int workerId) {
		this.workerId = workerId;
		this.workerPanel = new WorkerPanel(Integer.toString(workerId));
		thread = new Thread(this);
	}

	public WorkerPanel getPanel() {
		return workerPanel;
	}
	
	public int getWorkerId() {
		return workerId;
	}

	public boolean hasFinished() {
		if(!working || thread.isInterrupted()) {
			return true;
		}
		return false;
	}

	public void stop() {
		working = false;
		thread.interrupt();
	}

	public void start() {
		working = true;
		thread.start();
	}
	
	private void captchaResolve() {
		
	}

	@Override
	public void run() {
		File bin = new File(CoinbotApplication.browser);
		FirefoxBinary ffBinary = new FirefoxBinary(bin);
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("privacy.popups.disable_from_plugins", 3);
		profile.setPreference("browser.link.open_newwindow.restriction", 0);
		profile.setPreference("dom.popup_allowed_events", " ");
		profile.setPreference("dom.popup_maximum", 0);
		
		FirefoxDriver driver = new FirefoxDriver(ffBinary, profile);
		
		
		while (CoinbotApplication.bot.isRunning()) {
			// Sacamos un claim de la cola
			Claim claim = CoinbotApplication.bot.getClaimQueue().next();
			if (claim == null) {
				continue;
			}
			String name = claim.getFaucet().getName(); 
			
			ClaimPanel cp = claim.getPanel();
			cp.reset();
			cp.getBar().setMaximum(10);
			workerPanel.addPanel(cp);
			
			cp.nextStep("");
			CoinbotApplication.ui.log.append("(" + name + ")" + "Opening URL");

			try {
				driver.manage().timeouts()
						.pageLoadTimeout(12, TimeUnit.SECONDS);
				driver.navigate().to(new URL(claim.getFaucet().getUrl()));
			} catch (MalformedURLException e) {
				continue;
			} catch (TimeoutException e) {
				// Busca un elemento, si no lo encuentra que vuelva a cargar
			}
			
			// Detectando datos faucet
			cp.nextStep("");
			CoinbotApplication.ui.log.append("(" + name + ")" + "Detecting faucet data");
			FaucetDetector fd = new FaucetDetector(driver);

			try {
				fd.detect();
			} catch (DetectionException e) {
				cp.finalStep("");
				CoinbotApplication.ui.log.append("(" + name + ")" + "Faucet data detection failed!");
				continue;
			}
			
			// Faucet vacia
			if(fd.getBalance() < 1000) {
				cp.finalStep("");
				CoinbotApplication.ui.log.append("(" + name + ")" + "Faucet dry!");
				continue;
			}
			
			// Detectando captcha
			cp.nextStep("");
			CoinbotApplication.ui.log.append("(" + name + ")" + "Detecting captcha");
			CaptchaDetector captchaDetector = new CaptchaDetector(driver);

			try {
				captchaDetector.detect();
			} catch (DetectionException e) {
				cp.finalStep("");
				CoinbotApplication.ui.log.append("(" + name + ")" + "Captcha not recognized.");
				continue;
			}
			
			cp.nextStep("");
			CoinbotApplication.ui.log.append("(" + name + ")" + "Resolving captcha.");
			CaptchaService captcha = captchaDetector.getCaptcha();
			String captchaHash = Image.imageToHash(captcha.getImage());
			String answer = CoinbotApplication.captchaDatabase
					.getAnswer(captchaHash);
			//------
			// Captcha no encontrada en bd local
			if(answer == null) {
				// Si answer es null, no fue encontrado, probamos 2captcha
				if(CoinbotApplication.config.is2CaptchaEnabled()) {
					cp.nextStep("Trying 2Captcha ...");
				}
				
				// 2Captcha falla
				if(!captcha.isResolved()) {
					if(CoinbotApplication.config.isDeathByCaptchaEnabled()) {
						cp.nextStep("Trying DeathByCaptcha ...");
					}
				}
				
				// Si answer es null, no fue encontrado, probamos manualmente
				if (CoinbotApplication.config.isCaptchaQueueEnabled()) {
					cp.nextStep("Captcha to Queue ...");
					
					// Encolar captcha
					CoinbotApplication.bot.getCaptchaQueue().toQueue(captcha);
					
					// Esperar la resolucion del captcha
					//CaptchaTimer timer = new CaptchaTimer(captcha, 35);
					//timer.start();

					// Esperamos a la resolucion
					while (!captcha.isExpired() && !captcha.isResolved()) {
						CoinbotApplication.ui.captchaQueue.getCaptchaPanel(captcha)
								.setTimer(captcha.getTimer().getSeconds());
					}
					
					// Desencolar captcha
					CoinbotApplication.bot.getCaptchaQueue().deQueue(captcha);
				}
				
				// Tanto si fue resuelto como si no, guardamos el captcha
				CoinbotApplication.captchaDatabase.addCaptcha(captcha);
				
				// Y la imagen en un archivo
				try {
					ImageIO.write(captcha.getImage(), "png", new File(
							"coinbot/captchas/" + captchaHash + ".png"));
				} catch (IOException e) {
					
				}
			}
			
			// Si answer aun es null, pasamos a la siguiente pagina
			if(!captcha.isResolved()) {
				cp.finalStep("Captcha failed! ...");
				continue;
			}
						
			cp.nextStep("Captcha solved!");
			// Captcha resuelto, enviamos la  respuesta
			try {
				captcha.insertAnswer(driver, captcha.getAnswer());
			} catch (InputNotFoundException e) {
				cp.finalStep("Captcha input not found!");
				continue;
			}
			
			cp.nextStep("Detecting antibot...");
			// Antibot detection
			AntibotDetector ad = new AntibotDetector(driver);
			try {
				ad.detect();
			} catch (DetectionException e) {
				cp.nextStep("No antibot detected.");
				continue;
			}
			
			Antibot ap = ad.getAntibot();
			// La faucet tiene antibot
			if(ap != null) {
				String abHash = Image.imageToHash(ap.getImage());
				Antibot abr = CoinbotApplication.antibotDatabase
						.getAntibot(abHash);
				
				if(abr == null) {
					CoinbotApplication.ui.antibotQueue.addPanel(ap);
				} else {
					ap.setOrder(abr.getOrder());
					ap.resolve();
				}

				while (!ap.isExpired() && !ap.isResolved()) {
					CoinbotApplication.ui.antibotQueue.getPanel(ap)
					.setTimer(ap.getTimer().getSeconds());
				}
				
				CoinbotApplication.ui.antibotQueue.removePanel(ap);
				
				if(!ap.isResolved()) {
					cp.finalStep("Antibot not resolved");
					continue;
				}
			}
			
			if(ap.isResolved()) {
				ap.resolve();
			}
			
			// Escribir address
			cp.nextStep("Detecting input address");

			InputAddressDetector iad = new InputAddressDetector(driver);
			try {
				iad.detect();
			} catch (DetectionException e) {
				cp.nextStep("Address input not found.");
				continue;
			}
			
			iad.insertAddress(claim.getAddress().getAddress());
			
			
			// Submit
			cp.nextStep("Submitting ...");
			try {
				iad.getInput().submit();
			} catch (NoSuchElementException e) {
				cp.finalStep("Submit failed!");
				continue;
			}
			
			cp.nextStep("Checking response");

			ClaimDetector cd = new ClaimDetector(driver);
			try {
				cd.detect();
			} catch (InvalidCaptchaException e) {
				cp.finalStep("Captcha wrong solution");
				continue;
			} catch (InvalidAntibotException e) {
				cp.finalStep("Antibot wrong solution");
				continue;
			} catch (InvalidAddressException e) {
				cp.finalStep("Invalid address");
				continue;
			} catch (DetectionException e) {
				cp.finalStep("Unknow detect error.");
				continue;
			}
			
			
			if(cd.isClaimSuccessfull()) {
				cp.finalStep("Successfull claim!");
				
				// Actualizando stats
				CoinbotApplication.bot.getStats().updateBalance(
						claim.getFaucet().getCurrency(), fd.getReward());
				
				claim.getTimer().setMinutes(fd.getTimer());
				claim.getTimer().start();
			}
			
			cp.done();
			workerPanel.removePanel(cp);
			CoinbotApplication.bot.getClaimQueue().toQueue(claim);
		}
		
		try {
			driver.close();
		} catch (Exception e) {

		}
		CoinbotApplication.ui.workerQueue.removePanel(workerPanel);
	}
}
