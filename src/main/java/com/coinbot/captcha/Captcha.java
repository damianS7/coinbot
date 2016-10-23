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

import com.coinbot.utils.Image;

public class Captcha {
	protected BufferedImage captcha;
	protected String hash;
	protected String answer;
	
	public Captcha(BufferedImage captcha) {
		this.captcha = captcha;
		this.hash = Image.imageToHash(captcha);
	}
	
	public Captcha(String hash) {
		this(hash, null);
	}
	
	public Captcha(String hash, String answer) {
		this.hash = hash;
		this.answer = answer;
	}
	
	public BufferedImage getImage() {
		return captcha;
	}
	
	public String getHash() {
		return hash;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
