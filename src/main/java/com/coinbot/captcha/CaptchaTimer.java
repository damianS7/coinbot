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

public class CaptchaTimer implements Runnable {
	private Captcha captcha;
	private boolean expired = false;
	private int seconds = 0;
	
	public CaptchaTimer(Captcha captcha, int seconds) {
		this.captcha = captcha;
		this.seconds = seconds;
	}
	
	public void start() {
		new Thread(this).start();
	}
	
	public void stop() {
		expired = true;
	}
	
	public int getSecondsLeft() {
		return seconds;
	}
	
	public boolean isExpired() {
		return expired;
	}
	
	@Override
	public void run() {
		while(!captcha.resolved() && !isExpired()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(seconds <= 0) {
				captcha.expired();
				expired = true;
				break;
			}
			
			seconds--;
		}
	}

}
