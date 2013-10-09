/*
  Copyright (c) 2011-2013 Malte Mader <mader@zbh.uni-hamburg.de>
  Copyright (c) 2011-2013 Center for Bioinformatics, University of Hamburg

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

import de.unihamburg.zbh.fishoracle_db_api.data.Segment;
import de.unihamburg.zbh.fishoracle_db_api.data.Location;

/**
 * @author Malte Mader
 *
 */
public interface SegmentAdaptor {
	
	/**
	 * Stores a new segment in the database.
	 * 
	 * @param segment The Segment object holding the data to be stored
	 *         in the database.
	 * @param studyId The database ID of the study the segment
	 *         belongs to.
	 * @return Returns the database ID of the newly added segment.
	 */
	public int storeSegment(Segment segment, int studyId);
	
	/**
	 *  Stores an array segments in the database.
	 * 
	 * @param segments An array of Segment object holding the data
	 *         to be stored in the database.
	 * @param studyId he database ID of the study the segment
	 *         belongs to.
	 */
	public void storeSegments(Segment[] segments, int studyId);
	
	/**
	 * Fetches segment data for a given segment ID.
	 * 
	 * @param segmentId The segment ID for which the database will be queried.
	 * @return Returns a Segment object. If the ID does not exist null
	 *          is returned.
	 */
	public Segment fetchSegmentById(int segmentId);
	
	/**
	 * Fetches a Location object containing the position of a segment.
	 * 
	 * @param segmentId The segment ID for which the database will be queried.
	 * @return A Location object. Will return null if the requested segment
	 *          does not exist.
	 */
	public Location fetchLocationForSegmentId(int segmentId);
	
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
	public Location fetchMaximalOverlappingSegmentRange(String chr,
															int start,
															int end,
															String[] projectFilter,
															String[] organFilter,
															String[] experimentFilter);
	
	/**
	 * Fetches all segments for a study.
	 * 
	 * @param studyId The database ID of the study for which the
	 *         database will be queried. 
	 * @return Returns an array of Segment objects. The Segment objects are
	 *          ordered by the Segment ID. If there are no segments stored in
	 *          the database an array of length 0 is returned.
	 */
	public Segment[] fetchSegmentsForStudyId(int studyId);
	
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
	 * @param experimentFilter An array of study IDs for which
	 *         segments should be fetched.
	 * @return Returns an array of Segment objects. The Segment objects are
	 *          ordered by the CnSegment ID. If there are no segments stored in
	 *          the database an array of length 0 is returned.
	 */
	public Segment[] fetchSegments(String chr,
										int start,
										int end,
										Double segMean,
										String[] projectFilter,
										String[] organFilter,
										String[] experimentFilter);
	
	/**
	 * Removes a segment from the database.
	 * 
	 * @param segment The Segment object for that data that should be removed.
	 */
	public void deleteSegment(Segment segment);
	
	/**
	 * Removes all segment of a study from the database.
	 * 
	 * @param studyId The database ID of the study for
	 *         which all segments will be removed. 
	 */
	public void deleteSegment(int studyId);
	
	/**
	 * Removes all segment of a set of studies from the database.
	 * 
	 * @param studyIds The database IDs of the studies
	 *         for which all segments will be removed. 
	 */
	public void deleteSegment(int[] studyIds);
	
	public Double getSegmentIntensity();
	public void setSegmentIntensity(Double segmentIntensity);
	public String[] getSegmentStati();
	public void setSegmentStati(String[] segmentStati);
	
	final static String TYPE = "SegmentAdaptor";
}