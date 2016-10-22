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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase para leer y almacenar datos de configuracion del bot
 * @author danjian
 */
public class CoinbotProperties extends Properties {
	private static final long serialVersionUID = 8607944448799338338L;
	private boolean modyfied = false;
	private File file;
	private String[][] fields = { { "timeout", "15" }, { "workers", "1" },
			{ "2captcha_enabled", "false" }, { "2captcha_api", "" },
			{ "captcha_queue_enabled", "false" },
			{ "use_rotator_faucets", "false" } };

	public CoinbotProperties(File file) throws IOException {
		this.file = file;
		load(new FileInputStream(file));

		// Comprueba que las claves obligatorias estan en el fichero de
		// configuracion
		for (String v[] : fields) {
			// Clave no encontrada
			if (getProperty(v[0]) == null) {
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
	
	public void set2Captcha(boolean active) {
		setProperty("2captcha_enabled", Boolean.toString(active));
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
