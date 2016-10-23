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

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import com.coinbot.faucet.Faucet;


public class FaucetQueuePanel extends JPanel {
	private static final long serialVersionUID = 6383417013245947991L;
	private List<FaucetPanel> panels = new ArrayList<FaucetPanel>();
	private JPanel queuePanel;

	public FaucetQueuePanel() {
		setBorder(new TitledBorder(null, "Faucet queue", TitledBorder.LEADING, 
				TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		queuePanel = new JPanel();
		scrollPane.setViewportView(queuePanel);
		queuePanel.setLayout(new BoxLayout(queuePanel, BoxLayout.Y_AXIS));
	}
	
	public FaucetPanel getPanel(Faucet faucet) {
		for (FaucetPanel faucetPanel : panels) {
			if(faucetPanel.getFaucet() == faucet) {
				return faucetPanel;
			}
		}
		return null;
	}
	
	public void addFaucet(Faucet faucet) {
		FaucetPanel fp = new FaucetPanel(faucet);
		panels.add(fp);
		queuePanel.add(fp);
		queuePanel.revalidate();
	}
	
	public void removeFaucet(Faucet faucet) {
		FaucetPanel fp = getPanel(faucet);
		if(fp instanceof FaucetPanel) {
			panels.remove(fp);
			queuePanel.remove(fp);
			queuePanel.revalidate();
			queuePanel.repaint();
		}
	}
}
