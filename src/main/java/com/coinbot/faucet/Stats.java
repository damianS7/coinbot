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

import java.text.DecimalFormat;

public class Stats {
	private DecimalFormat df = new DecimalFormat("0.00000000");
	//private Uptimer uptime;
	private double collected = 0.00000000;
	private int successClaims = 0;
	private int failedClaims = 0;
	
	public void reset() {
		collected = 0.00000000;
		successClaims = 0;
		failedClaims = 0;
	}
	
	public double getCollected() {
		return collected;
	}
	
	public String getCollectedString() {
		return df.format(getCollected());
	}
	
	/**
	 * @return El numero de claims realizados con exito
	 */
	public int countSuccessClaims() {
		return successClaims;
	}
	
	/**
	 * @return El numero de claims fallados
	 */
	public int countFailedClaims() {
		return failedClaims;
	}
	
	/**
	 * Este metodo se utiliza para avisar de que el claim se realizo con exito
	 * por tanto se cuenta en la estadistica
	 * @param q La cantidad que se ha cobrado
	 * @return El numero de claims hechos con exito
	 */
	public int claimSuccessfull(double q) {
		successClaims++;
		collected+=q;
		return successClaims;
	}
	
	/**
	 * Añade a la estadistica un claim fallado
	 * @return El numero de claims fallados
	 */
	public int claimFailed() {
		failedClaims++;
		return failedClaims;
	}	
}
