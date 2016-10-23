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
package com.coinbot.detector;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.coinbot.captcha.CaptchaService;
import com.coinbot.captcha.ReCaptcha;
import com.coinbot.captcha.SolveMedia;
import com.coinbot.exceptions.DetectionException;
import com.coinbot.utils.Image;

public class CaptchaDetector implements Detector {
	private WebDriver driver;
	private CaptchaService captcha;

	public CaptchaDetector(WebDriver driver) {
		this.driver = driver;
	}

	public CaptchaService getCaptcha() {
		return captcha;
	}
	
	private SolveMedia detectSM() {
		WebElement body = driver.findElement(By.tagName("body"));
		WebElement captcha;

		try {
			captcha = body.findElement(By.id("adcopy-puzzle-image"));
		} catch (NoSuchElementException e) {
			return null;
		}

		WebElement iframe = null;
		try {
			iframe = captcha.findElement(By.tagName("iframe"));
		} catch (NoSuchElementException e) {
		}

		// Si tiene iframe es la version 2
		if (iframe != null) {
			driver.switchTo().frame(iframe);
			JavascriptExecutor js2 = (JavascriptExecutor) driver;
			js2.executeScript("document.getElementById('loading').style.display = 'none'");
			js2.executeScript("document.getElementById('overlay').style.display = 'inline'");
			driver.switchTo().defaultContent();
		}
		
		return new SolveMedia(Image.capture(driver, captcha));
	}
	
	private ReCaptcha detectRC() {
		return null;
	}

	@Override
	public void detect() throws DetectionException {
		// SM
		this.captcha = detectSM();
		if(this.captcha != null) {
			return;
		}
		
		// RC
		this.captcha = detectRC();
		if(this.captcha != null) {
			return;
		}

		throw new DetectionException("Captcha not recognized.");
	}
}