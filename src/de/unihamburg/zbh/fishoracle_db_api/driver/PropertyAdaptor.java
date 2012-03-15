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

import de.unihamburg.zbh.fishoracle_db_api.data.Property;

/**
 * @author Malte Mader
 *
 */
public interface PropertyAdaptor {
	
	/**
	 * Stores a new property in the database.
	 * 
	 * @param property The Property Object with data to be stored.
	 * @return Returns the database ID of the newly added property.
	 */
	public int storeProperty(Property property);
	
	/**
	 * Fetches all properties that are stored in the database.
	 * 
	 * @return Returns an array of Property objects. The Property objects are
	 *          ordered by the property ID. If there are no properties stored in
	 *          the database an array of length 0 is returned.
	 */
	public Property[] fetchAllProperties();
	
	/**
	 * Fetches all properties from the database that are either enabled or
	 *  disabled.
	 * 
	 * @param enabled True fetches enabled false disabled properties.
	 * @return Returns an array of Property objects. The Property objects are
	 *          ordered by the property ID. If there are no properties stored in
	 *          the database an array of length 0 is returned.
	 */
	public Property[] fetchProperties(boolean enabled);
	
	/**
	 * Fetches property data for a given organ ID.
	 * 
	 * @param propertyId The property ID for which the database will be queried.
	 * @return Returns a Property object. If the ID does not exist null
	 *          is returned.
	 */
	public Property fetchPropertyById(int propertyId);
	
	/**
	 * Fetches all different property types that are stored in the database.
	 * 
	 * @return Returns an array of Strings. The Strings are lexically
	 *          ordered. If there are no organs stored in the database
	 *          an array of length 0 is returned.
	 */
	public String[] fetchAllTypes();
	
	/**
	 * Fetches all properties that belong to the same type.
	 * 
	 * @param type The type for which the database will be queried.
	 * @return Returns an array of Property objects. The Property objects are
	 *          ordered by the property ID. If there are no properties stored in
	 *          the database an array of length 0 is returned.
	 */
	public Property[] fetchPropertiesByType(String type);
	
	/**
	 * Fetches all properties that are assigned to a tissue sample.
	 * 
	 * @param tissueSampleId The tissue sample ID for which all properties 
	 *         will be retrieved.
	 * @return Returns an array of Property objects. The Property objects are
	 *          ordered by the property ID. If there are no properties stored in
	 *          the database an array of length 0 is returned.
	 */
	public Property[] fetchPropertiesForTissueSampleId(int tissueSampleId);
	
	/**
	 * Removes a property from the database.
	 * 
	 * @param property The Property object for the data that should be removed.
	 */
	public void deleteProperty(Property property);
	
	final static String TYPE = "PropertyAdaptor";	
}