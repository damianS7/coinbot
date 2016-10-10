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

import java.util.List;

import com.coinbot.faucet.FaucetClaim;
import com.coinbot.faucet.Faucet;
import com.coinbot.ui.FaucetClaimPanel;
import com.coinbot.ui.WorkerPanel;
import com.proxy.Proxy;

/**
 * Esta clase hace todo el trabajo de claim
 * @author danjian
 */
public class WorkerThread implements Runnable {
	private int workerId = 0;
	private WorkerPanel workerPanel;
	private Thread thread;
	private Proxy proxy;
	private List<Faucet> faucets;
	
	public WorkerThread(int workerId) {
		this.workerId = workerId;
		this.workerPanel = new WorkerPanel(Integer.toString(workerId));
		thread = new Thread(this);
	}
	
	public int getWorkerId() {
		return workerId;
	}
	
	public boolean hasFinished() {
		return thread.isInterrupted();
	}
	
	public void stop() {
		thread.interrupt();
	}
	
	public void start() {
		thread.start();
	}
	
	@Override
	public void run() {
		CoinbotApplication.ui.workerQueue.addWorker(workerPanel);
		
		while(!thread.isInterrupted()) {
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			
			FaucetClaim claim = CoinbotApplication.bot.getClaimQueue().next();
			if(claim == null) {
				continue;
			}
			
			CoinbotApplication.ui.faucetQueue.removeFaucetClaim(claim.getPanel());
			workerPanel.addFaucetClaim(claim.getPanel());
			
			claim.getPanel().getProgressBar().setStringPainted(true);
			claim.getPanel().getProgressBar().setMaximum(25);
			for (int i = 0; i < 25; i++) {
				claim.getPanel().getProgressBar().setString("Doing things " + i);
				claim.getPanel().getProgressBar().setValue(i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			claim.getPanel().done();
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			claim.done(2000, 1);
			CoinbotApplication.bot.getClaimQueue().toQueue(claim);
		}
		System.out.println("Worker " + workerId + " end work!");
		
		CoinbotApplication.ui.workerQueue.removeWorker(workerPanel);
	}

}
