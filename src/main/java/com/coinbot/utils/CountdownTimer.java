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

import java.util.Timer;
import java.util.TimerTask;


public class CountdownTimer implements Runnable {
	private boolean stopped = false;
	private int hours;
	private int minutes;
	private int seconds;
	
	public CountdownTimer() {
		this(0, 0, 0);
	}
	
	public CountdownTimer(int hours, int minutes, int seconds) {
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
	}
	
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
	
	public void stop() {
		stopped = true;
	}
	
	public boolean hasFinished() {
		return stopped;
	}
	
	@Override
	public void run() {
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				if(stopped) {
					t.cancel();
					return;
				}
				
				seconds--;
				if(seconds < 0) {
					minutes--;
					seconds = 59;
				}
				
				if(minutes < 0) {
					hours--;
					minutes = 59;
				}
				
				if(hours < 0) {
					stopped = true;
				}
				
			}
		}, 0, 1000);
	}
	
	
	public static void main(String[] args) {
		CountdownTimer ct = new CountdownTimer(1, 20, 30);
		ct.start();
		
		while(!ct.hasFinished()) {
			System.out.println(ct.toString());
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
/*
package com.coinbot.faucet;

import com.coinbot.core.CoinbotApplication;
import com.coinbot.ui.ClaimPanel;

public class ClaimTimer implements Runnable {
	private Claim claim;
	private Thread thread;
	private boolean ready = true;
	private int hours = 0;
	private int minutes = 0;
	private int seconds = 0;
	
	public ClaimTimer(Claim claim) {
		this.claim = claim;
		this.hours = claim.getFaucet().getTimer()/60;
		this.minutes = claim.getFaucet().getTimer();
	}
	
	public void done(int reward, int timer) {
		this.hours = timer/60;
		this.minutes = timer;
		this.seconds = 0;
		claim.getFaucet().setTimer(timer);
		claim.getFaucet().setReward(reward);
		ready = false;
		thread = new Thread(this);
		thread.start();
	}
	
	public boolean isReady() {
		return ready;
	}
	
	public String getTimer() {
		String zeroH = "";
		String zeroM = "";
		String zeroS = "";
		if(hours < 10) {
			zeroH = "0";
		}
		
		if(minutes < 10) {
			zeroM = "0";
		}
		if(seconds < 10) {
			zeroS = "0";
		}
		
		return zeroH + hours + ":" + zeroM + minutes + ":" + zeroS + seconds;
	}
	
	@Override
	public void run() {
		ClaimPanel p = CoinbotApplication.ui.claimQueue.getClaimPanel(claim);
		p.getProgressBar().setMaximum(claim.getFaucet().getTimer()*60);
		p.getProgressBar().setValue(0);
		p.getProgressBar().setStringPainted(true);
		
		int s = claim.getFaucet().getTimer()*60;
		
		for (int i = 0; i < s; i++) {
			
			try {
				seconds--;
				
				if(seconds < 0) {
					minutes--;
					
					if(minutes < 0) {
						hours--;
						minutes = 59;
					}
					
					seconds = 59;
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			p.getProgressBar().setString(getTimer());
			p.getProgressBar().setValue(i);
		}
		
		p.ready();
		ready = true;
	}
	
}
*/