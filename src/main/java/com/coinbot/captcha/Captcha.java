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

public abstract class Captcha {
	protected BufferedImage puzzle;
	protected String answer;
	protected boolean expired = false;

	public Captcha(BufferedImage puzzle) {
		this.puzzle = puzzle;
	}
	
	public BufferedImage getImage() {
		return puzzle;
	}
	
	public void setAnswer(String newAnswer) {
		this.answer = newAnswer;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public boolean resolved() {
		if(answer!=null) {
			return true;
		}
		return false;
	}
	
	public boolean isExpired() {
		return expired;
	}
	
	public void expired() {
		expired = true;
	}
}
