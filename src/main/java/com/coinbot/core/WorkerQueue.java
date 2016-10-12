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

/**
 * Esta clase se usa para gestionar la cola de "workers" (thread), en caso de 
 * que un worker muera por una excepcion, la cola automaticamente crea otro. 
 * @author danjian
 */
public class WorkerQueue implements Runnable {
	private List<Worker> queue = new ArrayList<Worker>();
	private Thread queueThread;
	private int maxWorkers = 4;
	
	public WorkerQueue(int maxWorkers) {
		this.maxWorkers = maxWorkers;
	}
	
	public WorkerQueue() {
		this(4);
	}
	
	public void setMaxWorkers(int maxWorkers) {
		this.maxWorkers = maxWorkers;
	}
	
	public synchronized void addWorker(Worker worker) {
		queue.add(worker);
	}
	
	public synchronized void removeWorker(Worker worker) {
		queue.remove(worker);
	}
	
	public List<Worker> getWorkers() {
		return new ArrayList<Worker>(queue);
	}
	
	public int countWorkers() {
		return getWorkers().size();
	}
	
	public void clearQueue() {
		queue.clear();
	}
	
	public void stop() {
		queueThread.interrupt();
		stopWorkers();
	}
	
	public void stopWorkers() {
		for (Worker worker : getWorkers()) {
			worker.stop();
		}
		clearQueue();
	}
	
	public void start() {
		queueThread = new Thread(this);
		queueThread.start();
	}
	
	public boolean isQueueFull() {
		if(countWorkers() >= maxWorkers) {
			return true;
		}
		return false;
	}
	
	public void checkQueue() {
		for (Worker worker : getWorkers()) {
			if(worker.hasFinished()) {
				removeWorker(worker);
			}
		}
	}
	
	@Override
	public void run() {
		while(CoinbotApplication.bot.isRunning()) {
			checkQueue();
			
			// La cola no esta llena, nuevo worker ...
			if(!isQueueFull()) {
				//System.out.println("Adding worker id: " + countWorkers());
				Worker worker = new Worker(countWorkers());
				worker.start();
				addWorker(worker);
			}
		}
		System.out.println("Stopped");
	}
}
