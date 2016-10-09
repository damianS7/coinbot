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

import com.coinbot.database.ProxyDatabase;
import com.coinbot.ui.UI;


/**
 * Clase principal del bot
 * @author danjian
 */
public class Coinbot {
	private static final Logger LOGGER = Logger.getLogger(Coinbot.class
			.getName());
	public static final String dirName = "coinbot/";
	public static Coinbot bot;
	public static UI ui;
	public static CoinbotProperties coinbotProperties;
	public static ProxyDatabase proxyDatabase;
	
	public static void main(String[] args) {
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
		
		File proxy = new File(dirName + "proxy.txt");
		if(!proxy.exists()) {
			try {
				proxy.createNewFile();
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
		
		proxyDatabase = new ProxyDatabase(proxy);
		LOGGER.info("Proxies cargados: " + proxyDatabase.load());
		
		LOGGER.info("Cargando configuracion de la aplicacion");
		coinbotProperties = new CoinbotProperties();
		bot = new Coinbot();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ui = new UI();
			}
		});
	}
}
