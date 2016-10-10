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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import net.miginfocom.swing.MigLayout;

import com.coinbot.core.CoinbotApplication;


public class Preferences extends JDialog {
	
	public Preferences() {
		super(CoinbotApplication.ui.frame, "Preferences");
		setModal(true);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton btnCancel = new JButton("Cancel");
		panel.add(btnCancel);
		
		JButton btnSave = new JButton("Save");
		panel.add(btnSave);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new MigLayout("", "[grow][grow]", "[][][]"));
		
		JLabel lblThreads = new JLabel("Threads");
		panel_1.add(lblThreads, "cell 0 2");
		
		JSpinner spinner = new JSpinner();
		panel_1.add(spinner, "cell 1 2,growx");
		
		JLabel lblManualCaptcha = new JLabel("Manual captcha");
		panel_1.add(lblManualCaptcha, "cell 0 0");
		
		JCheckBox checkBox = new JCheckBox("");
		panel_1.add(checkBox, "cell 1 0,alignx right");
		
		JLabel lblCaptchaTimeout = new JLabel("Captcha timeout");
		panel_1.add(lblCaptchaTimeout, "cell 0 1");
		
		JSpinner spinner_1 = new JSpinner();
		panel_1.add(spinner_1, "cell 1 1,growx");
		pack();
		setLocationRelativeTo(CoinbotApplication.ui.frame);
		setVisible(true);
	}
	
	public void save() {
		//JassapServer.serverProperties.setAddress(address.getText());
		
		try {
			CoinbotApplication.coinbotProperties.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
		dispose();
	}
}
