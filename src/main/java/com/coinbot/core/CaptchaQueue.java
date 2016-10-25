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
package com.coinbot.core;

import java.util.ArrayList;
import java.util.List;

import com.coinbot.captcha.CaptchaService;

public class CaptchaQueue implements Runnable {
	private List<CaptchaService> queue = new ArrayList<CaptchaService>();
	
	public void toQueue(CaptchaService captcha) {
		queue.add(captcha);
		CoinbotApplication.ui.captchaQueue.addCaptcha(captcha);
	}
	
	public void deQueue(CaptchaService captcha) {
		queue.remove(captcha);
		CoinbotApplication.ui.captchaQueue.removeCaptcha(captcha);
	}
	
	public List<CaptchaService> getQueue() {
		return new ArrayList<CaptchaService>(queue);
	}

	@Override
	public void run() {
		for (CaptchaService captcha : getQueue()) {
			if(captcha.isExpired()) {
				deQueue(captcha);
			}
		}
	}
}
