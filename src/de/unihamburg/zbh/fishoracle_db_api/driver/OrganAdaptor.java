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

import de.unihamburg.zbh.fishoracle_db_api.data.Organ;

/**
 * @author Malte Mader
 *
 */
public interface OrganAdaptor {
	
	/**
	 * Stores a new organ in the database.
	 * 
	 * @param organ The Organ object to be stored.
	 * @return Returns the database ID of the newly added organ.
	 */
	public int storeOrgan(Organ organ);
	
	/**
	 *  Fetches all organs that are stored in the database
	 * 
	 * @return Returns an array of Organ objects. The Organ objects are
	 *          ordered by the organ ID. If there are no organs stored in
	 *          the database an array of length 0 is returned.
	 */
	public Organ[] fetchAllOrgans();
	
	/**
	 *  Fetches all organs from the database that are either enabled or
	 *  disabled.
	 * 
	 * @param enabled True fetches enabled false disabled organs.
	 * @return Returns an array of Organ objects. The Organ objects are
	 *          ordered by the organ ID. If there are no organs stored in
	 *          the database an array of length 0 is returned.
	 */
	public Organ[] fetchOrgans(boolean enabled);
	
	/**
	 * Fetches organ data for a given organ ID.
	 * 
	 * @param organId The organ ID for which the database will be queried.
	 * @return Returns an Organ object. If the ID does not exist null
	 *          is returned.
	 */
	public Organ fetchOrganById(int organId);
	
	/**
	 * Fetches all different organ types that are stored in the database.
	 * 
	 * @return Returns an array of Strings. The Strings are lexically
	 *          ordered. If there are no organs stored in the database
	 *          an array of length 0 is returned.
	 */
	public String[] fetchAllTypes();
	
	/**
	 *  Fetches all organs that belong to the same type.
	 * 
	 * @param type The type for which the database will be queried.
	 * @return Returns an array of Organ objects. The Organ objects are
	 *          ordered by the organ ID. If there are no organs stored in
	 *          the database an array of length 0 is returned.
	 */
	public Organ[] fetchOrgansByType(String type);
	
	/**
	 * Removes an organ from the database.
	 * 
	 * @param organ The Organ object for the data that should be removed.
	 */
	public void deleteOrgan(Organ organ);
	
	final static String TYPE = "OrganAdaptor";
}