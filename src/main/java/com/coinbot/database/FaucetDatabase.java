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
package com.coinbot.database;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.coinbot.faucet.Currency;
import com.coinbot.faucet.Faucet;

public class FaucetDatabase extends FileDatabase {
	private List<Faucet> faucets = new ArrayList<Faucet>();
	
	public FaucetDatabase(File file) {
		super(file);
	}
	
	@Override
	public int load() throws IOException {
		super.load();
		for (String line : getLines()) {
			try {
				if(line.contains("=")) {
					String[] s = line.split("=");
					if(s.length == 2) {
						faucets.add(new Faucet(Currency.valueOf(s[0]), s[1]));
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
		return faucets.size();
	}


	@Override
	public void save() {
		List<String> newLines = new ArrayList<String>();
		for (Faucet faucet : getFaucets()) {
			newLines.add(faucet.getCurrency().toString() + "=" 
					+ faucet.getUrl());
		}
		setLines(newLines);
		try {
			super.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Faucet> getFaucets() {
		return new ArrayList<Faucet>(faucets);
	}
	
	public void setFaucets(List<Faucet> newFaucets) {
		this.faucets.clear();
		this.faucets = newFaucets;
	}
	
	public List<Faucet> getFaucetsCurrency(Currency currency) {
		List<Faucet> rList = new ArrayList<Faucet>();
		for (Faucet f : getFaucets()) {
			if(f.getCurrency().equals(currency)) {
				rList.add(f);
			}
		}
		return rList; 
	}
}
