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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.coinbot.captcha.Captcha;

public class CaptchaDatabase {
	private List<Captcha> captchas = new ArrayList<Captcha>();
	private Properties properties;
	private File file;
	
	public CaptchaDatabase(File file) {
		this.file = file;
		properties = new Properties();
	}
	
	public int load () throws FileNotFoundException, IOException {
		properties.load(new FileInputStream(file));
		
		for (Object key : properties.keySet()) {
			String k = (String) key;
			captchas.add(new Captcha(k, properties.getProperty(k)));
		}
		
		return captchas.size();
	}
	
	public void save() throws FileNotFoundException, IOException {
		properties.clear();
		for (Captcha captcha : captchas) {
			properties.put(captcha.getHash(), captcha.getAnswer());
		}
		properties.store(new FileOutputStream(file), "Captcha hash database");
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
