/*
  Copyright (c) 2011-2012 Malte Mader <mader@zbh.uni-hamburg.de>
  Copyright (c) 2011-2012 Center for Bioinformatics, University of Hamburg

  Permission to use, copy, modify, and distribute this software for any
  purpose with or without fee is hereby granted, provided that the above
  copyright notice and this permission notice appear in all copies.

  THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
  WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
  MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
  ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
  WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
  ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
  OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
*/

package de.unihamburg.zbh.fishoracle_db_api.data;

/**
 * @author Malte Mader
 *
 */
public class Location {
	
	private String chrosmome;
	private int start;
	private int end;
	
	public Location() {
	}

	public Location(String chrosmome, int start, int end) throws Exception {
		
		if(start > end){
			throw new Exception("Start > End: " + start + " > " + end );
		}
		
		this.chrosmome = chrosmome;
		this.start = start;
		this.end = end;
	}

	public String getChromosome() {
		return chrosmome;
	}

	public void setChrosmome(String chrosmome) {
		this.chrosmome = chrosmome;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	/**
	 * Maximize the range of two locations.
	 * 
	 * @param loc The second location.
	 * @return Returns a new Location Object.
	 * @throws Exception
	 */
	public Location maximize(Location loc) throws Exception{
		
		Location maxLoc;
		
		if(this.chrosmome.equals(loc.getChromosome())){
		
			maxLoc = new Location();
			
			maxLoc.setChrosmome(this.chrosmome);
		
			if(this.start < loc.getStart()){
				maxLoc.setStart(this.start);
			} else {
				maxLoc.setStart(loc.getStart());
			}
		
			if(this.end > loc.getEnd()){
				maxLoc.setEnd(this.end);
			} else {
				maxLoc.setEnd(loc.getEnd());
			}
			
			} else {
				throw new Exception("Chromosomes not equal!");
			} 
		
		return maxLoc;
		
	}
}