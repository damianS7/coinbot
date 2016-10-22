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
package com.coinbot.database;

import com.coinbot.captcha.Captcha;
import com.coinbot.utils.ImageHash;

public class CaptchaHash {
	private String hash;
	private String answer;
	
	public CaptchaHash(Captcha captcha) {
		this(ImageHash.imageToHash(captcha.getImage()), captcha.getAnswer());
	}
	
	public CaptchaHash(String hash, String answer) {
		this.hash = hash;
		this.answer = answer;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public String getHash() {
		return hash;
	}
}
