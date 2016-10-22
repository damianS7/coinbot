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

import com.coinbot.faucet.Claim;

public class ClaimQueuePanel extends JPanel {
	private static final long serialVersionUID = 6383417013245947991L;
	private JPanel queuePanel;
	private List<ClaimPanel> panels = new ArrayList<ClaimPanel>();

	public ClaimQueuePanel() {
		setBorder(new TitledBorder(null, "Faucet queue", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		queuePanel = new JPanel();
		scrollPane.setViewportView(queuePanel);
		queuePanel.setLayout(new BoxLayout(queuePanel, BoxLayout.Y_AXIS));
	}
	
	public ClaimPanel getClaimPanel(Claim claim) {
		for (ClaimPanel panel : panels) {
			if(panel.getClaim() == claim) {
				return panel;
			}
		}
		return null;
	}
	
	public void addClaim(Claim claim) {
		ClaimPanel c = getClaimPanel(claim); 
		if(c!=null) {
			return;
		}
		c = new ClaimPanel(claim);
		panels.add(c);
		queuePanel.add(c);
		queuePanel.revalidate();
	}
	
	public void removeClaim(Claim claim) {
		ClaimPanel c = getClaimPanel(claim); 
		if(c!=null) {
			return;
		}
		
		panels.remove(c);
		queuePanel.remove(c);
		queuePanel.revalidate();
		queuePanel.repaint();
	}
}
