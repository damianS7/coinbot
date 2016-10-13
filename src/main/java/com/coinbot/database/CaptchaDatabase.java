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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.coinbot.captcha.Captcha;

public class CaptchaDatabase extends FileDatabase {
	private List<CaptchaHash> captchaHashes = new ArrayList<CaptchaHash>();
	
	public CaptchaDatabase(File file){
		super(file);
	}
	
	public String getAnswer(String hash) {
		for (CaptchaHash ch : captchaHashes) {
			if(ch.getHash().equals(hash)) {
				if(!ch.getAnswer().isEmpty()) {
					return ch.getAnswer();
				}
			}
		}
		return null;
	}
	
	public void addCaptcha(CaptchaHash captchaHash) {
		captchaHashes.add(captchaHash);
		save();
	}
	
	public List<CaptchaHash> getCaptchasHashes() {
		return new ArrayList<CaptchaHash>(captchaHashes);
	}
	
	@Override
	public void save() {
		List<String> lines = new ArrayList<String>();
		
		for (CaptchaHash captcha : getCaptchasHashes()) {
			lines.add(captcha.getHash() + "=" + captcha.getAnswer());
		}
		
		super.save();
	}
	
	@Override
	public int load() {
		super.load();
		
		for (String line : getLines()) {
			if(line.contains("=")) {
				String[] s = line.split("=");
				
				if(s.length == 1) {
					captchaHashes.add(new CaptchaHash(s[0], ""));
				}
				
				if(s.length == 2) {
					captchaHashes.add(new CaptchaHash(s[0], s[1]));
				}
			}
		}
		
		return captchaHashes.size();
	}
	
}
