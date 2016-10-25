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
package com.coinbot.stats;

import java.util.ArrayList;
import java.util.List;

import com.coinbot.core.CoinbotApplication;
import com.coinbot.faucet.Currency;

public class Stats {
	private List<Balance> balances = new ArrayList<Balance>();
	
	public Stats() {
		for (Currency currency : Currency.values()) {
			balances.add(new Balance(currency));
		}
	}
	
	public Balance getBalance(Currency currency) {
		for (Balance balance : balances) {
			if(balance.getCurrency() == currency) {
				return balance;
			}
		}
		return null;
	}
	
	public void updateBalance(Currency currency, double amount) {
		Balance b = getBalance(currency);
		b.updateBalance(amount);
		CoinbotApplication.ui.balance.setBalance(currency, b.getBalance());
	}
}
