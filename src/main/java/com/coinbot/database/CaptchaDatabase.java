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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.coinbot.captcha.Captcha;

public class CaptchaDatabase extends Properties {
	private File file;
	private List<Captcha> captchas = new ArrayList<Captcha>();
	
	public CaptchaDatabase(File file) {
		this.file = file;
	}
	
	public void addCaptcha(Captcha captcha) {
		captchas.add(captcha);
	}
	
	public void removeCaptcha(Captcha captcha) {
		captchas.remove(captcha);
	}
	
	public List<Captcha> getCaptchas() {
		return captchas;
	}
	
	public String getAnswer(String captchaHash) {
		for (Captcha captcha : captchas) {
			if(captcha.getHash().equals(captchaHash)) {
				return captcha.getAnswer();
			}
		}
		return null;
	}
}
