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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import com.coinbot.ui.CaptchaPanel;
import com.coinbot.ui.ClaimPanel;
import com.coinbot.ui.WorkerPanel;
import com.proxy.Proxy;

/**
 * Esta clase hace todo el trabajo de claim
 * @author danjian
 */
public class Worker implements Runnable {
	private int workerId = 0;
	private WorkerPanel workerPanel;
	private Thread thread;
	private Proxy proxy;
	private List<Claim> faucets;
	
	public Worker(int workerId) {
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
			
			Claim claim = CoinbotApplication.bot.getClaimQueue().next();
			if(claim == null) {
				continue;
			}
			
			CoinbotApplication.ui.claimQueue.removeClaim(claim.getPanel());
			
			workerPanel.addClaim(claim.getPanel());
			
			claim.getPanel().getProgressBar().setStringPainted(true);
			claim.getPanel().getProgressBar().setMaximum(3);
			
			BufferedImage bi;
			try {
				bi = ImageIO.read(new File("coinbot/captchas/smc1.png"));
				CoinbotApplication.ui.captchaQueue.addCaptcha(new CaptchaPanel(bi));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
			for (int i = 0; i < 3; i++) {
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
				e.printStackTrace();
			}
			
			workerPanel.removeClaim(claim.getPanel());
			
			claim.getTimer().done(2000, 1);
			CoinbotApplication.bot.getClaimQueue().toQueue(claim);
			
		}
		System.out.println("Worker " + workerId + " end work!");
		
		CoinbotApplication.ui.workerQueue.removeWorker(workerPanel);
	}

}
