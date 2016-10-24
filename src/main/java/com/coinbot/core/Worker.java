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
 * Esta clase hace todo el trabajo para procesar los claims
 * 
 * @author danjian
 */
public class Worker implements Runnable {
	private boolean working = false;
	private int workerId = 0;
	private Thread thread;

	public Worker(int workerId) {
		this.workerId = workerId;
		thread = new Thread(this);
	}

	public int getWorkerId() {
		return workerId;
	}

	public boolean hasFinished() {
		return working;
	}

	public void stop() {
		working = false;
		thread.interrupt();
	}

	public void start() {
		working = true;
		thread.start();
	}

	@Override
	public void run() {
	}
}
