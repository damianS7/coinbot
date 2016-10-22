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

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import com.coinbot.database.AddressDatabase;
import com.coinbot.database.AntibotDatabase;
import com.coinbot.database.CaptchaDatabase;
import com.coinbot.database.FaucetDatabase;
import com.coinbot.faucet.Stats;
import com.coinbot.ui.UI;

public class CoinbotApplication {
	private static final Logger LOGGER = Logger.getLogger(CoinbotApplication.class
			.getName());
	public static final String dirName = "coinbot/";
	public static CoinbotProperties coinbotProperties;
	public static AddressDatabase addressDatabase;
	public static FaucetDatabase faucetDatabase;
	public static CaptchaDatabase captchaDatabase;
	public static AntibotDatabase antibotDatabase;
	public static Stats stats;
	public static Coinbot bot;
	public static UI ui;
	
	public static void main(String[] args) throws IOException {
		LOGGER.info("Comprobando permisos de escritura ...");
		File pwd = new File(".");
		if(!pwd.canWrite()) {
			LOGGER.severe("No se puede escribir en " + pwd.getAbsolutePath());
			return;
		}
		
		LOGGER.info("Preparando ficheros necesarios para la aplicacion ...");
		File dir = new File(dirName);
		if(!dir.exists()) {
			dir.mkdir();
		}
		
		File bitcoinAddresses = new File(dirName + "btcaddresses.txt");
		if(!bitcoinAddresses.exists()) {
			try {
				bitcoinAddresses.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		
		File captcha = new File(dirName + "captchas.txt");
		if(!captcha.exists()) {
			try {
				captcha.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		
		File faucets = new File(dirName + "faucets.txt");
		if(!faucets.exists()) {
			try {
				faucets.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}

		File properties = new File(dirName + "coinbot.properties");
		if(!properties.exists()) {
			try {
				properties.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		
		File antibot = new File(dirName + "antibotpuzzle.json");
		if(!antibot.exists()) {
			try {
				antibot.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		
		stats = new Stats();
		addressDatabase = new AddressDatabase(bitcoinAddresses);
		LOGGER.info("Direcciones btc cargadas: " + addressDatabase.load());
		
		faucetDatabase = new FaucetDatabase(faucets);
		LOGGER.info("Faucets cargadas: " + faucetDatabase.load());
		
		captchaDatabase = new CaptchaDatabase(captcha);
		LOGGER.info("Captchas cargados: " + captchaDatabase.load());
		
		antibotDatabase = new AntibotDatabase(antibot);
		LOGGER.info("Antibots cargados: " + antibotDatabase.load());
		
		LOGGER.info("Cargando configuracion de la aplicacion");
		coinbotProperties = new CoinbotProperties(antibot);
		bot = new Coinbot();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ui = new UI();
			}
		});
	}
}
