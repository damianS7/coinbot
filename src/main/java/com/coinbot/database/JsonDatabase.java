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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Esta clase se usa para interactuar con ficheros .json que usaremos como
 * base de datos para almacenar informacion
 * @author danjian
 */
public abstract class JsonDatabase {
	private File file;
	protected JSONObject jsonData;
	
	public JsonDatabase(File file) {
		this.file = file;
	}
	
	/**
	 * @return Un string con el JSON
	 */
	public String getJson() {
		return jsonData.toString(3);
	}

	/**
	 * Sobreescribe el fichero con el Json del objeto jsonData
	 */
	public void save() {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			fos.write(getJson().getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Carga el Json del fichero y lo transforma en un objeto Json para
	 * poder interactuar con el
	 * @return el numero de objetos json cargados
	 */
	public int load() {
		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			jsonData = new JSONObject(new String(bytes));
		} catch(JSONException e) {
			// Si hay algun error cargamos un objeto vacio
			jsonData = new JSONObject();
		}
		return jsonData.length();
	}
}
