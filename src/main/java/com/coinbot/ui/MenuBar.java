
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
				start();
			}
		});
		
		JMenuItem fileStop = new JMenuItem("Stop");
		fileStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		menuFile.add(fileStop);
		
		JMenuItem fileExit = new JMenuItem("Exit");
		fileExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		menuFile.add(fileExit);
		
		
		JMenu menuEdit = new JMenu("Edit");
		menu.add(menuEdit);
		JMenuItem editPreferences = new JMenuItem("Preferences");
		editPreferences.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editPreferences();
			}
		});
		menuEdit.add(editPreferences);
		
		JMenu menuHelp = new JMenu("Help");
		menu.add(menuHelp);
		JMenuItem helpAbout = new JMenuItem("About");
		helpAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAbout();
			}
		});
		menuHelp.add(helpAbout);
	}
		
	public JMenuBar getBar() {
		return menu;
	}

	protected void editPreferences() {
		new Preferences();
	}
	protected void start() {
		CoinbotApplication.bot.start();
	}
	
	protected void stop() {
		CoinbotApplication.bot.stop();
	}
	
	protected void showAbout() {
		new About();
	}
	
	protected void exit() {
		stop();
		System.exit(0);
	}
}
