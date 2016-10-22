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
package com.coinbot.detector;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.coinbot.antibot.AntibotPuzzle;
import com.coinbot.exceptions.DetectionException;

public class AntibotDetector implements Detector {
	private WebDriver driver;
	private AntibotPuzzle antibot;
	
	// <p class="alert alert-info">Please click on the Anti-Bot links in the
	// following order <img
	// src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAD8AAAAYCAYAAABN9iVRAAADd0lEQVRYhe3YX4hVVRQG8J/DIG4RGWTowcwHkZCQCokMg+whfCk4SvRSgVBYGUREiEFEhBBZVIiUDyYRFVYQtMOgSARDQiQiTELEQsTCh4ghwiUhQw/7jFyP596ZuX/e+uBwufvsvda31tp7rbXPAj2QqrQE05HjUq95/aKWvxw34FTkmBqFnm5Y0IXUcryMTbiAjZFjehBFqUpP4UbF2JkHpvAv9kaOLwfR0aFrBVbiUuT4qdu864xPVVqH/fVzCN/iochxagAyS/Ez3sEpnMG5yHGlX5ktOpbhaVSYxDTGsSdyvNW2Zrwh4FYcwI7IcbgeO42FA3JbrByf1weU04pUpVX4FIexDSfrV/twU7d1443/u/B85DjSMTaJPwbktxgjyRs17sTOBm+pSpN4o9uipvFbOs92vZUWRY6LA5JbpJzrkSByfNIcS1V6Ab9FjuPd1l1jfEtS24RjQ+C30AiNbyJV6RWsw4O95o3NImcLvhkCnzElAV1FqtIdqUobhiD7GqQq7ca92GEW+5rbvlPIKqxRksigmMaVWu5aJeuPYzJV6cCwEmGq0h5sxmV8gCWpSt8p+eDv5vxentmGL4ZUjq5grK4mH2N/5LgbO82yNeeKVKW3FcOPKaV5PdZjCd5vW9NqfJ3oNndb1AcuY0Ipozsjx0f1+A9YNiQd+3B/5HgkcpyEyPEPnsPtteOvQbdt/wSORY5zQyI2pURgV+T4umN8pcHLKIgcZ7qM/5mqdFGLk6+LfB31rdg7DFIzBLC7pSTdh++HpacNqUqLlLvD6ea7tsg/ixMzW2dYiBzvNkgtU877UM58DzyG4229SrO9XYmHlRI3MqQqjWEPDkaOsyPUswGPK/3+dWhG/iUcmrnE1LejNbgZZ/EjNkeO9wYgtFQx/HLkeK1fOXPQ84ByM30yclxom7OgY/I9+BBHlH5+teKcs8p5OYqLOIjb2urmLGQWK7tqOz6PHK+2zJk0uHMnlCDegu29knZn5O9Sks+v+Aq/4HSj11+hJMkJzNn4mtBRnMDWHtfj1XgxVemzPpy7EI8q2/ygcjPt+Q3iqvFz7LIm6t+/5kMsckylKm2cw5eaC/pwbo038btS6+f0Rahre9sFa3G+bh7mhTkS6su5tfxn5rtmtotNE+cNsf63oG/n9oN5RT5yjLQhMXrn/o//gf8APCdCdg+4ftcAAAAASUVORK5CYII="
	// alt="" width="63" height="24"> <a href="#" id="antibotlinks_reset"
	// style="display: none;">( reset )</a></p>
	
	public AntibotDetector(WebDriver driver) {
		this.driver = driver;
	}
	
	public AntibotPuzzle getAntibot() {
		return antibot;
	}
	
	@Override
	public void detect() throws DetectionException {
		try {
			List<WebElement> alerts = driver.findElements(By.className("alert-info"));
			
			for (WebElement alert : alerts) {
				if(alert.getText().toLowerCase().contains("anti-bot")) {
					
				}
			}
			
		} catch (NoSuchElementException e) {
			
		}
		
		
		
		
		throw new DetectionException("Antibot protection not found.");
	}
	
	public static void main(String[] args) {
		File pathToBinary = new File(
				"/home/jian/Descargas/firefox46/bin/firefox");
		// Firefox 46 needed
		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
		FirefoxProfile profile = new FirefoxProfile();
		FirefoxDriver driver = new FirefoxDriver(ffBinary, profile);

		try {
			driver.manage().timeouts().pageLoadTimeout(12, TimeUnit.SECONDS);
			driver.navigate().to(new URL("http://bitcoin-gator.com"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	
}
