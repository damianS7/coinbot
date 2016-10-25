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
package com.coinbot.faucet.list;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.coinbot.core.CoinbotApplication;
import com.coinbot.detector.FaucetDetector;

public class MakejarList {
	private List<String> urls = new ArrayList<String>();
	private String btc = "http://ltc.makejar.com/";
	
	public void parse() {
		File bin = new File(CoinbotApplication.browser);
		FirefoxBinary ffBinary = new FirefoxBinary(bin);
		FirefoxProfile profile = new FirefoxProfile();
		FirefoxDriver driver = new FirefoxDriver(ffBinary, profile);
		
		try {
			driver.manage().timeouts()
					.pageLoadTimeout(12, TimeUnit.SECONDS);
			driver.navigate().to(new URL(btc));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		
		
		List<WebElement> es = driver.findElements(By.className("flink"));
		for (WebElement e : es) {
			String url = "http://" + e.getText();
			try {
				driver.manage().timeouts()
				.pageLoadTimeout(12, TimeUnit.SECONDS);
				System.out.println(url);
				driver.navigate().to(new URL(url));
				
				FaucetDetector fd = new FaucetDetector(driver);
				System.out.println(fd.isClaimeable());
				System.out.println(fd.printData());
				
				if(fd.isClaimeable()) {
					urls.add(url);
				}
			} catch (Exception e1) {
				System.out.println(url);
				continue;
			}
		}
		
		System.out.println(urls.size());
	}
	
	public static void main(String[] args) {
		MakejarList l = new MakejarList();
		l.parse();
	}

}
