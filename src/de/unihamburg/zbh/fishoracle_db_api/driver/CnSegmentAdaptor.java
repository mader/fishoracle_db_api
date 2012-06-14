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

import de.unihamburg.zbh.fishoracle_db_api.data.CnSegment;
import de.unihamburg.zbh.fishoracle_db_api.data.Location;

/**
 * @author Malte Mader
 *
 */
public interface CnSegmentAdaptor {
	
	/**
	 * Stores a new segment in the database.
	 * 
	 * @param segment The CnSegment object holding the data to be stored
	 *         in the database.
	 * @param mstudyId The database ID of the microarray study the segment
	 *         belongs to.
	 * @return Returns the database ID of the newly added segment.
	 */
	public int storeCnSegment(CnSegment segment, int mstudyId);
	
	/**
	 *  Stores an array segments in the database.
	 * 
	 * @param segments An array of CnSegment object holding the data
	 *         to be stored in the database.
	 * @param mstudyId he database ID of the microarray study the segment
	 *         belongs to.
	 */
	public void storeCnSegments(CnSegment[] segments, int mstudyId);
	
	/**
	 * Fetches segment data for a given segment ID.
	 * 
	 * @param segmentId The segment ID for which the database will be queried.
	 * @return Returns a CnSegment object. If the ID does not exist null
	 *          is returned.
	 */
	public CnSegment fetchCnSegmentById(int segmentId);
	
	/**
	 * Fetches a Location object containing the position of a segment.
	 * 
	 * @param segmentId The segment ID for which the database will be queried.
	 * @return A Location object. Will return null if the requested segment
	 *          does not exist.
	 */
	public Location fetchLocationForCnSegmentId(int segmentId);
	
	/**
	 * Fetches a Location object containing the range of overlapping
	 * segments. 
	 * 
	 * @param chr The chromosome.
	 * @param start The start position.
	 * @param end the end position.
	 * @param lowerTh The lower mean intensity threshold.
	 * @param upperTh The upper mean intensity threshold.
	 * @param projectFilter An array of project IDs for which segments
	 *         should be fetched.
	 * @param organFilter An array of organ IDs for which segments
	 *         should be fetched.
	 * @param experimentFilter An array of microarraystudy IDs for which
	 *         segments should be fetched.
	 * @return Returns a Location object. If there are no overlapping
	 *          segments the input position will be returned. 
	 */
	public Location fetchMaximalOverlappingCnSegmentRange(String chr,
															int start,
															int end,
															Double lowerTh,
															Double upperTh,
															int[] projectFilter,
															int[] organFilter,
															int[] experimentFilter);
	
	/**
	 * Fetches all segments for a microarray study.
	 * 
	 * @param mstudyId The database ID of the microarray study for which the
	 *         database will be queried. 
	 * @return Returns an array of CnSegment objects. The CnSegment objects are
	 *          ordered by the CnSegment ID. If there are no segments stored in
	 *          the database an array of length 0 is returned.
	 */
	public CnSegment[] fetchCnSegmentsForStudyId(int mstudyId);
	
	/**
	 * Fetches segments for a range and filter attributes.
	 * 
	 * @param chr The chromosome.
	 * @param start The start position.
	 * @param end the end position.
	 * @param lowerTh The lower mean intensity threshold.
	 * @param upperTh The upper mean intensity threshold.
	 * @param projectFilter An array of project IDs for which segments
	 *         should be fetched.
	 * @param organFilter An array of organ IDs for which segments
	 *         should be fetched.
	 * @param experimentFilter An array of microarraystudy IDs for which
	 *         segments should be fetched.
	 * @return Returns an array of CnSegment objects. The CnSegment objects are
	 *          ordered by the CnSegment ID. If there are no segments stored in
	 *          the database an array of length 0 is returned.
	 */
	public CnSegment[] fetchCnSegments(String chr,
										int start,
										int end,
										Double lowerTh,
										Double upperTh,
										int[] projectFilter,
										int[] organFilter,
										int[] experimentFilter);
	
	/**
	 * Removes a segment from the database.
	 * 
	 * @param segment The Segment object for that data that should be removed.
	 */
	public void deleteCnSegment(CnSegment segment);
	
	/**
	 * Removes all segment of a microarray study from the database.
	 * 
	 * @param microarraystudyId The database ID of the microarray study for
	 *         which all segments will be removed. 
	 */
	public void deleteCnSegment(int microarraystudyId);
	
	/**
	 * Removes all segment of a set of microarray studies from the database.
	 * 
	 * @param microarraystudyIds The database IDs of the microarray studies
	 *         for which all segments will be removed. 
	 */
	public void deleteCnSegment(int[] microarraystudyIds);
	
	final static String TYPE = "CnSegmentAdaptor";
}