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

import de.unihamburg.zbh.fishoracle_db_api.data.Microarraystudy;

/**
 * @author Malte Mader
 *
 */
public interface MicroarraystudyAdaptor {
	
	/**
	 * Stores a new microarray study in the database including
	 * all child instances like tissue sample or corresponding
	 * segment.
	 * 
	 * @param mstudy The Microarraystudy object. Has to contain all 
	 *         child objects.
	 * @param projectId The database ID of the project the microarraystudy
	 *         belongs to.
	 * @return Returns the database ID of the newly added microarray study.
	 */
	public int storeMicroarraystudy(Microarraystudy mstudy,int projectId);
	
	/**
	 * Fetches all microarray studies that are stored in the database
	 * including all child objects assigned to a microarray study except
	 * segments.
	 * 
	 * @return Returns an array of Microarrastudy objects. The Microarraystudy
	 *          objects are ordered by the microarray study ID. If there are
	 *          no microarray studies stored in the database an array of length
	 *          0 is returned.
	 */
	public Microarraystudy[] fetchAllMicroarraystudies();
	
	/**
	 * Fetches all microarray studies that are stored in the database.
	 * 
	 * @param withChildren If true the corresponding child data of a
	 *         microarray study will also be fetched from the database.
	 *         Segment data will not be fetched. This should be done
	 *         separately if needed.
	 * @return Returns an array of Microarrastudy objects. The Microarraystudy
	 *          objects are ordered by the microarray study ID. If there are
	 *          no microarray studies stored in the database an array of length
	 *          0 is returned.
	 */
	public Microarraystudy[] fetchAllMicroarraystudies(boolean withChildren);
	
	/**
	 * Fetches microarray study data for a given microarray study ID including
	 * including all child objects assigned to a microarray study except
	 * segments.
	 * 
	 * @param mstudyId The microarray study ID for which the database will
	 *         be queried.
	 * @return Returns a Microarraystudy object. If the ID does not exist null
	 *          is returned.
	 */
	public Microarraystudy fetchMicroarraystudyById(int mstudyId);
	
	/**
	 * Fetches microarray study data for a given microarray study ID.
	 * 
	 * @param mstudyId The microarray study ID for which the database will
	 *         be queried.
	 * @param withChilden If true the corresponding child data of a
	 *         microarray study will also be fetched from the database.
	 *         Segment data will not be fetched. This should be done
	 *         separately if needed.
	 * @return Returns a Microarraystudy object. If the ID does not exist null
	 *          is returned.
	 */
	public Microarraystudy fetchMicroarraystudyById(int mstudyId, boolean withChilden);
	
	/**
	 * Fetch all microarray studies that are part of a project including
	 * including all child objects assigned to a microarray study except
	 * segments.
	 * 
	 * @param projectId The project ID for which the database will
	 *         be queried.
	 * @return Returns an array of Microarrastudy objects. The Microarraystudy
	 *          objects are ordered by the microarray study ID. If there are
	 *          no microarray studies stored in the database an array of length
	 *          0 is returned.
	 */
	public Microarraystudy[] fetchMicroarraystudiesForProject(int projectId);
	
	/**
	 * Fetch all microarray studies that are part of a project.
	 * 
	 * @param projectId The project ID for which the database will
	 *         be queried.
	 * @param withChrildren If true the corresponding child data of a
	 *         microarray study will also be fetched from the database.
	 *         Segment data will not be fetched. This should be done
	 *         separately if needed.
	 * @return Returns an array of Microarrastudy objects. The Microarraystudy
	 *          objects are ordered by the microarray study ID. If there are
	 *          no microarray studies stored in the database an array of length
	 *          0 is returned.
	 */
	public Microarraystudy[] fetchMicroarraystudiesForProject(int projectId, boolean withChrildren);
	
	/**
	 * Fetch all microarray studies that are part of a set of projects including
	 * including all child objects assigned to a microarray study except
	 * segments.
	 * 
	 * @param projectIds An array of project IDs for which the database will
	 *         be queried.
	 * @return Returns an array of Microarrastudy objects. The Microarraystudy
	 *          objects are ordered by the microarray study ID. If there are
	 *          no microarray studies stored in the database an array of length
	 *          0 is returned.
	 */
	public Microarraystudy[] fetchMicroarraystudiesForProject(int[] projectIds);
	
	/**
	 * Fetch all microarray studies that are part of a set of projects.
	 * 
	 * @param projectIds An array of project IDs for which the database will
	 *         be queried.
	 * @param withChrildren If true the corresponding child data of a
	 *         microarray study will also be fetched from the database.
	 *         Segment data will not be fetched. This should be done
	 *         separately if needed.
	 * @return Returns an array of Microarrastudy objects. The Microarraystudy
	 *          objects are ordered by the microarray study ID. If there are
	 *          no microarray studies stored in the database an array of length
	 *          0 is returned.
	 */
	public Microarraystudy[] fetchMicroarraystudiesForProject(int[] projectIds, boolean withChrildren);
	
	/**
	 * Removes a microarray study from the database.
	 * 
	 * @param mstudyId the database ID the the data that will be removed.
	 */
	public void deleteMicroarraystudy(int mstudyId);
	
	/**
	 * Removes a microarray study from the database.
	 * 
	 * @param mstudy The Microarraystudy object for that data that should
	 *         be removed.
	 */
	public void deleteMicroarraystudy(Microarraystudy mstudy);
	
	final static String TYPE = "MicroarraystudyAdaptor";
}