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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import com.coinbot.core.Worker;

import net.miginfocom.swing.MigLayout;

public class ClaimPanel extends JPanel {
	private JLabel proxy;
	private JProgressBar progressBar;
	private JLabel lblName;

	public ClaimPanel(String ip, String port, String faucet) {
        setMaximumSize(new Dimension((int) getMaximumSize().getWidth(), 35));
		setLayout(new MigLayout("", "[78px,grow][78px,grow][150px:150px]", "[20px]"));
		
		lblName = new JLabel(faucet);
		add(lblName, "cell 0 0,growx");
		
		proxy = new JLabel(ip + ":" + port);
		add(proxy, "cell 1 0,growx,aligny center");
		
		progressBar = new JProgressBar();
		add(progressBar, "cell 2 0,growx,aligny top");
		progressBar.setStringPainted(true);
	}
	
	public void nextStep(String step) {
		progressBar.setValue(progressBar.getValue()+1);
		progressBar.setString(step);
	}

	public void ready() {
		progressBar.setValue(progressBar.getMaximum());
		progressBar.setString("Ready!");
	}
	
	public void done() {
		progressBar.setValue(progressBar.getMaximum());
		progressBar.setString("Done!");
	}
	
	public void reset() {
		progressBar.setValue(0);
		progressBar.setString("");
	}
	
	public JProgressBar getProgressBar() {
		return progressBar;
	}
	
}
