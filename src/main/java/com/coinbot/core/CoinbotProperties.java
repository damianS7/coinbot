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
 * 
 * @author danjian
 */
public class CoinbotProperties extends Properties {
	private boolean modyfied = false;
	private File file;
	private String[][] fields = { { "timeout", "15" }, { "max_threads", "4" },
			{ "max_connections", "200" }, { "manual_captcha", "true" },
			{ "captcha_timeout", "30" } };

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

	public void setMaxThreads(String threads) {
		setProperty("max_threads", threads);
	}

	public String getMaxThreads() {
		return getProperty("max_threads");
	}
	
	public void setManualCaptcha(String captcha) {
		setProperty("manual_captcha", captcha);
	}
}
