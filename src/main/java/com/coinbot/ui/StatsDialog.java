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

import com.coinbot.core.CoinbotApplication;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

public class StatsDialog extends JDialog {
	private static final long serialVersionUID = 1291611127085806611L;
	
	public StatsDialog() {
		super(CoinbotApplication.ui.frame, "Stats");
		setModal(true);
		getContentPane().setLayout(new MigLayout("", "[grow][grow]", "[][grow][]"));
		
		JLabel label = new JLabel("New label");
		getContentPane().add(label, "cell 0 0,alignx right");
		
		JLabel lblNewLabel = new JLabel("New label");
		getContentPane().add(lblNewLabel, "cell 1 0,alignx right");
		
		JButton btnCancel = new JButton("Cancel");
		getContentPane().add(btnCancel, "cell 1 2,alignx right");
		
		//pack();
		setLocationRelativeTo(CoinbotApplication.ui.frame);
		setVisible(true);
	}
}
