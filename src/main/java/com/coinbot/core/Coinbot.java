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

public class Coinbot {
	private boolean isRunning;
	private WorkerQueue workerQueue;
	private ClaimQueue claimQueue;
	private CaptchaQueue captchaQueue;
	
	public Coinbot() {
		isRunning = false;
		captchaQueue = new CaptchaQueue();
		claimQueue = new ClaimQueue();
		workerQueue = new WorkerQueue();
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public CaptchaQueue getCaptchaQueue() {
		return captchaQueue;
	}
	
	public ClaimQueue getClaimQueue() {
		return claimQueue;
	}
	
	public void stop() {
		if(!isRunning()) {
			return;
		}
		isRunning = false;
		workerQueue.stop();
		workerQueue.clearQueue();
		claimQueue.clearQueue();
	}
	
	public void start() {
		if(isRunning()) {
			return;
		}
		// Start
		workerQueue.setMaxWorkers(CoinbotApplication.config.getWorkers());
		isRunning = true;
		claimQueue.start();
		workerQueue.start();
	}
}
