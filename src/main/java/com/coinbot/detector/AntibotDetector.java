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

import java.awt.image.BufferedImage;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.coinbot.antibot.Antibot;
import com.coinbot.antibot.AntibotLink;
import com.coinbot.antibot.MakejarAntibot;
import com.coinbot.exceptions.DetectionException;
import com.coinbot.utils.Image;

public class AntibotDetector implements Detector {
	private WebDriver driver;
	private Antibot antibot;
	private boolean hasAntibot = false;

	public AntibotDetector(WebDriver driver) {
		this.driver = driver;
	}

	public Antibot getAntibot() {
		return antibot;
	}

	public boolean hasAntibot() {
		return hasAntibot;
	}

	@Override
	public void detect() throws DetectionException {
		try {
			driver.findElement(By.className("antibotlinks"));
			hasAntibot = true;
		} catch (NoSuchElementException e) {
			return;
		}

		try {
			List<WebElement> alerts = driver.findElements(By
					.className("alert-info"));

			// Buscando el puzzle principal
			for (WebElement alert : alerts) {
				if (alert.getText().toLowerCase().contains("anti-bot")) {
					WebElement img = alert.findElement(By.tagName("img"));
					BufferedImage bi = Image.capture(driver, img);
					antibot = new MakejarAntibot(bi);
				}
			}

			// Buscando antibots
			List<WebElement> antibots = driver.findElements(By
					.className("antibotlinks"));
			for (WebElement ab : antibots) {

				// Aveces los links van dentro de divs (V2)
				if (ab.getTagName().equals("div")) {
					if (!ab.getText().isEmpty()) {
						ab = ab.findElement(By.tagName("a"));
					}
				}

				// V1
				if (ab.getTagName().equals("a")) {
					if (ab.getAttribute("rel") != null) {
						antibot.addLink(new AntibotLink(ab, Image.capture(
								driver, ab)));
					}
				}
			}
		} catch (NoSuchElementException e) {
			throw new DetectionException("Failed detecting antibot.");
		}
	}
}
