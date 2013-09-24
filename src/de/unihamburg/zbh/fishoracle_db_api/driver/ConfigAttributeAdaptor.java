/*
  Copyright (c) 2012 Malte Mader <mader@zbh.uni-hamburg.de>
  Copyright (c) 2012 Center for Bioinformatics, University of Hamburg

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

import de.unihamburg.zbh.fishoracle_db_api.data.Attribute;

public interface ConfigAttributeAdaptor {

	/**
	 * Stores a new configuration attribute in the database.
	 * 
	 * @param key 
	 * @param value 
	 * @return Returns the database ID of the newly added attribute.
	 */
	public int storeAttribute(String key, String value);
	
	/**
	 * Add an attribute to a configuration.
	 * 
	 * @param attribId 
	 * @param configId 
	 * @return Returns the new relation Id.
	 */
	public int addAttributeToConfig(int attribId, int configId, boolean global);
	
	/**
	 * Fetch all distinct keys stored in the database.
	 * 
	 * @return Returns a string array containing the keys. If no keys are
	 *          stored null is returned.
	 */
	public String[] fetchAllKeys();
	
	/**
	 * Fetch all keys for a configuration. 
	 * 
	 * @param configId
	 * @param global If false all keys corresponding to a track configuration
	 *         will be fetched else all key for a global configuration will be
	 *         fetched. 
	 * @return Returns string array containing the keys. If no keys are
	 *          stored null is returned.
	 */
	public String[] fetchKeysForConfigId(int configId, boolean global);
	
	/**
	 * Fetch an attribute for its id.
	 * 
	 * @param attribId
	 * @return Returns an attribute object. If the id does not exist null is
	 *          returned.
	 */
	public Attribute fetchAttributeById(int attribId);
	
	/**
	 * Fetch an attribute for its key value pair.
	 * 
	 * @param key
	 * @param value
	 * @return Returns an attribute object. If the key value pair does not
	 *          exist null is returned.
	 */
	public Attribute fetchAttribute(String key, String value);
	
	/**
	 * Fetch all attributes for for a configuration.
	 * 
	 * @param key
	 * @param configId
	 * @param global If false all attributes corresponding to a track
	 *         configuration will be fetched else all attributes for a global
	 *         configuration will be fetched.
	 * @return Returns an array of attribute objects. If the no keys for a
	 *          configuration exist null is returned.
	 */
	public Attribute[] fetchAttribute(String key, int configId, boolean global);

	/**
	 * Delete an attribute.
	 * 
	 * @param attribId The id for which the data should be deleted.
	 */
	public void deleteAttribute(int attribId);
	
	final static String TYPE = "ConfigAttributeAdaptor";	
}
