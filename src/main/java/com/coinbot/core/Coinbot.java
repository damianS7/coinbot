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



/**
 * Clase principal del bot
 * @author danjian
 */
public class Coinbot {
	private WorkerQueue workerQueue;
	private ClaimQueue claimQueue;
	private CaptchaQueue captchaQueue;
	private boolean running = false;
	
	public Coinbot() {
		captchaQueue = new CaptchaQueue();
		claimQueue = new ClaimQueue();
		workerQueue = new WorkerQueue();
		workerQueue.setMaxWorkers(CoinbotApplication.coinbotProperties.getWorkers());
	}
	
	public CaptchaQueue getCaptchaQueue() {
		return captchaQueue;
	}
	
	public ClaimQueue getClaimQueue() {
		return claimQueue;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public void start() {
		if(isRunning()) {
			return;
		}
		
		running = true;
		claimQueue.start();
		workerQueue.start();
	}
	
	public void stop() {
		if(!isRunning()) {
			return;
		}
		
		running = false;
		workerQueue.stop();
	}
}
