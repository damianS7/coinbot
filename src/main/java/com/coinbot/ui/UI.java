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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.miginfocom.swing.MigLayout;

public class UI {
	public JFrame frame;
	public CaptchaQueuePanel captchaQueue;
	public AntibotQueuePanel antibotQueue;
	public WorkerQueuePanel workerQueue;
	public ClaimQueuePanel claimQueue;
	public BalancePanel balance;
	private JPanel panel;
	private JPanel panel_1;
	private Menu menu;
	
	public UI() {
		setLookAndFeel();
		frame = new JFrame();
		frame.getContentPane().setLayout(new MigLayout("", "[160px][300px][grow]", "[573px,grow][85.00,grow]"));
		captchaQueue = new CaptchaQueuePanel();
		antibotQueue = new AntibotQueuePanel();
		workerQueue = new WorkerQueuePanel();
		claimQueue = new ClaimQueuePanel();
		balance = new BalancePanel();
		menu = new Menu();
		
		JPanel panel1 = new JPanel();
		frame.getContentPane().add(panel1, "cell 0 0,growy");
		
		panel1.setLayout(new MigLayout("", "[300px]", "[][grow]"));
		panel1.add(balance, "cell 0 0,growx,aligny top");
		panel1.add(menu, "cell 0 1,grow");
		
		panel = new JPanel();
		panel.setLayout(new MigLayout("", "[300px]", "[grow][grow]"));
		panel.add(captchaQueue, "cell 0 0,grow");
		panel.add(antibotQueue, "cell 0 1,grow");
		
		panel_1 = new JPanel();
		panel_1.setLayout(new MigLayout("", "[grow]", "[200px][grow]"));
		panel_1.add(workerQueue, "cell 0 0,grow");
		panel_1.add(claimQueue, "cell 0 1,grow");
		
		frame.getContentPane().add(panel, "cell 1 0,grow");
		frame.getContentPane().add(panel_1, "cell 2 0,grow");
		initComponents();
	}
	
	private void initComponents() {
		frame.setSize(1200, 600);
		frame.setTitle("Coinbot - Bitcoin faucet bot");
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