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
import java.util.logging.Logger;

import com.coinbot.database.AddressDatabase;
import com.coinbot.database.AntibotDatabase;
import com.coinbot.database.CaptchaDatabase;
import com.coinbot.database.CoinbotProperties;
import com.coinbot.database.FaucetDatabase;
import com.coinbot.ui.UI;

/**
 * Esta clase es el lanzador de la aplicacion, aqui comprobamos que la app
 * tiene lo minimo necesario para ejecutarse. Permisos, ficheros basicos, 
 * directorios etc ... Tambien inicializa objetos y carga configuraciones de
 * archivos.
 * @author danjian
 */
public class CoinbotApplication {
	private static Logger logger;
	public final static String APP_PATH = "coinbot/";
	public final static String LOG_PATH = APP_PATH + "logs/";
	public static CoinbotProperties config;
	public static AddressDatabase addressDatabase;
	public static FaucetDatabase faucetDatabase;
	public static CaptchaDatabase captchaDatabase;
	public static AntibotDatabase antibotDatabase;
	public static String browser = "/home/jian/Descargas/firefox46/bin/firefox";
	public static Coinbot bot;
	public static UI ui;

	public static void main(String[] args) throws Exception {
		// Ruta de la aplicacion
		File pwd = new File(APP_PATH);
		
		/*
		 * La app necesita permisos de escritura, a continuacion hemos de
		 * asegurarnos que podemos crear/modificar los ficheros antes de
		 * continuar 
		 */
		if (!pwd.canWrite()) {
			// ops! no se puede escribir :(
			throw new Exception("La app necesita poder escribir!");
		}

		// La app puede escribir :)
		
		/*
		 * La app guarda los logs en el directorio LOG_PATH, vamos a asignar
		 * un fichero.log para cada clase.
		 */
		logger = new CoinbotLogger(CoinbotApplication.class).getLogger();

		// Configuracion de la app
		File fileConfig = new File(APP_PATH + "coinbot.properties");
		config = new CoinbotProperties(fileConfig);
		logger.info("Configuracion cargada.");
		
		// Faucets
		File fileFaucets = new File(APP_PATH + "faucets.txt");
		faucetDatabase = new FaucetDatabase(fileFaucets);
		logger.info("Cargadas: " + faucetDatabase.load() + " faucets.");
		
		// Direcciones de BTC
		File fileAddress = new File(APP_PATH + "address.txt");
		addressDatabase = new AddressDatabase(fileAddress);
		logger.info("Cargadas: " + addressDatabase.load() + " direcciones.");
		
		// Captchas resueltos
		File fileCaptcha = new File(APP_PATH + "captchas.txt");
		captchaDatabase = new CaptchaDatabase(fileCaptcha);
		logger.info("Cargados: " + captchaDatabase.load() + " captchas.");
		
		// Captchas resueltos
		File fileAntibot = new File(CoinbotApplication.APP_PATH + "antibots/");
		antibotDatabase = new AntibotDatabase(fileAntibot);
		logger.info("Cargados: " + antibotDatabase.load() + " antibots.");
		
		
		bot = new Coinbot();
		
		// Todo funciona, lanzando UI!
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ui = new UI();
			}
		});
	}
}
