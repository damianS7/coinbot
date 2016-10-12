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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.miginfocom.swing.MigLayout;

public class UI {
	public JFrame frame;
	public MenuBar bar;
	public ClaimQueuePanel claimQueue;
	public WorkerQueuePanel workerQueue;
	public CaptchaQueuePanel captchaQueue;
	public StatsPanel statsPanel;
	
	public UI() {
		setLookAndFeel();
		frame = new JFrame();
		bar = new MenuBar();
		captchaQueue = new CaptchaQueuePanel();
		workerQueue = new WorkerQueuePanel();
		statsPanel = new StatsPanel();
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new MigLayout("", "[grow][grow][350px:n:350px]", "[grow][grow]"));
		panel_1.add(workerQueue, "cell 0 0 2 1,grow");
		panel_1.add(captchaQueue, "cell 2 0 1 2,grow");
		claimQueue = new ClaimQueuePanel();
		panel_1.add(claimQueue, "cell 0 1 2 1,grow");
		frame.getContentPane().add(statsPanel, BorderLayout.SOUTH);
		initComponents();
	}
	
	private void initComponents() {
		frame.setSize(900, 600);
		frame.setTitle("Coinbot - Automatic claim bot");
		frame.setJMenuBar(bar.getBar());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
	}
	
	private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (InstantiationException e) {
            System.out.println(e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }
}
