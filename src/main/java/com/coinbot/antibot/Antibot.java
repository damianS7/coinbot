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
package com.coinbot.antibot;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.coinbot.core.CoinbotApplication;

public class Antibot implements Serializable {
	private static final long serialVersionUID = 7468098572515422144L;
	private AntibotTimer timer;
	private List<AntibotLink> links = new ArrayList<AntibotLink>();
	private BufferedImage image;
	private int[] order;

	public Antibot(BufferedImage image) {
		this.image = image;
		this.timer = new AntibotTimer(CoinbotApplication.config.getCaptchaTimeout());
		this.timer.start();
	}
	
	public AntibotTimer getTimer() {
		return timer;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public List<AntibotLink> getLinks() {
		return links;
	}
	
	public void addLink(AntibotLink al) {
		links.add(al);
	}
	
	public int[] getOrder() {
		return order;
	}
	
	public void setOrder(int[] order) {
		this.order = order;
	}
	
	public boolean isResolved() {
		if(order==null) {
			return false;
		}
		return true;
	}
	
	public boolean isExpired() {
		if(timer.hasFinished()) {
			return true;
		}
		return false;
	}
	
	public void resolve() {
		for (int x : order) {
			links.get(x).getLink().click();
		}
	}
}
