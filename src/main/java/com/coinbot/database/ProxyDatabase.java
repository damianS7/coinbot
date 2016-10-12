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
import java.util.ArrayList;
import java.util.List;

import com.coinbot.proxy.Proxy;

public class ProxyDatabase {
	private File file;
	private List<Proxy> proxies = new ArrayList<Proxy>();
	
	public ProxyDatabase(File file) {
		this.file = file;
	}
	
	public void removeProxy(Proxy p) {
		proxies.remove(p);
	}
	
	public void addProxy(Proxy p) {
		proxies.add(p);
	}
	
	public List<Proxy> getProxies() {
		return new ArrayList<Proxy>(proxies);
	}
	
	public String toString() {
		String s = "";
		
		for (Proxy proxy : proxies) {
			s += proxy.getAddress() + ":" + proxy.getPort() + "\n";
		}

		return s.substring(0, s.length()-1);
	}
	
	public int load() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(file.getAbsolutePath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(lines == null) {
			return 0;
		}
		
		for (String line : lines) {
			
			if(line.isEmpty()) {
				continue;
			}
			line = line.trim();
			
			if(line.contains(":")) {
				String[] s = line.split(":");
				
				if(s.length == 1) {
					addProxy(new Proxy(s[0], 80));
				}
				
				if(s.length == 2) {
					addProxy(new Proxy(s[0], s[1]));
				}
				
			} else {
				addProxy(new Proxy(line, 80));
			}
		}			
		
		return proxies.size();
	}
	
	public void save() {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(toString().getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
