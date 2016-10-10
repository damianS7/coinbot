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

public class FileDatabase {
	private List<String> lines = new ArrayList<String>();
	private File file;
	
	public FileDatabase(File file) {
		this.file = file;
	}
	
	public String toString() {
		String s = "";
		
		for (String line : lines) {
			s += line + "\n";
		}

		return s.substring(0, s.length()-1);
	}
	
	public List<String> getLines() {
		return new ArrayList<String>(lines);
	}
	
	public void addLine(String line) {
		lines.add(line);
	}
	
	public void setLines(List<String> newLines) {
		lines.clear();
		for (String line : newLines) {
			lines.add(line);
		}
	}
	
	public void save() {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int load() {
		List<String> loadedLines = new ArrayList<String>();
		try {
			loadedLines = Files.readAllLines(Paths.get(file.getAbsolutePath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (String line : loadedLines) {
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
