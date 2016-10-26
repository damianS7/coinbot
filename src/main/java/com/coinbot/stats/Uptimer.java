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
package com.coinbot.stats;

import java.util.Timer;
import java.util.TimerTask;

public class Uptimer implements Runnable {
	private int hours = 0;
	private int minutes = 0;
	private int seconds = 0;
	
	public int getHours() {
		return hours;
	}
	
	public int getMinutes() {
		return minutes;
	}
	
	public int getSeconds() {
		return seconds;
	}
	
	public String toString() {
		String h = (hours < 10) ? "0" + hours : "" + hours;
		String m = (minutes < 10) ? "0" + minutes : "" + minutes;
		String s = (seconds < 10) ? "0" + seconds : "" + seconds;
		return h + ":" + m + ":" + s;
	}
	
	public void start() {
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				/*if(!CoinbotApplication.bot.isRunning()) {
					t.cancel();
					return;
				}*/
				
				seconds++;
				if(seconds > 59) {
					minutes++;
					seconds = 0;
				}
				
				if(minutes > 59) {
					hours++;
					minutes = 0;
				}
				
			}
		}, 0, 1000);
	}
}
