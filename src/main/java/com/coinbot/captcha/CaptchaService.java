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

import org.openqa.selenium.WebDriver;

import com.coinbot.exceptions.InputNotFoundException;

public abstract class CaptchaService extends Captcha {
	protected CaptchaTimer timer;
	
	public CaptchaService(BufferedImage captcha, CaptchaTimer timer) {
		super(captcha);
		this.timer = timer;
		timer.start();
	}

	public boolean isExpired() {
		return timer.hasFinished();
	}
	
	public CaptchaTimer getTimer() {
		return timer;
	}
	
	public boolean isResolved() {
		if(getAnswer() == null) {
			return false;
		}
		return true;
	}
	
	public abstract void insertAnswer(WebDriver driver, String answer) 
			throws InputNotFoundException;
}
