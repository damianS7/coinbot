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
package com.coinbot.detector;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.coinbot.exceptions.DetectionException;

/**
 * Esta clase se usa para comprobar si una faucet realmente ha pagado.
 * Para evitar demasiadas peticiones a faucetbox, esta clase solo ha de usarse
 * una vez por faucet para comprobar si paga la primera vez. Despues de comprobar
 * el primer pago ya asumimos que nos va a pagar en las proximas veces.
 * @author danjian
 */
public class PaymentDetector implements Detector {
	private DecimalFormat df = new DecimalFormat("0.00000000");
	private String url = "https://faucetbox.com/en/check/";
	private WebDriver driver;
	private String address;
	private double initialBalance = 0.00000000;
	private double afterPayment = 0.00000000;
	// total acumalted
	
	public PaymentDetector(WebDriver driver, String address) {
		this.driver = driver;
		this.address = address;
	}
	
	public PaymentDetector() {
	}
	
	public void reset() {
		initialBalance = 0.00000000;
		afterPayment = 0.00000000;	
	}
	
	public void setInitialBalance(double n) {
		if(initialBalance == 0) {
			initialBalance = n;
		}
	}
	
	public void setAfterPayment(double n) {
		afterPayment = n;
	}
	
	// Devuelve la cantidad recibida en este pago
	public double getLastPayment() {
		return afterPayment-initialBalance;
	}
	
	// Devuel true si la faucet ha pagado, false si no
	public boolean isPaying() {
		if(getLastPayment() == 0) {
			return false;
		}
		return true;
	}
	
	// True si se ha recibido pago
	public boolean paymentReceived() {
		if(afterPayment > initialBalance) {
			return true;
		}
		return false;
	}
	
	public String doubleToString(double d) {
		return df.format(d);
	}
	
	public double getInitialBalance() {
		return initialBalance;
	}
	
	public double getAfterPayment() {
		return afterPayment;
	}
	
	public int toSatoshi(double n) {
		double r = n*100000000;
		return (int)r;
	}
	
	@Override
	public void detect () throws DetectionException {
		try {
			driver.manage().timeouts()
					.pageLoadTimeout(12, TimeUnit.SECONDS);
			driver.navigate().to(new URL(url + address));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			// Busca un elemento, si no lo encuentra que vuelva a cargar
			e.printStackTrace();
		}
		
		try {
			WebElement e = driver.findElement(By.className("data-pending"));
			setInitialBalance(Double.valueOf(e.getText()));
			setAfterPayment(Double.valueOf(e.getText()));
		} catch (NoSuchElementException e) { 
			
		}
		
	}
}
