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

import de.unihamburg.zbh.fishoracle_db_api.data.Group;
import de.unihamburg.zbh.fishoracle_db_api.data.Project;
import de.unihamburg.zbh.fishoracle_db_api.data.ProjectAccess;

/**
 * @author Malte Mader
 *
 */
public interface ProjectAdaptor {
	
	/**
	 * Stores a new project in the database.
	 * 
	 * @param project The Project object containing the data to be stored.
	 * @return Returns the database ID of the newly added project
	 */
	public int storeProject(Project project);
	
	/**
	 * Stores a new project in the database.
	 * 
	 * @param name The project name.
	 * @param description A text describing the contents or purpose of
	 *         the project.
	 * @return Returns the database ID of the newly added project
	 */
	public int storeProject(String name, String description);
	
	/**
	 * Fetches all projects that are stored in the database including all
	 * existing child objects of each Project object. 
	 * 
	 * @return Returns an array of Project objects. The Project objects are
	 *          ordered by the project ID. If there are no projects stored in
	 *          the database an array of length 0 is returned.
	 */
	public Project[] fetchAllProjects();
	
	/**
	 * Fetches all projects that are stored in the database.
	 * 
	 * @param withChildren If true all assigned child objects of a project
	 *         will also be fetched from the database.
	 * @return Returns an array of Project objects. The Project objects are
	 *          ordered by the project ID. If there are no projects stored in
	 *          the database an array of length 0 is returned.
	 */
	public Project[] fetchAllProjects(boolean withChildren);
	
	/**
	 * Fetches project data for a given project ID including all
	 * existing child objects of each Project object. 
	 * 
	 * @param projctId The project ID for which the database will be queried.
	 * @return Returns a Project object. If the ID does not exist null
	 *          is returned.
	 */
	public Project fetchProjectById(int projectId);
	
	/**
	 * Fetches project data for a given project ID.
	 * 
	 * @param projectId The project ID for which the database will be queried.
	 * @param withChildren If true all assigned child objects of a project
	 *         will also be fetched from the database.
	 * @return Returns a Project object. If the ID does not exist null
	 *          is returned.
	 */
	public Project fetchProjectById(int projectId, boolean withChildren);
	
	//TODO implement
	/**
	 * XXX This has not been implemented yet!
	 * Fetches all projects that contain a certain microarraystudy including all
	 * existing child objects of each Project object. 
	 * 
	 * @param mstudyId The microarray study ID for which the database will
	 *         be queried.
	 * @return Returns an array of Project objects. The Project objects are
	 *          ordered by the project ID. If there are no projects stored in
	 *          the database an array of length 0 is returned.
	 */
	//public Project[] fetchProjectsForMicroarraystudy(int mstudyId);
	//TODO implement
	/**
	 * XXX This has not been implemented yet!
	 * Fetches all projects that contain a certain microarraystudy.
	 * 
	 * @param mstudyId The microarray study ID for which the database will
	 *         be queried.
	 * @param withChildren If true all assigned child objects of a project
	 *         will also be fetched from the database.
	 * @return Returns an array of Project objects. The Project objects are
	 *          ordered by the project ID. If there are no projects stored in
	 *          the database an array of length 0 is returned.
	 */
	//public Project[] fetchProjectsForMicroarraystudy(int mstudyId, boolean withChildren);
	
	/**
	 * Fetches the access right to a certain project for a given group.  
	 * 
	 * @param projectId The database ID of the project for which the database
	 *         will be queried.
	 * @param groupId The database ID of the group for which the database
	 *         will be queried.
	 * @return Returns a string encoding the access right. "r" for read and
	 *          "rw" for read and write access. If no access right for the
	 *           project and group exit null is returned.
	 */
	public String fetchAccessRightForGroup(int projectId, int groupId);
	
	/**
	 * Fetches all ProjectAccess objects for a given project including all
	 * existing child objects of each ProjectAccess object. 
	 * 
	 * @param projectId The database ID of the project for which the database
	 *         will be queried.
	 * @return Returns an array of ProjectAccess objects. The ProjectAccess
	 *          objects are ordered by the project access ID. If there are no
	 *          project accesses stored in the database an array of length 0
	 *          is returned.
	 */
	ProjectAccess[] fetchProjectAccessForProject(int projectId);
	
	/**
	 * Fetches all ProjectAccess objects for a given project.
	 * 
	 * @param projectId The database ID of the project for which the database
	 *         will be queried.
	 * @param withChildren If true all assigned child objects of a project
	 *         access will also be fetched from the database.
	 * @return Returns an array of ProjectAccess objects. The ProjectAccess
	 *          objects are ordered by the project access ID. If there are no
	 *          project accesses stored in the database an array of length 0
	 *          is returned.
	 */
	ProjectAccess[] fetchProjectAccessForProject(int projectId, boolean withChildren);
	
	/**
	 * Fetches all ProjectAccess objects for a given set of groups including
	 * all existing child objects of each ProjectAccess object.
	 * 
	 * @param groups An array of groups for which project accesses will be
	 *         fetched.
	 * @return Returns an array of ProjectAccess objects. The ProjectAccess
	 *          objects are ordered by the project access ID. If there are no
	 *          project accesses stored in the database an array of length 0
	 *          is returned.
	 */
	public ProjectAccess[] fetchProjectAccessForGroups(Group[] groups);
	
	/**
	 * Fetches all ProjectAccess objects for a given set of groups.
	 * 
	 * @param groups An array of groups for which project accesses will be
	 *         fetched.
	 * @param write Fetches only groups with writing rights if true, else
	 *         all access rights for the specified groups will be fetched.
	 * @param withChildren If true all assigned child objects of a project
	 *         access will also be fetched from the database.
	 * @return Returns an array of ProjectAccess objects. The ProjectAccess
	 *          objects are ordered by the project access ID. If there are no
	 *          project accesses stored in the database an array of length 0
	 *          is returned.
	 */
	public ProjectAccess[] fetchProjectAccessForGroups(Group[] groups, boolean write, boolean withChildren);
	
	/**
	 * Fetches all projects for which access rights exists including all
	 * existing child objects of each Project object. 
	 * 
	 * @param projectAccess The ProjectAccess objects for which the database
	 *         will be queried.
	 * @return Returns an array of Project objects. The Project objects are
	 *          ordered by the project ID. If there are no projects stored in
	 *          the database an array of length 0 is returned.
	 */
	public Project[] fetchProjectsForProjectAccess(ProjectAccess[] projectAccess);
	
	/**
	 * Fetches all projects for which access rights exists.
	 * 
	 * @param projectAccess The ProjectAccess objects for which the database
	 *         will be queried.
	 * @param withChildren If true all assigned child objects of a project
	 *         will also be fetched from the database.
	 * @return Returns an array of Project objects. The Project objects are
	 *          ordered by the project ID. If there are no projects stored in
	 *          the database an array of length 0 is returned.
	 */
	public Project[] fetchProjectsForProjectAccess(ProjectAccess[] projectAccess, boolean withChildren);
	
	/**
	 * Grant access to a project for a group.
	 * 
	 * @param groupId The database ID for the group that will get the access
	 *         right.
	 * @param projectId The database ID for the project to be accessed.
	 * @param accessRights A string encoding the access right. "r" for read
	 *         only and "rw" for read and write access.
	 * @return Returns a ProjectAccess object representing the new access rights.
	 */
	public ProjectAccess addGroupAccessToProject(int groupId, int projectId, String accessRights);
	
	/**
	 * Removes the access right for a group to a project.
	 * 
	 * @param groupId The database ID for the group for which the access
	 *         right will be revoked.
	 * @param projectId The database ID of the project from which the
	 *         access right will be revoked.
	 */
	public void removeGroupAccessFromProject(int groupId, int projectId);
	
	/**
	 * Removes the access right for a specific project access.
	 * 
	 * @param projectAccessId The database ID for the project access that
	 *         will be removed.
	 */
	public void removeGroupAccessFromProject(int projectAccessId);
	
	/**
	 * Change the access to a project for a group.
	 * 
	 * @param groupId The database ID for the group that will get the access
	 *         right.
	 * @param projectId The database ID for the project to be accessed.
	 * @param accessRights A string encoding the access right. "r" for read
	 *         only and "rw" for read and write access.
	 */
	public void modifyGroupAccessForProject(int groupId, int projectId, String accessRights);
	
	/**
	 * Adds a microarray study to a project.
	 * 
	 * @param microarraystudyId The database ID of the microarray study that
	 *         will be added. 
	 * @param projectId The database ID of the project that will be assigned
	 *         the new microarray study. 
	 */
	public void addMicroarraystudyToProject(int microarraystudyId, int projectId);
	
	/**
	 * Remove a microarray study from a project.
	 * 
	 * @param microarraystudyId The database ID of the microarray study that
	 *         will be removed. 
	 * @param projectId The database ID of the project from which the
	 *         microarray study will be removed.
	 */
	public void removeMicroarraystudyFromProject(int microarraystudyId, int projectId);
	
	/**
	 * Removes a project from the database.
	 * 
	 * @param project The Project object to be removed from the database.
	 */
	public void deleteProject(Project project);
	
	/**
	 * Removes a project from the database.
	 * 
	 * @param projectId The project id for which the data will be removed.
	 */
	public void deleteProject(int projectId);
	
	final static String TYPE = "ProjectAdaptor";
}