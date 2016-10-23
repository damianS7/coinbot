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
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Lee/Escribe datos en un archivo  que se usa como almacen de informacion
 * para la app
 * @author danjian
 */
public abstract class FileDatabase {
	private List<String> lines = new ArrayList<String>();
	private File file;
	
	public FileDatabase(File file) {
		this.file = file;
	}
	
	/**
	 * Devuelve la lineas actuales
	 * @return
	 */
	public List<String> getLines() {
		return new ArrayList<String>(lines);
	}
	
	/**
	 * Añade una nueva linea
	 * @param line
	 */
	public void addLine(String line) {
		lines.add(line);
	}
	
	/**
	 * Sobreescribe las lineas anteriores por estas
	 * @param newLines
	 */
	public void setLines(List<String> newLines) {
		lines = newLines;
		/*lines.clear();
		for (String line : newLines) {
			lines.add(line);
		}*/
	}
	
	/**
	 * Devuelve el buffer temporal de lineas en una cadena de texto
	 */
	public String toString() {
		String s = "";
		
		for (String line : lines) {
			s += line + "\n";
		}

		// Devuelve y quita el ultimo \n
		return s.substring(0, s.length()-1);
	}
	
	/**
	 * Escribe las lineas en el fichero
	 */
	public void save() throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(toString().getBytes());
		fos.close();
	}
	
	/**
	 * Carga el fichero
	 * @return Devuelve el numero de lineas leidas
	 */
	public int load () throws IOException {
		List<String> temp = new ArrayList<String>();
		temp = Files.readAllLines(Paths.get(file.getAbsolutePath()));
		
		for (String line : temp) {
			if(!line.isEmpty()) {
				String newLine = line.trim();
				if(!lines.contains(newLine)) {
					lines.add(newLine);
				}
			}
		}
		return lines.size();
	}
}
