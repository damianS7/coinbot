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

import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.BorderLayout;

import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.coinbot.core.CoinbotApplication;

import java.awt.Font;
import javax.swing.UIManager;

public class AboutDialog extends JDialog {
	private static final long serialVersionUID = 4981909357262396526L;
	
	public AboutDialog() {
		super(CoinbotApplication.ui.frame, "About Coinbot");
		setModal(true);
		setSize(1200, 600);
		JLabel lblCoinbotByDanjian = new JLabel(" Coinbot by Danjian ");
		lblCoinbotByDanjian.setFont(new Font("D3 Euronism Bold", Font.BOLD, 38));
		lblCoinbotByDanjian.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblCoinbotByDanjian, BorderLayout.NORTH);
		
		JTextArea area = new JTextArea();
		area.setEnabled(false);
		area.setEditable(false);
		area.setText("This is a bot developed by danjian");
		getContentPane().add(area, BorderLayout.CENTER);
		
		pack();
		setLocationRelativeTo(CoinbotApplication.ui.frame);
		setVisible(true);
	}
}
