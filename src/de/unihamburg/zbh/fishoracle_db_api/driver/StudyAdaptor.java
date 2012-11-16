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

import de.unihamburg.zbh.fishoracle_db_api.data.Study;

/**
 * @author Malte Mader
 *
 */
public interface StudyAdaptor {
	
	/**
	 * Stores a new study in the database including
	 * all child instances like tissue sample or corresponding
	 * segment.
	 * 
	 * @param study The Study object. Has to contain all 
	 *         child objects.
	 * @param projectId The database ID of the project the study
	 *         belongs to.
	 * @return Returns the database ID of the newly added study.
	 */
	public int storeStudy(Study study,int projectId);
	
	/**
	 * Fetches all studies that are stored in the database
	 * including all child objects assigned to a study except
	 * segments.
	 * 
	 * @return Returns an array of Study objects. The Study
	 *          objects are ordered by the study ID. If there are
	 *          no studies stored in the database an array of length
	 *          0 is returned.
	 */
	public Study[] fetchAllStudies();
	
	/**
	 * Fetches all studies that are stored in the database.
	 * 
	 * @param withChildren If true the corresponding child data of a
	 *         study will also be fetched from the database.
	 *         Segment data will not be fetched. This should be done
	 *         separately if needed.
	 * @return Returns an array of study objects. The Study
	 *          objects are ordered by the study ID. If there are
	 *          no studies stored in the database an array of length
	 *          0 is returned.
	 */
	public Study[] fetchAllStudies(boolean withChildren);
	
	/**
	 * Fetches study data for a given study ID including
	 * including all child objects assigned to a study except
	 * segments.
	 * 
	 * @param studyId The study ID for which the database will
	 *         be queried.
	 * @return Returns a Study object. If the ID does not exist null
	 *          is returned.
	 */
	public Study fetchStudyById(int studyId);
	
	/**
	 * Fetches study data for a given study ID.
	 * 
	 * @param studyId The study ID for which the database will
	 *         be queried.
	 * @param withChilden If true the corresponding child data of a
	 *         study will also be fetched from the database.
	 *         Segment data will not be fetched. This should be done
	 *         separately if needed.
	 * @return Returns a Study object. If the ID does not exist null
	 *          is returned.
	 */
	public Study fetchStudyById(int studyId, boolean withChilden);
	
	/**
	 * Fetch a study for a given name
	 * 
	 * @param studyName The name for which the database will
	 *         be queried.
	 * @param withChrildren If true the corresponding child data of a
	 *         study will also be fetched from the database.
	 *         Segment data will not be fetched. This should be done
	 *         separately if needed.
	 * @return Returns an array of Study objects. The Study
	 *          objects are ordered by the study ID. If there are
	 *          no studies stored in the database an array of length
	 *          0 is returned.
	 */
	public Study fetchStudyForName(String studyName, boolean withChilden);
	
	/**
	 * Fetch all studies that are part of a project including
	 * including all child objects assigned to a study except
	 * segments.
	 * 
	 * @param projectId The project ID for which the database will
	 *         be queried.
	 * @return Returns an array of Study objects. The Study
	 *          objects are ordered by the study ID. If there are
	 *          no studies stored in the database an array of length
	 *          0 is returned.
	 */
	public Study[] fetchStudiesForProject(int projectId);
	
	/**
	 * Fetch all studies that are part of a project.
	 * 
	 * @param projectId The project ID for which the database will
	 *         be queried.
	 * @param withChrildren If true the corresponding child data of a
	 *         study will also be fetched from the database.
	 *         Segment data will not be fetched. This should be done
	 *         separately if needed.
	 * @return Returns an array of Study objects. The Study
	 *          objects are ordered by the study ID. If there are
	 *          no studies stored in the database an array of length
	 *          0 is returned.
	 */
	public Study[] fetchStudiesForProject(int projectId, boolean withChrildren);
	
	/**
	 * Fetch all studies that are part of a set of projects including
	 * including all child objects assigned to a study except
	 * segments.
	 * 
	 * @param projectIds An array of project IDs for which the database will
	 *         be queried.
	 * @return Returns an array of Study objects. The Study
	 *          objects are ordered by the study ID. If there are
	 *          no studies stored in the database an array of length
	 *          0 is returned.
	 */
	public Study[] fetchStudiesForProject(int[] projectIds);
	
	/**
	 * Fetch all studies that are part of a set of projects.
	 * 
	 * @param projectIds An array of project IDs for which the database will
	 *         be queried.
	 * @param withChrildren If true the corresponding child data of a
	 *         study will also be fetched from the database.
	 *         Segment data will not be fetched. This should be done
	 *         separately if needed.
	 * @return Returns an array of Study objects. The Study
	 *          objects are ordered by the study ID. If there are
	 *          no studies stored in the database an array of length
	 *          0 is returned.
	 */
	public Study[] fetchStudiesForProject(int[] projectIds, boolean withChrildren);
	
	
	/**
	 * 
	 * Fetch all studies that are not a member of the given project.
	 * 
	 * @param projectIds
	 * @param withChrildren
	 * @return Returns an array of Study objects. The Study objects are
	 *          ordered by the study ID. If there are no studies stored in
	 *          the database an array of length 0 is returned.
	 */
	public Study[] fetchStudiesNotInProject(int projectIds, boolean withChrildren);
	
	/**
	 * Counts how often a study is associated to a project.
	 * 
	 * @param studyId
	 * @return Number of projects that stire the study.
	 */
	public int countStudyInProjects(int studyId);
	
	/**
	 * Removes a study from the database.
	 * 
	 * @param studyId the database ID the the data that will be removed.
	 */
	public void deleteStudy(int studyId);
	
	/**
	 * Removes a study from the database.
	 * 
	 * @param mstudy The Study object for that data that should
	 *         be removed.
	 */
	public void deleteStudy(Study study);
	
	final static String TYPE = "StudyAdaptor";
}