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
package com.coinbot.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.coinbot.captcha.SolveMedia;


public class Image {
	
	public static BufferedImage capture(WebDriver driver, WebElement e) {
		byte[] screen = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.BYTES);
		BufferedImage imageScreen = null;
		try {
			imageScreen = ImageIO.read(new ByteArrayInputStream(screen));
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		Point capLocation = e.getLocation();
		Dimension capDimension = e.getSize();
		return imageScreen.getSubimage(capLocation.x,
				capLocation.y, capDimension.width, capDimension.height);
	}
	
	public static BufferedImage base64ToImage(String base64) {
		
		byte[] image = Base64.getDecoder().decode(base64);
		
		ByteArrayInputStream bais = new ByteArrayInputStream(image);

		BufferedImage i = null;
		
		try {
			i = ImageIO.read(bais);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return i;
	}
	
	public static String imageToHash(BufferedImage image) {
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
		for (int i = 0; i < hashByteArray.length; i++) { // for loop ID:1
			hash += Integer.toString((hashByteArray[i] & 0xff) + 0x100, 16)
					.substring(1);
		}
		
		return hash;
	}
}
