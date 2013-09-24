/*
  Copyright (c) 2012-2013 Malte Mader <mader@zbh.uni-hamburg.de>
  Copyright (c) 2012-2013 Center for Bioinformatics, University of Hamburg

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

import de.unihamburg.zbh.fishoracle_db_api.data.ConfigData;

public interface ConfigAdaptor {

	/**
	 * Stores a configuration in the database.
	 * 
	 * @param cd contains all key value pairs to be stored.
	 * @return Returns the id for the stored object.
	 */
	public int storeConfig(ConfigData cd);
	
	/**
	 * Fetches a stored configuration for a given id.
	 * 
	 * @param configId The id for the data to be fetched.
	 * @return Returns a configuration data object. If there is no
	 *          corresponding object stored in the database, null is returned.
	 */
	public ConfigData fetchConfigById(int configId);
	
	/**
	 * Fetches stored configurations for a given user id.
	 * 
	 * @param userId The user id for which the configuration objects will be
	 *         fetched.
	 * @return Returns an array of configuration data objects. If there are no	
	 *          corresponding objects stored in the database, null is returned.
	 */
	public ConfigData[] fetchConfigForUserId(int userId);
	
	/**
	 * Updates an existing configurations.
	 * 
	 * @param cd The configuration object containing the updated data. 
	 * @return Returns the configuration id.
	 */
	public int updateConfigData(ConfigData cd);
	
	/**
	 * Delete a stored configuration.
	 * 
	 * @param configId The id for which the configuration object will be
	 *         deleted.
	 */
	public void deleteConfig(int configId);
	
	final static String TYPE = "ConfigAdaptor";
	
}
