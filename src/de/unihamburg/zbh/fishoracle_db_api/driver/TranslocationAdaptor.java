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

import de.unihamburg.zbh.fishoracle_db_api.data.Translocation;

/**
 * @author Malte Mader
 *
 */
public interface TranslocationAdaptor {

	/**
	 * Stores a translocation.
	 * 
	 * @param transloc The Translocation Objects representing one translocation.
	 * @param studyId The ID to the corresponding study.
	 * @return Returns The an Array of IDs of the newly added translocation.
	 */
	public int[] storeTranslocation(Translocation[] transloc, int studyId);
	
	/**
	 * Stores a several translocations.
	 * 
	 * @param translocs An array of Translocation objects.
	 * @param studyId The ID to the corresponding study.
	 */
	public void storeTranslocations(Translocation[][] translocs, int studyId);
	
	/**
	 * Fetches a translocation for the given ID from the database.
	 * 
	 * @param translocationId The Translocation ID.
	 * @return A Translocation Array which stores the translocation
	 *          for the requested ID at index 0 and the reference
	 *          translocation at index 1. 
	 */
	public Translocation[] fetchTranslocationById(int translocationId);
	
	/**
	 * Fetches a translocation for the given ID from the database.
	 * 
	 * @param translocationId The Translocation ID.
	 * @param fetchReference Determines if the reference for a
	 *         translocation should be fetched.
	 * @return A Translocation Array which stores the translocation
	 *          for the requested ID at index 0 and the reference
	 *          translocation at index 1. 
	 */
	public Translocation[] fetchTranslocationById(int translocationId, boolean fetchReference);
	
	/**
	 * Fetches all translocations for the given study from the database.
	 * 
	 * @param studyId The ID for the study.
	 * @return An array of translocation objects.
	 */
	public Translocation[][] fetchTranslocationsForStudyId(int studyId);
	
	/**
	 * Fetches translocations that pass the given filters/ 
	 * 
	 * @param chr The chromosome.
	 * @param start The genomic start position.
	 * @param end The genomic end position.
	 * @param projectFilter Array of project filter options.
	 * @param organFilter Array of organ filter options.
	 * @param experimentFilter Array of experiment filter options.
	 * @return An array of Translocation objects.
	 */
	public Translocation[][] fetchTranslocations(String chr,
												int start,
												int end,
												int[] projectFilter,
												int[] organFilter,
												int[] experimentFilter);
	
	/**
	 * Deletes a given translocation from the database/
	 * 
	 * @param transloc The Translocation object to be removed from the database.
	 */
	public void deleteTranslocation(Translocation[] transloc);
	
	/**
	 * Remove all translocations belonging to a given study.
	 * 
	 * @param studyId The ID to the corresponding study. 
	 */
	public void deleteTranslocation(int studyId);
	
	/**
	 * Remove all translocations for several studies.
	 * 
	 * @param studyIds An array of study IDs.
	 */
	public void deleteTranslocation(int[] studyIds);
	
	final static String TYPE = "TranslocationAdaptor";
}
