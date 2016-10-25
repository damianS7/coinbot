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

import com.coinbot.antibot.Antibot;

public class AntibotQueue {
private List<Antibot> queue = new ArrayList<Antibot>();
	
	public void toQueue(Antibot ab) {
		queue.add(ab);
		CoinbotApplication.ui.antibotQueue.addPanel(ab);
	}
	
	public void deQueue(Antibot ab) {
		queue.remove(ab);
		CoinbotApplication.ui.antibotQueue.removePanel(ab);
	}
	
	public List<Antibot> getQueue() {
		return new ArrayList<Antibot>(queue);
	}
}
