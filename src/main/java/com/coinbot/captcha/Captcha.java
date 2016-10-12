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

import com.coinbot.ui.CaptchaPanel;
import com.thoughtworks.selenium.webdriven.commands.GetExpression;

public class Captcha {
	private BufferedImage image;
	private String hash;
	private String answer;
	private boolean resolved = false;
	private int expirationTime = 45;
	private boolean expired = false;
	
	public Captcha(BufferedImage image) {
		this.image = image;
	}
	
	public Captcha(String hash) {
		this.hash = hash;
	}
	
	public Captcha(String hash, String answer) {
		this.answer = answer;
		this.hash = hash;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public String getHash() {
		return hash;
	}
	
	public boolean isResolved() {
		return resolved;
	}
	
	public void resolve(String answer) {
		resolved = true;
		this.answer = answer;
	}
	
	public boolean hasExpired() {
		return expired;
	}
	
	public void expired() {
		expired = true;
	}

	public int getExpirationTime() {
		return expirationTime;
	}
}
