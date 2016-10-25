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

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class WorkerPanel extends JPanel {
	private static final long serialVersionUID = 5648986356219872274L;
	private ClaimPanel last;
	
	public WorkerPanel(String id) {
		TitledBorder border = new TitledBorder(null, "Worker #" + id, 
				TitledBorder.LEADING, TitledBorder.TOP, null, null);
		setBorder(border);
		setLayout(new BorderLayout(0, 0));
	}
	
	public void addPanel(ClaimPanel fp) {
		if(last != null) {
			removePanel(last);
		}
		
		last = fp;
		add(fp, BorderLayout.CENTER);
		revalidate();
	}
	
	public void removePanel(ClaimPanel cp) {
		remove(cp);
		revalidate();
	}
}
