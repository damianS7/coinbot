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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Esta clase maneja la configuracion del servidor  que se encuentra almacenada
 * en un archivo. Lee y guarda las configuraciones.
 * @author danjian
 */
public class CoinbotProperties extends Properties {
	private static final long serialVersionUID = 8607944448799338338L;
	private File file;
	
	// Flag para indicar si ha sido modificado alguna opcion
	private boolean modyfied = false;
	
	// Campos obligatorios
	private String[][] fields = { { "workers", "1" },
			{ "2captcha_enabled", "false" }, { "2captcha_api", "123" },
			{ "deathbycaptcha_enabled", "false" }, { "deathbycaptcha_api", "1234" },
			{ "captcha_queue_enabled", "true" },
			{ "captcha_timeout", "60" },
			{ "rotator_ifaucet", "http://ifaucet.net/" },
			{ "rotator_makejar", "http://bit.makejar.com/" }
			};

	public CoinbotProperties(File file) throws IOException {
		this.file = file;
		
		// Se carga el archivo
		load(new FileInputStream(file));

		/*
		 * Configuracion obligatoria en el archivo, si no esta se añade.
		 */
		for (String v[] : fields) {
			// Clave no encontrada
			if (getProperty(v[0]) == null) {
				// Se añade la clave
				setProperty(v[0], v[1]);
				modyfied = true;
			}
		}

		// Si faltaban campos se guarda el fichero
		if (modyfied) {
			save();
		}
	}

	// Guarda en un fichero
	public void save() throws IOException {
		store(new FileOutputStream(file), "Coinbot properties");
	}

	public String get2CaptchaAPI() {
		return getProperty("2captcha_api");
	}
	
	public void set2CaptchaAPI(String api) {
		setProperty("2captcha_api", api);
	}
	
	public String getDeathByCaptchaAPI() {
		return getProperty("deathbycaptcha_api");
	}
	
	public void setDeathByCaptcha(boolean enabled) {
		setProperty("deathbycaptcha_enabled", Boolean.toString(enabled));
	}
	
	public void setDeathByCaptchaAPI(String api) {
		setProperty("deathbycaptcha_api", api);
	}
	
	public boolean isDeathByCaptchaEnabled() {
		return Boolean.parseBoolean(getProperty("deathbycaptcha_enabled"));
	}
	
	public int getCaptchaTimeout() {
		return Integer.parseInt(getProperty("captcha_timeout"));
	}
	
	public void setCaptchaTimeout(int timeout) {
		setProperty("captcha_timeout", Integer.toString(timeout));
	}
	
	public void set2Captcha(boolean enabled) {
		setProperty("2captcha_enabled", Boolean.toString(enabled));
	}
	
	public boolean is2CaptchaEnabled() {
		return Boolean.parseBoolean(getProperty("2captcha_enabled"));
	}

	public void setWorkers(String threads) {
		setProperty("workers", threads);
	}

	public int getWorkers() {
		return Integer.parseInt(getProperty("workers"));
	}

	public void setCaptchaQueue(boolean bool) {
		setProperty("captcha_queue_enabled", Boolean.toString(bool));
	}

	public boolean isCaptchaQueueEnabled() {
		return Boolean.parseBoolean(getProperty("captcha_queue_enabled"));
	}
}
