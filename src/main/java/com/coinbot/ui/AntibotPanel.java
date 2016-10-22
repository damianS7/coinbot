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
import java.util.List;

import javax.swing.JPanel;

import com.coinbot.antibot.AntibotPuzzle;
import com.coinbot.antibot.AntibotPuzzleLink;
import com.coinbot.antibot.MakejarV2;
import com.coinbot.antibot.PuzzleImageLink;

import net.miginfocom.swing.MigLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;



public class AntibotPanel extends JPanel {
	private static final long serialVersionUID = 332789023675768813L;
	private AntibotPuzzle ap;
	private List<JTextField> fields = new ArrayList<JTextField>();
	
	public AntibotPanel(AntibotPuzzle ap) {
		setLayout(new MigLayout("", "[grow,center][grow]", "[][][][][][][]"));
		
		JLabel puzzle = new JLabel();
		puzzle.setIcon(new ImageIcon(ap.getPuzzleImage()));
		add(puzzle, "cell 0 0 2 1");
		
		List<AntibotPuzzleLink> links = ap.getLinks();
		for (int i = 0; i < links.size(); i++) {
			JLabel label = new JLabel("-");
			AntibotPuzzleLink apl = links.get(i);
			if(ap instanceof MakejarV2) {
				PuzzleImageLink fl = (PuzzleImageLink) apl;
				ImageIcon icon = new ImageIcon(fl.getImage());
				label = new JLabel(icon);
			} else {
				label = new JLabel(apl.getContent());
			}
			
			add(label, "cell " + i + " 1,alignx center");
			JTextField field = new JTextField();
			add(field, "cell " + i + " 1,alignx center");
			field.setColumns(10);
			
			fields.add(field);
		}
				
		JLabel label_6 = new JLabel("-:-");
		add(label_6, "cell 0 6");
		
		JButton btnDone = new JButton("Done!");
		btnDone.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] order = new int[fields.size()];
				int index = 0;
				
				for (JTextField field : fields) {
					order[index] = Integer.parseInt(field.getText());
					index++;
				}
				
				ap.setOrder(order);
			}
		});
		add(btnDone, "cell 1 6,alignx center");
	}
	
	public AntibotPuzzle getAntibot() {
		return ap;
	}
}