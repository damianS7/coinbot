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
package com.coinbot.faucet;

import com.coinbot.proxy.Proxy;
import com.coinbot.ui.ClaimPanel;
/**
 * Esta clase se usa para representar un "claim" en la faucet. El claim es el
 * acto de rellenar el captcha y poner tu direccion de btc en la faucet y 
 * presionar el boton de reward.
 * @author danjian
 */
public class Claim {
	private ClaimPanel panel;
	private ClaimTimer timer;
	private String btcAddress;
	private Faucet faucet;
	private Proxy proxy;
	
	public Claim(Proxy proxy, Faucet faucet, String btcAddress) {
		this.proxy = proxy;
		this.faucet = faucet;
		this.btcAddress = btcAddress;
		this.panel = new ClaimPanel(proxy.getAddress(), 
				Integer.toString(proxy.getPort()), 
				faucet.getName());
		panel.ready();
		this.timer = new ClaimTimer(this);
	}
	
	public ClaimPanel getPanel() {
		return panel;
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
	
	public ClaimTimer getTimer() {
		return timer;
	}
}