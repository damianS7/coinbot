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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import com.coinbot.captcha.Captcha;

public class CaptchaPanel extends JPanel {
	private Captcha captcha;
	private static final long serialVersionUID = 1868852228497027988L;
	private JTextField input;
	private JLabel timer;
	
	public CaptchaPanel(Captcha captcha) {
		this.captcha = captcha;
		setLayout(new MigLayout("", "[grow,center]", "[][]"));
		
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(captcha.getImage()));
		add(label, "cell 0 0");
		
		input = new JTextField();
		input.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					captcha.resolve(input.getText());
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					captcha.resolve(input.getText());
				}
			}
		});
		JPanel panel = new JPanel();
		add(panel, "cell 0 1,growx");
		panel.setLayout(new MigLayout("", "[114px,grow][20px,center]", "[19px]"));
		
		panel.add(input, "cell 0 0,growx,aligny top");
		input.setColumns(10);
		
		timer = new JLabel("-");
		panel.add(timer, "cell 1 0,alignx center,aligny center");
	}
	
	public Captcha getCaptcha() {
		return captcha;
	}
	
	public void setTimer(int t) {
		timer.setText(Integer.toString(t));
	}
}
