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

package de.unihamburg.zbh.fishoracle_db_api.driver;

import de.unihamburg.zbh.fishoracle_db_api.data.Chip;

/**
 * @author Malte Mader
 * 
 * Organizes the different chip types that could
 * correspond to a microarraystudy.
 *
 */
public interface ChipAdaptor {

	/**
	 * Stores a new chip in the database.
	 * 
	 * @param chip The Chip object. The chip ID of the object will not
	 *         will not be used.
	 * @return Returns the database ID of the newly added chip.
	 */
	public int storeChip(Chip chip);
	
	/**
	 * Fetches all chips that are stored in the database.
	 * 
	 * @return Returns an array of Chip objects. The Chip objects are
	 *          ordered by the chip ID. If there are no chips stored in
	 *          the database an array of length 0 is returned.
	 */
	public Chip[] fetchAllChips();
	
	/**
	 * Fetches all different chip types that are stored in the database.
	 * 
	 * @return Returns an array of Strings. The Strings are lexically
	 *          ordered. If there are no chips stored in the database
	 *          an array of length 0 is returned.
	 */
	public String[] fetchAllTypes();
	
	/**
	 * Fetches chip data for a given chip ID.
	 * 
	 * @param id The chip ID for which the database will be queried.
	 * @return Returns a Chip object. If the ID does not exist null
	 *          is returned.
	 */
	public Chip fetchChipById(int id);
	
	/**
	 * Removes a chip from the database.
	 * 
	 * @param chipId The chip ID for the data that should be removed.
	 */
	public void deleteChip(int chipId);
	
	/**
	 * Removes a chip from the database.
	 * 
	 * @param chip The Chip object for the data that should be removed.
	 */
	public void deleteChip(Chip chip);
	
	final static String TYPE = "ChipAdaptor";
}
