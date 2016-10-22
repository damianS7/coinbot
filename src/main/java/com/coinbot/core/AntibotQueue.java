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

import com.coinbot.antibot.AntibotPuzzle;

public class AntibotQueue {
private List<AntibotPuzzle> queue = new ArrayList<AntibotPuzzle>();
	
	public void toQueue(AntibotPuzzle ap) {
		queue.add(ap);
		CoinbotApplication.ui.captchaQueue.addAntibotPuzzle(ap);
	}
	
	public void deQueue(AntibotPuzzle captcha) {
		queue.remove(captcha);
		CoinbotApplication.ui.captchaQueue.removeAntibotPuzzle(captcha);
	}
	
	public List<AntibotPuzzle> getQueue() {
		return new ArrayList<AntibotPuzzle>(queue);
	}
}
