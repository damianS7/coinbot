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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

import com.coinbot.faucet.Currency;

public class BalancePanel extends JPanel {
	private static final long serialVersionUID = 5862330573901127410L;
	private JLabel btc;
	private JLabel ltc;
	private JLabel doge;
	private JLabel dash;
	private JLabel eth;

	public BalancePanel() {
		setLayout(new MigLayout("", "[31px,grow]", "[]"));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Balances", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[grow][grow]", "[][][][][]"));
		
		JLabel lblBtc = new JLabel("BTC");
		panel.add(lblBtc, "cell 0 0");
		
		btc = new JLabel("0.00000000");
		panel.add(btc, "cell 1 0");
		
		JLabel lblLtc = new JLabel("LTC");
		panel.add(lblLtc, "cell 0 1");
		
		ltc = new JLabel("0.00000000");
		panel.add(ltc, "cell 1 1");
		
		JLabel lblDoge = new JLabel("DOGE");
		panel.add(lblDoge, "cell 0 4");
		
		doge = new JLabel("0.00000000");
		panel.add(doge, "cell 1 4");
		
		JLabel lblDash = new JLabel("DASH");
		panel.add(lblDash, "cell 0 2");
		
		dash = new JLabel("0.00000000");
		panel.add(dash, "cell 1 2");
		
		JLabel lblEth = new JLabel("ETH");
		panel.add(lblEth, "cell 0 3");
		
		eth = new JLabel("0.00000000");
		panel.add(eth, "cell 1 3");
	}
	
	public void setBalance(Currency currency, String amount) {
		switch (currency) {
		case BTC:
			btc.setText(amount);
			break;
		case LTC:
			ltc.setText(amount);
			break;
		case DOGE:
			doge.setText(amount);
			break;
		case DASH:
			dash.setText(amount);
			break;
		case ETH:
			eth.setText(amount);
			break;
		default:
			break;
		}
	}
	
}
