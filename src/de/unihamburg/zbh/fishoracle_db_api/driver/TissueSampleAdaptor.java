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

import de.unihamburg.zbh.fishoracle_db_api.data.TissueSample;

/**
 * @author Malte Mader
 *
 */
public interface TissueSampleAdaptor {
	
	/**
	 * Stores a new tissue sample in the database.
	 * 
	 * @param tissue The TissueSample Object.
	 * @return Returns the database ID of the newly added tissue sample.
	 */
	public int storeTissueSample(TissueSample tissue);
	
	/**
	 * Stores a new tissue sample in the database.
	 * 
	 * @param organ_id The database ID to the corresponding organ.
	 * @param propertyIds All database IDs of all properties assigned
	 *         to this tissueSample.
	 * @return Returns the database ID of the newly added tissue sample.
	 */
	public int storeTissueSample(int organ_id, int[] propertyIds, int studyId);
	
	/**
	 * Fetches tissue sample data for a given tissue sample ID including
	 * all properties and organ assigned to a tissue sample.
	 * 
	 * @param tissueSampleId The database ID for the TissueSample
	 *         object to be fetched.
	 * @return Returns a TissueSample object. If the ID does not exist null
	 *          is returned.
	 */
	public TissueSample fetchTissueSampleById(int tissueSampleId);
	
	/**
	 * Fetches tissue sample data for a given tissue sample ID.
	 * 
	 * @param tissueSampleId The database ID for the TissueSample
	 *         object to be fetched.
	 * @param withChildren  If true all assigned properties and the organ
	 *         of a tissue sample will also be fetched from the database.
	 * @return Returns a TissueSample object. If the ID does not exist null
	 *          is returned.
	 */
	public TissueSample fetchTissueSampleById(int tissueSampleId, boolean withChildren);
	
	/**
	 * Removes a tissue sample from the database.
	 * 
	 * @param tissue The TissueSample object for that data that
	 *                should be removed.
	 */
	public void deleteTissueSample(TissueSample tissue);
	
	final static String TYPE = "TissueSampleAdaptor";	
}