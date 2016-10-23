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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class UI {
	public JFrame frame;
	public ResolverQueuePanel resolverQueue;
	public WorkerQueuePanel workerQueue;
	public FaucetQueuePanel faucetQueue;
	public BalancePanel stats;
	
	public UI() {
		setLookAndFeel();
		frame = new JFrame();
		resolverQueue = new ResolverQueuePanel();
		workerQueue = new WorkerQueuePanel();
		faucetQueue = new FaucetQueuePanel();
		frame.getContentPane().setLayout(new MigLayout("", "[160px]", "[573px,grow][85.00,grow]"));
		stats = new BalancePanel();
		frame.getContentPane().add(stats, "cell 0 0,alignx left,growy");
		initComponents();
	}
	
	private void initComponents() {
		frame.setSize(900, 600);
		frame.setTitle("Coinbot - Bitcoin faucet bot");
		//frame.setJMenuBar(bar.getBar());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
	}
	
	private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
        	e.printStackTrace();
        } catch (IllegalAccessException e) {
        	e.printStackTrace();
        }
    }
}