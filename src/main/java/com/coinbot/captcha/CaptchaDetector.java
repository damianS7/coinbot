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
package com.coinbot.captcha;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.coinbot.exceptions.UnrecognizedCaptcha;

public class CaptchaDetector {
	
	private BufferedImage captureCaptcha(WebDriver driver, WebElement captcha) {
		WebElement iframe = null;
		try {
			iframe = captcha.findElement(By.tagName("iframe"));
		} catch (NoSuchElementException e) {
		}
		
		// Si tiene iframe es la version 2
		if(iframe != null) {
			driver.switchTo().frame(iframe);
			JavascriptExecutor js2 = (JavascriptExecutor)driver;
			js2.executeScript("document.getElementById('overlay').style.display = 'inline'");
			driver.switchTo().defaultContent();
		}
		
		byte[] screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		BufferedImage imageScreen = null;
		try {
			imageScreen = ImageIO.read(new ByteArrayInputStream(
					screen));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		Point capLocation = captcha.getLocation();
		Dimension capDimension = captcha.getSize();
		
		return imageScreen.getSubimage(capLocation.x, capLocation.y, 
				capDimension.width, capDimension.height);
	}
	
	private SolveMedia findSolvemedia(WebDriver driver, WebElement body) {
		WebElement captcha;
		
		try {
			captcha = body.findElement(By.id("adcopy-puzzle-image"));
		} catch (NoSuchElementException e) {
			return null;
		}
		
		BufferedImage shot = captureCaptcha(driver, captcha); 
		return new SolveMedia(shot);
	}
	
	private ReCaptcha findReCaptcha() {
		return null;
	}
	
	public Captcha find(WebDriver driver, WebElement body) throws UnrecognizedCaptcha {
		SolveMedia sm = findSolvemedia(driver, body);
		if(sm != null) {
			return sm;
		}
		throw new UnrecognizedCaptcha("Captcha not recognized.");
	}
}
