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

import com.coinbot.captcha.Captcha;

public class CaptchaQueuePanel extends JPanel {
	private static final long serialVersionUID = -8529873522109527318L;
	private List<CaptchaPanel> panels = new ArrayList<CaptchaPanel>();
	private JPanel queuePanel;
	
	public CaptchaQueuePanel() {
		setBorder(new TitledBorder(null, "Captcha queue", TitledBorder.LEADING, 
				TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		queuePanel = new JPanel();
		scrollPane.setViewportView(queuePanel);
		queuePanel.setLayout(new BoxLayout(queuePanel, BoxLayout.Y_AXIS));
	}
	
	public CaptchaPanel getCaptchaPanel(Captcha captcha) {
		for (CaptchaPanel panel : panels) {
			if(panel.getCaptcha() == captcha) {
				return panel;
			}
		}
		return null;
	}
	
	public void addCaptcha(Captcha captcha) {
		CaptchaPanel c = getCaptchaPanel(captcha); 
		if(c!=null) {
			return; // El captcha ya esta añadido
		}
		c = new CaptchaPanel(captcha);
		panels.add(c);
		queuePanel.add(c);
		queuePanel.revalidate();
	}
	
	public void removeCaptcha(Captcha captcha) {
		CaptchaPanel c = getCaptchaPanel(captcha);
		if(c==null) {
			return; // El captcha no esta añadido
		}
		panels.remove(c);
		queuePanel.remove(c);
		queuePanel.revalidate();
		queuePanel.repaint();
		//queuePanel.updateUI();
	}
}
