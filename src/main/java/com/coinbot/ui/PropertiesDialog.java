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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import com.coinbot.core.CoinbotApplication;


public class PropertiesDialog extends JDialog {
	private static final long serialVersionUID = 1488391075068984462L;
	private JCheckBox autoCaptcha;
	private JSpinner workers;

	public PropertiesDialog() {
		super(CoinbotApplication.ui.frame, "Preferences");
		setModal(true);
		getContentPane().setLayout(new MigLayout("", "[grow]", "[85px][35px]"));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel, "cell 0 1,alignx right,aligny top");
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.add(btnCancel);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		btnSave.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(btnSave);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, "cell 0 0,growx,aligny top");
		panel_1.setLayout(new MigLayout("", "[150px:n,grow][150,grow,left]", "[][][]"));
		
		JLabel lblThreads = new JLabel("Workers");
		panel_1.add(lblThreads, "cell 0 2,growx");
		
		workers = new JSpinner();
		workers.getModel().setValue(CoinbotApplication.config.getWorkers());
		panel_1.add(workers, "flowy,cell 1 2,alignx right");
		
		JLabel lblAutoCaptcha = new JLabel("Auto-captcha");
		panel_1.add(lblAutoCaptcha, "cell 0 0,growx");
		
		autoCaptcha = new JCheckBox("");
		autoCaptcha.setSelected(CoinbotApplication.config.isCaptchaQueueEnabled());
		panel_1.add(autoCaptcha, "cell 1 0,alignx right");
		
		JLabel lblCaptchaTimeout = new JLabel("Captcha timeout");
		panel_1.add(lblCaptchaTimeout, "cell 0 1,growx");
		
		JSpinner spinner_1 = new JSpinner();
		panel_1.add(spinner_1, "cell 1 1,alignx right");
		
		pack();
		setLocationRelativeTo(CoinbotApplication.ui.frame);
		setVisible(true);
	}
	
	public void save() {
		CoinbotApplication.config.setCaptchaQueue(autoCaptcha.isSelected());
		CoinbotApplication.config.setWorkers(workers.getValue().toString());
		
		try {
			CoinbotApplication.config.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
		dispose();
	}
}
