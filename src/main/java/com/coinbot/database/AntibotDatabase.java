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
package com.coinbot.database;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class AntibotDatabase extends JsonDatabase {
	private List<AntibotHash> hashes = new ArrayList<AntibotHash>();

	public AntibotDatabase(File file) {
		super(file);
	}
	
	public void addAntibot(AntibotHash ahash) {
		hashes.add(ahash);
	}
	
	public void removeAntibot(AntibotHash ahash) {
		hashes.remove(ahash);
	}
	
	public List<AntibotHash> getHashes() {
		return hashes;
	}
	
	public AntibotHash getHash(String hash) {
		for (AntibotHash antibotHash : hashes) {
			if(antibotHash.getPuzzleHash().equals(hash)) {
				return antibotHash;
			}
		}
		return null;
	}
	
	@Override
	public int load() {
		super.load();
		
		for (String puzzleHash : jsonData.keySet()) {

			try {
				JSONObject ohash = jsonData.getJSONObject(puzzleHash);
				List<LinkHash> lhashes = new ArrayList<LinkHash>();
				
				for (String linkHash : ohash.keySet()) {
					lhashes.add(new LinkHash(linkHash, ohash.getInt(linkHash)));
				}

				addAntibot(new AntibotHash(puzzleHash, lhashes));
			} catch (JSONException e) {
				
				continue;
			}
		}

		return hashes.size();
	}
	
	@Override
	public void save() {
		jsonData = new JSONObject();
		for (AntibotHash antibotHash : hashes) {
			JSONObject ohash = new JSONObject();
			
			for (LinkHash lh : antibotHash.getLinksHash()) {
				ohash.put(lh.getHash(), lh.getOrder());
			}
			
			jsonData.put(antibotHash.getPuzzleHash(), ohash);
		}
		
		super.save();
	}
}
