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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

import com.coinbot.core.CoinbotApplication;

public class LogDialog {
	private JDialog dialog;
	private JTextArea area;

	public LogDialog() {
		area = new JTextArea();
	}
	
	public void showLog() {
		dialog = new JDialog(CoinbotApplication.ui.frame, "Logs");
		dialog.setModal(true);
		dialog.getContentPane().setLayout(new MigLayout("", "[600px,grow]", "[300px,grow][35px]"));
		
		JScrollPane scrollPane = new JScrollPane();
		dialog.getContentPane().add(scrollPane, "cell 0 0,grow");
		
		area.setEditable(false);
		scrollPane.setViewportView(area);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		dialog.getContentPane().add(btnClose, "cell 0 1,alignx right");
		
		dialog.pack();
		dialog.setLocationRelativeTo(CoinbotApplication.ui.frame);
		dialog.setVisible(true);
		
		area.setCaretPosition(area.getDocument().getLength());
	}
	
	public void append(String line) {
		area.append(line + "\n");
	}
}
