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

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.coinbot.exceptions.DetectionException;
import com.coinbot.exceptions.InvalidAddressException;
import com.coinbot.exceptions.InvalidAntibotException;
import com.coinbot.exceptions.InvalidCaptchaException;

public class ClaimDetector implements Detector {
	private WebDriver driver;
	private boolean success = false;
	
	public ClaimDetector(WebDriver driver) {
		this.driver = driver;
	}
	
	public boolean isClaimSuccessfull() {
		return success;
	}

	@Override
	public void detect() throws DetectionException, InvalidAntibotException,
			InvalidCaptchaException, InvalidAddressException {
		try {
			List<WebElement> alertsDanger = driver.findElements(By.className("alert-danger"));
			for (WebElement e : alertsDanger) {

				if (e.getText().toLowerCase().contains("invalid captcha")) {
					throw new InvalidCaptchaException("Invalid captcha.");
				}

				if (e.getText().toLowerCase()
						.contains("invalid antibot")) {
					throw new InvalidAntibotException(
							"Invalid antibot verification.");
				}

				if (e.getText().toLowerCase().contains("invalid address")) {
					throw new InvalidAddressException("Invalid address.");
				}
			}
		} catch (NoSuchElementException e) {
		}

		try {
			List<WebElement> alerts = driver.findElements(By.className("alert-success"));
			for (WebElement e : alerts) {

				if (e.getText().toLowerCase().contains("satoshi was sent")) {
					this.success = true;
					return;
				}
				
			}
		} catch (NoSuchElementException e) {
		}

		throw new DetectionException("Couldn't detect any output message.");
	}
}
