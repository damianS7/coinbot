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

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.hamcrest.core.IsNull;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.coinbot.antibot.Antibot;
import com.coinbot.antibot.AntibotLink;
import com.coinbot.antibot.Makejar;
import com.coinbot.antibot.MakejarV1Link;
import com.coinbot.antibot.MakejarV2Link;
import com.coinbot.exceptions.DetectionException;
import com.coinbot.utils.Image;

public class AntibotDetector implements Detector {
	private WebDriver driver;
	private Antibot antibot;
	private boolean hasAntibot = false;

	// <p class="alert alert-info">Please click on the Anti-Bot links in the
	// following order <img
	// alt="" width="63" height="24"> <a href="#" id="antibotlinks_reset"
	// style="display: none;">( reset )</a></p>
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
					
					antibot = new MakejarAntibot();
				}
			}

			List<WebElement> links = driver.findElements(By.tagName("a"));
			
			// Buscando los enlaces
			for (WebElement l : links) {
				if (l.getAttribute("rel") != null) {
					try {
						// V2
						WebElement img = l.findElement(By.tagName("img"));
						String src = img.getAttribute("src");
						String[] s = src.split(",");
						String base64 = s[s.length - 1];
						
						antibot.addLink(new MakejarV2Link(img, Image
								.base64ToImage(base64)));
					} catch (Exception e) {
						// V1
						if(l.getAttribute("class").equals("antibotlinks")) {
							antibot.addLink(new MakejarV1Link(l));
							continue;
						}
					}
				}
			}
		} catch (NoSuchElementException e) {
			throw new DetectionException("Failed detecting antibot.");
		}
	}

	public static void main(String[] args) {
		/*
		File pathToBinary = new File(
				"/home/jian/Descargas/firefox46/bin/firefox");
		// Firefox 46 needed
		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
		FirefoxProfile profile = new FirefoxProfile();
		FirefoxDriver driver = new FirefoxDriver(ffBinary, profile);

		try {
			driver.manage().timeouts().pageLoadTimeout(12, TimeUnit.SECONDS);
			//driver.navigate().to(new URL("http://bitcoin-gator.com"));
			driver.navigate().to(new URL("http://bitcoins-loot.site"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}

		AntibotDetector ad = new AntibotDetector(driver);
		try {
			ad.detect();
		} catch (DetectionException e) {
			e.printStackTrace();
		}
		Antibot a = ad.getAntibot();
*/
		
		String b = "iVBORw0KGgoAAAANSUhEUgAAAJYAAAAQCAIAAAB7ihFwAAAABnRSTlMAAA"
				+ "AAAABupgeRAAAA3UlEQVRYhe2X2w6EIAxEW8P//zL7gGm6IDAU48rS8yIS6"
				+ "jgtl0DkOI7jOHvD54NZumKMUCQzPjiLAoVsEqNCBu+v4iCVqWRAW2Jm/ar7"
				+ "DWINobskukKZo6FfeieHIeYBn8w8syCkJDsQqLJ7XE5V3TYUsiskw+YLUG"
				+ "6PjcW3NEFa4rDhauZwGhKaRz6edk7wOFyxomcJy9pka07HtE9HJFmlEI453"
				+ "TVHt8zLHxJoxEPpfIjHkoULrV4/SpeKy9OoH/ltHskFLtQYCSYdvCrYvP8h"
				+ "tbvHukKO4zjOPnwAkMalKVsXMY4AAAAASUVORK5CYII=";
		String b2 = "iVBORw0KGgoAAAANSUhEUgAAAJYAAAAQCAIAAAB7ihFwAAAABnRSTlMAA"
				+ "AAAAABupgeRAAAAiklEQVRYhe3W0QqAIAwF0E37/19eD6JEGUw024V7Xqqh6"
				+ "QUZihARERER/U1VVfX23i1C8yfCyn4MjX6GMbPJ6f7i5EKTI8NKUjfdjlv57"
				+ "Bah+RNhZc/l0e0VQw1EqzX7+mYh/6zgzfMqSd2umbXT91aE5k+ElT2t/d22n"
				+ "hO8ueHZdm2Lfz8kIiI4J9gZSGK4DjdcAAAAAElFTkSuQmCC";
		JLabel label = new JLabel(new ImageIcon(Image.base64ToImage(b2)));
		JFrame f = new JFrame();
		JPanel p = new JPanel();
		f.add(p, BorderLayout.CENTER);
		p.add(label);
		/*
		for (AntibotLink l : a.getLinks()) {
			if(l instanceof MakejarV2Link) {
				MakejarV2Link ml = (MakejarV2Link) l;
				p.add(new JLabel(new ImageIcon(ml.getImage())));
			} else {
				MakejarV1Link ml = (MakejarV1Link) l;
				p.add(new JLabel(ml.getContent()));
			}
		}*/
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(900, 600);
		f.setVisible(true);
		
	}

}
