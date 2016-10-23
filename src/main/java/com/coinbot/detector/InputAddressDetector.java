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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.coinbot.exceptions.DetectionException;

public class InputAddressDetector implements Detector {
	private WebDriver driver;
	private WebElement input;
	
	public InputAddressDetector(WebDriver driver) {
		this.driver = driver;
	}
	
	public void insertAddress(String address) {
		input.sendKeys(address);
	}

	@Override
	public void detect() throws DetectionException {
		List<WebElement> inputs = driver.findElements(By.tagName("input"));
		for (WebElement input : inputs) {
			
			if(input.getAttribute("type").equals("text") &&
					input.isDisplayed() &&
					input.getAttribute("name").length() > 20) {
				this.input = input;
				return;
			}
		}
		
		throw new DetectionException("Input address not founded.");
	}
}
