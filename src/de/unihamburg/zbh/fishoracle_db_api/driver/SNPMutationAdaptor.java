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

import de.unihamburg.zbh.fishoracle_db_api.data.SNPMutation;

public interface SNPMutationAdaptor {

	/**
	 * Stores a mutation.
	 * 
	 * @param snpMut The Mutation Object.
	 * @param studyId The ID to the corresponding study.
	 * @return Returns The ID of the newly added mutation.
	 */
	public int storeSNPMutation(SNPMutation snpMut, int studyId);
	
	/**
	 * Stores a several mutation.
	 * 
	 * @param snpMuts An array of SNPMutation objects.
	 * @param studyId The ID to the corresponding study.
	 */
	public void storeSNPMutations(SNPMutation[] snpMuts, int studyId);
	
	/**
	 * Fetches a mutation for the given ID from the database.
	 * 
	 * @param mutationId The Mutation ID.
	 * @return A SNPMutation object.
	 */
	public SNPMutation fetchSNPMutationById(int mutationId);
	
	/**
	 * Fetches all mutations for the given study from the database.
	 * 
	 * @param studyId The ID for the study.
	 * @return An array of SNPMutation objects.
	 */
	public SNPMutation[] fetchSNPMutationsForStudyId(int studyId);
	
	/**
	 * Fetches mutations that pass the given filters/ 
	 * 
	 * @param chr The chromosome.
	 * @param start The genomic start position.
	 * @param end The genomic end position.
	 * @param qualityFilter The quality score filter.
	 * @param somaticFilter Array of somatic filter options.
	 * @param confidenceFilter Array of confidence filter options.
	 * @param snpToolFilter Array of snp tool filter options.
	 * @param projectFilter Array of project filter options.
	 * @param organFilter Array of organ filter options.
	 * @param experimentFilter Array of experiment filter options.
	 * @return An array of SNPMutation objects.
	 */
	public SNPMutation[] fetchSNPMutations(String chr,
											int start,
											int end,
											double qualityFilter,
											String[] somaticFilter,
											String[] confidenceFilter,
											String[] snpToolFilter,
											int[] projectFilter,
											int[] organFilter,
											int[] experimentFilter);
	
	/**
	 * Deletes a given mutation from the database/
	 * 
	 * @param snpMut The SNPMutation object to be removed from the database.
	 */
	public void deleteSNPMutation(SNPMutation snpMut);
	
	/**
	 * Remove all mutations belonging to a given study.
	 * 
	 * @param studyId The ID to the corresponding study. 
	 */
	public void deleteSNPMutation(int studyId);
	
	/**
	 * Remove all mutations for several studies.
	 * 
	 * @param studyIds An array of study IDs.
	 */
	public void deleteSNPMutation(int[] studyIds);
	
	final static String TYPE = "SNPMutationAdaptor";
}
