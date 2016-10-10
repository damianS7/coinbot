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

import com.coinbot.faucet.FaucetClaim;
import com.coinbot.faucet.Faucet;
import com.coinbot.ui.FaucetClaimPanel;
import com.proxy.Proxy;

public class ClaimQueue {
	private List<FaucetClaim> queue = new ArrayList<FaucetClaim>();
	
	public void toQueue(FaucetClaim claim) {
		queue.add(claim);
		CoinbotApplication.ui.faucetQueue.addFaucetClaim(claim.getPanel());
	}
	
	public FaucetClaim next() {
		List<FaucetClaim> cs = getClaims();
		
		if(!cs.isEmpty()) {
			FaucetClaim claim = cs.get(0);
			if(claim.isReady()) {
				queue.remove(claim);
				return claim;
			}
		}
		
		return null;
	}
	
	public void start() {
		// Asignacion de claims
		
		List<String> address = CoinbotApplication.bot.getAddresses();
		List<Faucet> faucets = CoinbotApplication.bot.getFaucets();
		
		for (Proxy p : CoinbotApplication.bot.getProxies()) {
			for (Faucet f : faucets) {
				int index = queue.size();
				
				try {
					String a = address.get(index);
					FaucetClaim c = new FaucetClaim(p, f, a);
					toQueue(c);
				} catch (IndexOutOfBoundsException e) {
					break;
				}
			}
			
		}
		
	}
	
	public List<FaucetClaim> getClaims() {
		return new ArrayList<FaucetClaim>(queue);
	}
	
	public int size() {
		return queue.size();
	}
	
	public FaucetClaim getClaim() {
		for (FaucetClaim claim : getClaims()) {
			if(claim.isReady()) {
				return claim;
			}
		}
		return null;
	}
}
