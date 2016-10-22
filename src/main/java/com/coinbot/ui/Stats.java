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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.coinbot.core.CoinbotApplication;

public class Stats extends JDialog {
	private static final long serialVersionUID = -975309551293483252L;

	public Stats() {
		super(CoinbotApplication.ui.frame, "Stats");
		setModal(true);
		getContentPane().setLayout(
				new MigLayout("", "[300px:n,grow]", "[100px:n][35px]"));

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, "cell 0 0,grow");
		panel_1.setLayout(new MigLayout("", "[grow][grow]", "[][][]"));

		JLabel lblCollected = new JLabel("Collected");
		panel_1.add(lblCollected, "cell 0 0");

		JLabel collected = new JLabel(
				CoinbotApplication.stats.getCollectedString());
		panel_1.add(collected, "cell 1 0,alignx right");

		JLabel lblClaims = new JLabel("Success claims");
		panel_1.add(lblClaims, "cell 0 1");

		JLabel successClaims = new JLabel(
				Integer.toString(CoinbotApplication.stats.countSuccessClaims()));
		panel_1.add(successClaims, "cell 1 1,alignx right");

		JLabel lblFailedClaims = new JLabel("Failed claims");
		panel_1.add(lblFailedClaims, "cell 0 2");

		JLabel failedClaims = new JLabel(
				Integer.toString(CoinbotApplication.stats.countFailedClaims()));
		panel_1.add(failedClaims, "cell 1 2,alignx right");

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel, "cell 0 1,grow");

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.add(btnCancel);

		pack();
		setLocationRelativeTo(CoinbotApplication.ui.frame);
		setVisible(true);
	}
}
