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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import net.miginfocom.swing.MigLayout;

import com.coinbot.antibot.Antibot;
import com.coinbot.antibot.AntibotLink;

public class AntibotPanel extends JPanel {
	private static final long serialVersionUID = 1868852228497027988L;
	private Antibot antibot;
	private JLabel timer;
	private List<JSpinner> spinners = new ArrayList<JSpinner>();
	
	public AntibotPanel(Antibot ab) {
		this.antibot = ab;
		setLayout(new MigLayout("", "[grow,center][grow]", "[][][]"));
		
		JLabel puzzle = new JLabel();
		puzzle.setIcon(new ImageIcon(ab.getImage()));
		add(puzzle, "cell 0 0 2 1");
		
		int index = 1;
		for (AntibotLink abl : ab.getLinks()) {
			JLabel l = new JLabel();
			l.setIcon(new  ImageIcon(abl.getImage()));
			add(l, "cell 0 " + index);
			
			JSpinner spinner = new JSpinner();
			spinner.setModel(new SpinnerNumberModel(index, 1, 5, 1));
			spinners.add(spinner);
			add(spinner, "cell 1 " + index + ",growx");
			
			index++;
		}
		
		timer = new JLabel();
		add(timer, "cell 0 " + index+1);
		
		JButton btnDone = new JButton("Done!");
		btnDone.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int size = spinners.size();
				int[] order = new int[size];
				
				
				for (int i = 0; i < order.length; i++) {
					int x = Integer.parseInt(spinners.get(i).getValue()
							.toString());
					order[i] = x-1;
				}
				
				antibot.setOrder(order);
				antibot.getTimer().stop();
			}
		});
		add(btnDone, "cell 1 " + index+1 + ",growx");
	}
	
	public Antibot getAntibot() {
		return antibot;
	}
	
	public void setTimer(int t) {
		timer.setText(Integer.toString(t));
	}
}
