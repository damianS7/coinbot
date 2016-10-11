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
package com.coinbot.faucet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

public class Faucet {
	private String url;
	private String html = "";
	private int reward;
	private int timer;
	private int referral;
	
	public Faucet(String url, int reward, int timer, int referral) {
		this.url = url;
		this.timer = timer;
		this.reward = reward;
		this.referral = referral;
	}
	
	public Faucet(String url) {
		this(url, 30, 1, 10);
	}
	
	public boolean isWorking() {
		int code = 404;
		
		try {
			URL u = new URL (url);
			HttpURLConnection huc = ( HttpURLConnection )  u.openConnection ();
			huc.setRequestMethod ("GET");
			huc.connect () ; 
			code = huc.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		if(code == 404) {
			return false; 
		}
		
		return true;
	}
	
	public String getHtml() {
		if(!html.isEmpty()) {
			return html;
		}
		URL u;
        try {

            u = new URL(url);
            URLConnection conn = u.openConnection();

            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                               new InputStreamReader(conn.getInputStream()));

            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                    html += inputLine;
            }
            br.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html;
	}
	
	public String getName() {
		URI uri;
		try {
			uri = new URI(url);
			String domain = uri.getHost();
			return domain.startsWith("www.") ? domain.substring(4) : domain;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void setReferral(int referral) {
		this.referral = referral;
	}
	
	public int getReferral() {
		return referral;
	}
	
	public void setReward(int reward) {
		this.reward = reward;
	}
	
	public int getReward() {
		return reward;
	}
	
	public void setTimer(int timer) {
		this.timer = timer;
	}
	
	public int getTimer() {
		return timer;
	}
	
	public String getUrl() {
		return url;
	}
}
