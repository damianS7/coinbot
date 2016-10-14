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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import com.coinbot.core.CoinbotApplication;

public class Faucets extends JDialog {
	private JTextArea input;
	
	public Faucets() {
		super(CoinbotApplication.ui.frame, "Faucets");
		setModal(true);
		getContentPane().setLayout(new MigLayout("", "[300px:n,grow]", "[200px:n,grow][-17.00,grow][35px]"));
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "cell 0 0,grow");
		
		input = new JTextArea(CoinbotApplication.faucetDatabase.toString());
		scrollPane.setViewportView(input);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel, "cell 0 2,alignx right,aligny top");
		
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
		
		pack();
		setLocationRelativeTo(CoinbotApplication.ui.frame);
		setVisible(true);
	}
	
	public void save() {
		
		String[] faucets = input.getText().split("\n");
		List<String> faucetList = new ArrayList<String>();
		
		for (String faucet : faucets) {
			faucetList.add(faucet);
		}
		
		CoinbotApplication.faucetDatabase.setFaucets(faucetList);
		CoinbotApplication.faucetDatabase.save();
		dispose();
	}
}
