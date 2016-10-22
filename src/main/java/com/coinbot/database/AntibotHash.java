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

import java.util.List;

import com.coinbot.antibot.AntibotPuzzle;
import com.coinbot.utils.ImageHash;

public class AntibotHash {
	private String puzzleHash;
	private List<LinkHash> linksHashes;
	
	public AntibotHash(AntibotPuzzle ap) {
		this.puzzleHash = ImageHash.imageToHash(ap.getPuzzleImage());
	}
	
	public AntibotHash(String puzzleHash, List<LinkHash> linkHashes) {
		this.puzzleHash = puzzleHash;
		this.linksHashes = linkHashes;
	}

	public String getPuzzleHash() {
		return puzzleHash;
	}
	
	public LinkHash getLinkHash(String hash) {
		for (LinkHash linkHash : linksHashes) {
			if(linkHash.getHash().equals(hash)) {
				return linkHash;
			}
		}
		return null;
	}
	
	public List<LinkHash> getLinksHash() {
		return linksHashes;
	}
}
