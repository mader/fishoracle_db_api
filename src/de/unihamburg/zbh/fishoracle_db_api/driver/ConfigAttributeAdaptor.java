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
	
	public String[] fetchAllKeys();
	
	public String[] fetchKeysForConfigId(int configId);
	
	public String[] fetchKeysForTrackConfigId(int trackConfigId);
	
	public Attribute fetchAttributeById(int attribId);
	
	Attribute fetchAttribute(String key, String value);
	
	public Attribute[] fetchAttribute(String key, int configId, boolean global);

	public void deleteAttribute(int attribId);
	
	final static String TYPE = "ConfigAttributeAdaptor";	
}
