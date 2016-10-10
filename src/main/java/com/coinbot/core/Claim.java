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

import com.proxy.Proxy;

public class Claim implements Runnable {
	private Proxy proxy;
	private Faucet faucet;
	private String btcAddress;
	private boolean ready = true;
	private Thread thread;
	
	public Claim(Proxy proxy, Faucet faucet, String btcAddress) {
		this.proxy = proxy;
		this.faucet = faucet;
		this.btcAddress = btcAddress;
		this.thread = new Thread(this);
	}
	
	public String getBtcAddress() {
		return btcAddress;
	}
	
	public Faucet getFaucet() {
		return faucet;
	}
	
	public Proxy getProxy() {
		return proxy;
	}
	
	public void done() {
		ready = false;
		thread.start();
	}
	
	public boolean isReady() {
		return ready;
	}

	@Override
	public void run() {
		for (int i = 0; i < faucet.getTimer()*60; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ready = true;
	}
}
