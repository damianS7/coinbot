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

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import com.coinbot.core.WorkerThread;

import net.miginfocom.swing.MigLayout;

public class FaucetClaimPanel extends JPanel {
	private JLabel proxyIp;
	private JLabel proxyPort;
	private JProgressBar progressBar;

	public FaucetClaimPanel(String ip, String port, String faucet) {
        setMaximumSize(new Dimension((int) getMaximumSize().getWidth(), 35));
		setLayout(new MigLayout("", "[78px,grow][5px][78px,grow][148px,grow]", "[20px]"));
        
		proxyIp = new JLabel(ip);
		add(proxyIp, "cell 0 0,alignx left,aligny center");
		
		JLabel label_2 = new JLabel(":");
		add(label_2, "cell 1 0,alignx left,aligny center");
		
		proxyPort = new JLabel(port);
		add(proxyPort, "cell 2 0,alignx left,aligny center");
		
		progressBar = new JProgressBar();
		progressBar.setMaximum(4);
		progressBar.setStringPainted(true);
		progressBar.setString(faucet);
		add(progressBar, "cell 3 0,growx,aligny top");
	}
	
	public void ready() {
		progressBar.setStringPainted(true);
		progressBar.setString("Ready!");
		progressBar.setValue(progressBar.getMaximum());
	}
	
	public void done() {
		progressBar.setStringPainted(true);
		progressBar.setString("Done!");
		progressBar.setValue(progressBar.getMaximum());
	}
	
	public JProgressBar getProgressBar() {
		return progressBar;
	}
}
