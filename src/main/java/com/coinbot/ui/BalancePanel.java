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
import javax.swing.JButton;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.TitledBorder;

public class BalancePanel extends JPanel {
	
	public BalancePanel() {
		setLayout(new MigLayout("", "[31px,grow]", "[]"));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Balances", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[grow][grow]", "[][][][][]"));
		
		JLabel lblBtc = new JLabel("BTC");
		panel.add(lblBtc, "cell 0 0");
		
		JLabel label = new JLabel("0.00000000");
		panel.add(label, "cell 1 0");
		
		JLabel lblLtc = new JLabel("LTC");
		panel.add(lblLtc, "cell 0 1");
		
		JLabel label_1 = new JLabel("0.00000000");
		panel.add(label_1, "cell 1 1");
		
		JLabel lblDoge = new JLabel("DOGE");
		panel.add(lblDoge, "cell 0 4");
		
		JLabel label_2 = new JLabel("0");
		panel.add(label_2, "cell 1 4");
		
		JLabel lblDash = new JLabel("DASH");
		panel.add(lblDash, "cell 0 2");
		
		JLabel label_3 = new JLabel("0.00000000");
		panel.add(label_3, "cell 1 2");
		
		JLabel lblEth = new JLabel("ETH");
		panel.add(lblEth, "cell 0 3");
		
		JLabel label_4 = new JLabel("0.00000000");
		panel.add(label_4, "cell 1 3");
		
	}
	
}
