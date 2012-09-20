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

import de.unihamburg.zbh.fishoracle_db_api.data.GenericFeature;

/**
 * @author Malte Mader
 *
 */
public interface GenericAdaptor {

	/**
	 * Stores a generic feature.
	 * 
	 * @param feature The GenericFeature objects representing one any genomic
	 *         feature.
	 * @param studyId The ID to the corresponding study.
	 * @return Returns The an Array of IDs of the newly added generic feature.
	 */
	public int storeGenericFeature(GenericFeature feature, int studyId);
	
	/**
	 * Stores a several generic features.
	 * 
	 * @param features An array of GenericFeature objects.
	 * @param studyId The ID to the corresponding study.
	 */
	public void storeGenericFeatures(GenericFeature[] features, int studyId);
	
	/**
	 * Fetches a generic feature for the given ID from the database.
	 * 
	 * @param featureId The GenericFeature ID.
	 * @return A genericFeature object.
	 */
	public GenericFeature fetchGenericFeatureById(int featureId);
	
	/**
	 * Fetches all different feature types that are stored in the database.
	 * 
	 * @return Returns an array of Strings. The Strings are lexically
	 *          ordered. If there are no generic features are stored in the
	 *          database an array of length 0 is returned.
	 */
	public String[] fetchAllTypes();
	
	/**
	 * Fetches all generic features for the given study from the database.
	 * 
	 * @param studyId The ID for the study.
	 * @return An array of GenericFeqature objects.
	 */
	public GenericFeature[] fetchGenericFeaturesForStudyId(int studyId);
	
	/**
	 * Fetches generic features that pass the given filters. 
	 * 
	 * @param chr The chromosome.
	 * @param start The genomic start position.
	 * @param end The genomic end position.
	 * @param featureTypeFilter Specifies the feature type.
	 * @param projectFilter Array of project filter options.
	 * @param organFilter Array of organ filter options.
	 * @param experimentFilter Array of experiment filter options.
	 * @return An array of GenericFeature objects.
	 */
	public GenericFeature[] fetchGenericFeatures(String chr,
											int start,
											int end,
											String[] featureTypeFilter,
											int[] projectFilter,
											int[] organFilter,
											int[] experimentFilter);
	
	/**
	 * Deletes a given generic feature from the database.
	 * 
	 * @param feature The GenericFeature object to be removed from the database.
	 */
	public void deleteGenericFeature(GenericFeature feature);
	
	/**
	 * Remove all generic features belonging to a given study.
	 * 
	 * @param studyId The ID to the corresponding study. 
	 */
	public void deleteGenericFeature(int studyId);
	
	/**
	 * Remove all genericFeatures for several studies.
	 * 
	 * @param studyIds An array of study IDs.
	 */
	public void deleteGenericFeature(int[] studyIds);
	
	final static String TYPE = "GenericAdaptor";
}
