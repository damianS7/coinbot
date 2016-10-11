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
import java.awt.image.BufferedImage;

import javax.swing.JDialog;

import net.miginfocom.swing.MigLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

import com.coinbot.core.CoinbotApplication;

public class CaptchaPanel extends JPanel {
	private JTextField input;
	
	public CaptchaPanel(BufferedImage image) {
		setLayout(new MigLayout("", "[grow]", "[grow][]"));
		
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(image));
		add(label, "cell 0 0");
		
		input = new JTextField();
		input.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//CoinbotApplication.
			}
		});
		add(input, "cell 0 1,growx");
		input.setColumns(10);
	}

}
