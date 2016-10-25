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

/**
 * Esta clase lee/guarda las direcciones de BTC de un archivo.
 * @author danjian
 */
public class AddressDatabase extends FileDatabase {
	private List<Address> addresses = new ArrayList<Address>();

	public AddressDatabase(File file) {
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
						addresses.add(new Address(Currency.valueOf(s[0]), s[1]));
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
		return addresses.size();
	}
	
	@Override
	public void save() {
		List<String> newLines = new ArrayList<String>();
		for (Address address : getAddresses()) {
			newLines.add(address.getCurrency().toString() + "=" 
					+ address.getAddress());
		}
		setLines(newLines);
		try {
			super.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Address> getAddresses() {
		return new ArrayList<Address>(addresses);
	}
	
	public List<Address> getAddressesCurrency(Currency currency) {
		List<Address> rList = new ArrayList<Address>();
		for (Address address : getAddresses()) {
			if(address.getCurrency().equals(currency)) {
				rList.add(address);
			}
		}
		return rList; 
	}
	
	public void addAddress(Currency currency, String address) {
		addresses.add(new Address(currency, address));
	}
	
	public void setAddress(List<Address> newAddresses) {
		this.addresses.clear();
		this.addresses = newAddresses;
	}
}
