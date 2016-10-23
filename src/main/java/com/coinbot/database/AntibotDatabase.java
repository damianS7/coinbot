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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.coinbot.antibot.Antibot;
import com.coinbot.antibot.MakejarV1;
import com.coinbot.core.CoinbotApplication;

public class AntibotDatabase {
	private List<Antibot> antibots = new ArrayList<Antibot>();
	
	public List<Antibot> getAntibots() {
		return antibots;
	}
	
	public void add(Antibot ab) {
		antibots.add(ab);
	}
	
	public int load() {
		File pwd = new File(CoinbotApplication.APP_PATH + "antibots/");
		
		for (File f : pwd.listFiles()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(f));
				Antibot ab = (Antibot) ois.readObject();
				ois.close();
				antibots.add(ab);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return antibots.size();
	}
	
	public void save() {
		for (Antibot antibot : antibots) {
			try {
				ObjectOutputStream oos = new ObjectOutputStream(
						new FileOutputStream(CoinbotApplication.APP_PATH 
								+ "antibots/" + System.currentTimeMillis() + ".ab"));
				oos.writeObject(antibot);
				oos.flush();
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		AntibotDatabase ad = new AntibotDatabase();
		MakejarV1 ab = new MakejarV1(null);
		System.out.println(ad.load());
		ad.add(ab);
		ad.save();
		
	}
}
