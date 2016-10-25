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

import java.text.DecimalFormat;

import com.coinbot.faucet.Currency;

public class Balance {
	private DecimalFormat df = new DecimalFormat("0.00000000");
	private Currency currency;
	private double amount;
	
	public Balance(Currency currency) {
		this(currency, 0.00000000);
	}
	
	public Balance(Currency currency, double amount) {
		this.currency = currency;
		this.amount = amount;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public Currency getCurrency() {
		return currency;
	}
	
	public String getBalance() {
		return df.format(amount);
	}
	
	public void updateBalance(double amount) {
		if(currency != Currency.DOGE) {
			amount /= 100000000;
		}
		
		this.amount += amount;
	}
}
