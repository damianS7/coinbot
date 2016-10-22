
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

package com.coinbot.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.coinbot.core.CoinbotApplication;

public class MenuBar {
	private JMenuBar menu;
	
	public MenuBar() {
		menu = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		menu.add(menuFile);
		JMenuItem fileStart = new JMenuItem("Start");
		menuFile.add(fileStart);
		fileStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CoinbotApplication.bot.start();
			}
		});
		
		JMenuItem fileStop = new JMenuItem("Stop");
		fileStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CoinbotApplication.bot.stop();
			}
		});
		menuFile.add(fileStop);
		
		JMenuItem fileExit = new JMenuItem("Exit");
		fileExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CoinbotApplication.bot.stop();
				System.exit(0);
			}
		});
		menuFile.add(fileExit);
		
		
		JMenu menuEdit = new JMenu("Edit");
		menu.add(menuEdit);
		JMenuItem editPreferences = new JMenuItem("Preferences");
		editPreferences.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Preferences();
			}
		});
		menuEdit.add(editPreferences);
		
		JMenuItem editAddress = new JMenuItem("BTC Addresses");
		editAddress.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Addresses();
			}
		});
		menuEdit.add(editAddress);
		
		JMenuItem editFaucets = new JMenuItem("Faucets");
		editFaucets.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Faucets();
			}
		});
		menuEdit.add(editFaucets);
		
		JMenuItem editStats = new JMenuItem("Stats");
		editStats.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Stats();
			}
		});
		menuEdit.add(editStats);
		
		JMenu menuHelp = new JMenu("Help");
		menu.add(menuHelp);
		JMenuItem helpAbout = new JMenuItem("About");
		helpAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new About();
			}
		});
		menuHelp.add(helpAbout);
	}
		
	public JMenuBar getBar() {
		return menu;
	}

}
