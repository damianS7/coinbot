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

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.imageio.ImageIO;

import com.coinbot.captcha.Captcha;

public class CaptchaHash {
	private String hash;
	private String answer;
	
	public CaptchaHash(Captcha captcha) {
		this(imageToHash(captcha.getImage()), captcha.getAnswer());
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
	
	private static String imageToHash(BufferedImage image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {
			ImageIO.write(image, "png", baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] data = baos.toByteArray();
		
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(data);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		
		byte[] hashByteArray = md.digest();
		
		String hash = "";
		for (byte b : hashByteArray) {
			hash += Byte.toString(b);
		}
		
		return hash;
	}
}
