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
package com.coinbot.faucet.list;

import java.util.ArrayList;
import java.util.List;

import com.coinbot.faucet.Faucet;

/**
 * Esta clase se usa para crear listas de faucets sobre las que hacer claims
 * @author danjian
 */
public class FaucetList {
	private List<Faucet> faucets = new ArrayList<Faucet>();
	
	public List<Faucet> getFaucets() {
		return faucets;
	}
	
	public void add(Faucet f) {
		faucets.add(f);
	}
	
	public void remove(Faucet f) {
		faucets.remove(f);
	}
}
