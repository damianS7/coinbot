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

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;

public class Menu extends JPanel {
	public Menu() {
		setLayout(new MigLayout("", "[grow]", "[][][][][][][][]"));
		
		JButton btnStart = new JButton("Start");
		add(btnStart, "cell 0 0,growx,aligny center");
		
		JButton btnStop = new JButton("Stop");
		add(btnStop, "cell 0 1,growx");
		
		JButton btnPreferences = new JButton("Preferences");
		add(btnPreferences, "cell 0 2,growx");
		
		JButton btnAddresses = new JButton("Addresses");
		add(btnAddresses, "cell 0 3,growx");
		
		JButton btnFaucets = new JButton("Faucets");
		add(btnFaucets, "cell 0 4,growx");
		
		JButton btnCaptchas = new JButton("Captchas");
		add(btnCaptchas, "cell 0 5,growx");
		
		JButton btnAntibot = new JButton("Antibot");
		add(btnAntibot, "cell 0 6,growx");
		
		JButton btnStats = new JButton("Stats");
		add(btnStats, "cell 0 7,growx");
	}

}
