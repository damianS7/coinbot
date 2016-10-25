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
import javax.swing.JTextField;


public class PropertiesDialog extends JDialog {
	private static final long serialVersionUID = 1488391075068984462L;
	private JCheckBox autoCaptcha;
	private JSpinner workers;
	private JTextField twoCaptchaAPI;
	private JTextField deathByCaptchaAPI;
	private JCheckBox twoCaptcha;
	private JSpinner captchaTimeout;
	private JCheckBox deathByCaptcha;

	public PropertiesDialog() {
		super(CoinbotApplication.ui.frame, "Preferences");
		setModal(true);
		getContentPane().setLayout(new MigLayout("", "[grow]", "[85px][35px]"));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 0 1,alignx right,aligny top");
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
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
		panel_1.setLayout(new MigLayout("", "[150px:n,grow][150,grow,left]", "[][][][][][][]"));
		
		JLabel lblcaptcha = new JLabel("2Captcha");
		panel_1.add(lblcaptcha, "cell 0 2");
		
		twoCaptcha = new JCheckBox();
		twoCaptcha.setSelected(CoinbotApplication.config.is2CaptchaEnabled());
		panel_1.add(twoCaptcha, "cell 1 2,alignx right");
		
		JLabel lblcaptchaApi = new JLabel("2Captcha API");
		panel_1.add(lblcaptchaApi, "cell 0 3,alignx left");
		
		twoCaptchaAPI = new JTextField(CoinbotApplication.config.get2CaptchaAPI());
		panel_1.add(twoCaptchaAPI, "cell 1 3,growx");
		twoCaptchaAPI.setColumns(10);
		
		JLabel lblDeathbycaptcha = new JLabel("DeathByCaptcha");
		panel_1.add(lblDeathbycaptcha, "cell 0 4");
		
		deathByCaptcha = new JCheckBox();
		deathByCaptcha.setSelected(CoinbotApplication.config.isDeathByCaptchaEnabled());
		panel_1.add(deathByCaptcha, "cell 1 4,alignx right");
		
		JLabel lblDeathbycaptchaApi = new JLabel("DeathByCaptcha API");
		panel_1.add(lblDeathbycaptchaApi, "cell 0 5,alignx left");
		
		deathByCaptchaAPI = new JTextField(CoinbotApplication.config.getDeathByCaptchaAPI());
		panel_1.add(deathByCaptchaAPI, "cell 1 5,growx");
		deathByCaptchaAPI.setColumns(10);
		
		JLabel lblThreads = new JLabel("Workers");
		panel_1.add(lblThreads, "cell 0 6,growx");
		
		workers = new JSpinner();
		workers.getModel().setValue(CoinbotApplication.config.getWorkers());
		panel_1.add(workers, "flowy,cell 1 6,growx");
		
		JLabel lblAutoCaptcha = new JLabel("Captcha Queue");
		panel_1.add(lblAutoCaptcha, "cell 0 0,growx");
		
		autoCaptcha = new JCheckBox("");
		autoCaptcha.setSelected(CoinbotApplication.config.isCaptchaQueueEnabled());
		panel_1.add(autoCaptcha, "cell 1 0,alignx right");
		
		JLabel lblCaptchaTimeout = new JLabel("Captcha timeout");
		panel_1.add(lblCaptchaTimeout, "cell 0 1,growx");
		
		captchaTimeout = new JSpinner();
		captchaTimeout.getModel().setValue(CoinbotApplication.config.getCaptchaTimeout());
		panel_1.add(captchaTimeout, "cell 1 1,growx");
		
		pack();
		setLocationRelativeTo(CoinbotApplication.ui.frame);
		setVisible(true);
	}
	
	public void save() {
		CoinbotApplication.config.setCaptchaQueue(autoCaptcha.isSelected());
		CoinbotApplication.config.setWorkers(workers.getValue().toString());
		CoinbotApplication.config.setCaptchaTimeout(Integer.parseInt(captchaTimeout.getValue().toString()));
		CoinbotApplication.config.set2Captcha(twoCaptcha.isSelected());
		CoinbotApplication.config.set2CaptchaAPI(twoCaptchaAPI.getText());
		CoinbotApplication.config.setDeathByCaptcha(deathByCaptcha.isSelected());
		CoinbotApplication.config.setDeathByCaptchaAPI(deathByCaptchaAPI.getText());
		
		try {
			CoinbotApplication.config.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
		dispose();
	}
}
