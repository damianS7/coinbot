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
package com.coinbot.proxy;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Comprueba si un proxy funciona
 * 
 * @author danjian
 */
public class ProxyCheck implements Runnable {
	private int connectTimeout = 3000;
	private int readTimeout = 8000;
	private boolean working = false;
	private Proxy proxy;

	public ProxyCheck(Proxy proxy) {
		this.proxy = proxy;
	}

	public boolean isWorking() {
		return working;
	}

	public boolean checkProxy() {
		/*System.setProperty("http.proxyHost", proxy.getAddress());
		System.setProperty("http.proxyPort", Integer.toString(proxy.getPort()));

		// Next connection will be through proxy.
		try {
			java.net.Proxy proxy = new java.net.Proxy(java.net.Proxy.Type.HTTP,
					new InetSocketAddress(proxy.getAddress(),
							Integer.parseInt(getPort())));

			URL url = new URL("http://pastebin.com/api/api_post.php");
			/*HttpURLConnection connection = (HttpURLConnection) url
					.openConnection(proxy);
			connection.setConnectTimeout(connectTimeout);
			connection.setReadTimeout(readTimeout);
			connection.setRequestMethod("GET");
			connection.connect();

			// Si responde con 200, el proxy funciona
			if (connection.getResponseCode() == 200) {
				working = true;
			}
			return true;
		} catch (SocketTimeoutException e) {
			// System.out.println("Timeout");
		} catch (IOException e) {
		}
*/
		return false;
	}

	@Override
	public void run() {
		/*checking = true;

		if (isAlive(connTimeout, readTimeout)) {
			working = true;
		}

		checking = false;*/
	}
}
