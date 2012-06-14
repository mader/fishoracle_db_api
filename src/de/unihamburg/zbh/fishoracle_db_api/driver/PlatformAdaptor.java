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

import de.unihamburg.zbh.fishoracle_db_api.data.Platform;

/**
 * @author Malte Mader
 * 
 * Organizes the different platform types that could
 * correspond to a study.
 *
 */
public interface PlatformAdaptor {

	/**
	 * Stores a new platform in the database.
	 * 
	 * @param platform The Platform object. The platform ID of the object will not
	 *         will not be used.
	 * @return Returns the database ID of the newly added platform.
	 */
	public int storePlatform(Platform platform);
	
	/**
	 * Fetches all platforms that are stored in the database.
	 * 
	 * @return Returns an array of Platform objects. The Platform objects are
	 *          ordered by the platform ID. If there are no platforms stored in
	 *          the database an array of length 0 is returned.
	 */
	public Platform[] fetchAllPlatforms();
	
	/**
	 * Fetches all different platform types that are stored in the database.
	 * 
	 * @return Returns an array of Strings. The Strings are lexically
	 *          ordered. If there are no platforms stored in the database
	 *          an array of length 0 is returned.
	 */
	public String[] fetchAllTypes();
	
	/**
	 * Fetches platform data for a given platform ID.
	 * 
	 * @param id The platform ID for which the database will be queried.
	 * @return Returns a Platform object. If the ID does not exist null
	 *          is returned.
	 */
	public Platform fetchPlatformById(int id);
	
	/**
	 * Removes a platform from the database.
	 * 
	 * @param platformId The platform ID for the data that should be removed.
	 */
	public void deletePlatform(int platformId);
	
	/**
	 * Removes a platform from the database.
	 * 
	 * @param platform The Platform object for the data that should be removed.
	 */
	public void deletePlatform(Platform platform);
	
	final static String TYPE = "PlatformAdaptor";
}
