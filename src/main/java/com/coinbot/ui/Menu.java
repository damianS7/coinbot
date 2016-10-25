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

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;

import com.coinbot.core.CoinbotApplication;

public class Menu extends JPanel {
	public Menu() {
		setLayout(new MigLayout("", "[grow]", "[][][][][][][][]"));
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CoinbotApplication.bot.start();
			}
		});
		add(btnStart, "cell 0 0,growx,aligny center");
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CoinbotApplication.bot.stop();
			}
		});
		add(btnStop, "cell 0 1,growx");
		
		JButton btnPreferences = new JButton("Preferences");
		btnPreferences.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new PropertiesDialog();
			}
		});
		add(btnPreferences, "cell 0 2,growx");
		
		JButton btnAddresses = new JButton("Addresses");
		btnAddresses.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddressesDialog();
			}
		});
		add(btnAddresses, "cell 0 3,growx");
		
		JButton btnFaucets = new JButton("Faucets");
		btnFaucets.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new FaucetsDialog();
			}
		});
		add(btnFaucets, "cell 0 4,growx");
		
		JButton btnCaptchas = new JButton("Captchas");
		add(btnCaptchas, "cell 0 5,growx");
		
		JButton btnAntibot = new JButton("Antibot");
		add(btnAntibot, "cell 0 6,growx");
		
		JButton btnStats = new JButton("Stats");
		btnStats.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new StatsDialog();
			}
		});
		add(btnStats, "cell 0 7,growx");
		
		JButton btnAbout = new JButton("About");
		btnAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AboutDialog();
			}
		});
		add(btnAbout, "cell 0 8,growx");
	}

}
