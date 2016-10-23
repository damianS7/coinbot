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

public class FaucetDetector implements Detector {
	private WebDriver driver;
	private int balance = 0;
	private int reward = 0;
	private int timer = 30;
	
	public FaucetDetector(WebDriver driver) {
		this.driver = driver;
	}
	
	private void parseBalance(String[] s) {
		for (int i = 0; i < s.length; i++) {
			try {
				balance = Integer.parseInt(s[i]);
				return;
			} catch (NumberFormatException ex) {
				continue;
			}
		}
	}
	
	private void parseReward(String[] s) {
		for (int i = 0; i < s.length; i++) {
			try {
				reward = Integer.parseInt(s[i]);
				return;
			} catch (NumberFormatException ex) {
				continue;
			}
		}
	}

	private void parseTimer(String[] s) {
		for (int i = 0; i < s.length; i++) {
			if(s[i].equals("minutes.")) {
				try {
					timer = Integer.parseInt(s[i-1]);
				} catch (NumberFormatException ex) {
				}
			}
		}
	}
	
	public boolean isClaimeable() {
		if(getBalance() > 10000 && reward > 10 && timer > 5) {
			return true;
		}
		return false;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public int getReward() {
		return reward;
	}
	
	public int getTimer() {
		return timer;
	}
	
	public String printData() {
		String data = "";
		data += "Balance: " + getBalance();
		data += " Reward: " + getReward();
		data += " Timer: " + getTimer();
		data += " Claimeable: " + isClaimeable();
		System.out.println(data);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return data;
	}
	

	@Override
	public void detect() throws DetectionException {
		try {
			List<WebElement> alerts = driver.findElements(By.className("alert"));
			for (WebElement alert : alerts) {
				String[] s = alert.getText().split(" ");
				if(alert.getText().toLowerCase().contains("minutes.")) {
					parseTimer(s);
				}
				
				if(alert.getText().toLowerCase().contains("satoshi")) {
					parseReward(s);
				}
				
				if(alert.getText().toLowerCase().contains("balance")) {
					parseBalance(s);
				}
			}
		} catch (NoSuchElementException e) {
			
		}
		
		throw new DetectionException("Faucet data not detected!");
	}
	
	public static void main(String[] args) {
		
	}
}
