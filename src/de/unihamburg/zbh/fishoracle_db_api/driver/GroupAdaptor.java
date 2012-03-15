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

/**
 * @author Malte Mader
 *
 */
public interface GroupAdaptor {
	
	/**
	 * Stores a new user group in the database.
	 * 
	 * @param group The Group Object. The group ID of the object will not
	 *         be used.
	 * @return Returns the database ID of the newly added group.
	 */
	public int storeGroup(Group group);
	
	/**
	 * Stores a new user group in the database.
	 * 
	 * @param name The name of the new group.
	 * @param isactive The activity status of the group.
	 * @return Returns the database ID of the newly added group.
	 */
	public int storeGroup(String name, int isactive);
	
	/**
	 * Fetches all groups that are stored in the database including all
	 *  users assigned to a group.
	 * 
	 * @return Returns an array of Group objects. The Group objects are
	 *          ordered by the group ID. If there are no groups stored in
	 *          the database an array of length 0 is returned.
	 */
	public Group[] fetchAllGroups();
	
	/**
	 * Fetches all groups that are stored in the database.
	 * 
	 * @param withChildren If true all assigned users of a group will also
	 *         be fetched from the database.
	 * @return Returns an array of Group objects. The Group objects are
	 *          ordered by the group ID. If there are no groups stored in
	 *          the database an array of length 0 is returned.
	 */
	public Group[] fetchAllGroups(boolean withChildren);
	
	/**
	 * Fetch all groups a user is assigned to including all users assigned
	 * to a group.
	 * 
	 * @param userId The ID of a given user.
	 * @return Returns an array of Group objects. The Group objects are
	 *          ordered by the group ID. If there are no groups stored in
	 *          the database an array of length 0 is returned.
	 */
	public Group[] fetchGroupsForUser(int userId);
	
	/**
	 * Fetch all groups a user is assigned to.
	 * 
	 * @param userId The ID of a given user.
	 * @param withChildren If true all assigned users of a group will also
	 *         be fetched from the database.
	 * @return Returns an array of Group objects. The Group objects are
	 *          ordered by the group ID. If there are no groups stored in
	 *          the database an array of length 0 is returned.
	 */
	public Group[] fetchGroupsForUser(int userId, boolean withChildren);
	
	/**
	 *  Fetch all groups that are a member of the given project including
	 * all users assigned to a group.
	 * 
	 * @param projectId The project ID for which the database will be queried.
	 * @return Returns an array of Group objects. The Group objects are
	 *          ordered by the group ID. If there are no groups stored in
	 *          the database an array of length 0 is returned.
	 */
	public Group[] fetchGroupsForProject(int projectId);
	
	/**
	 * Fetch all groups that are a member of the given project.
	 * 
	 * @param projectId The project ID for which the database will be queried.
	 * @param withChildren If true all assigned users of a group will also
	 *         be fetched from the database.
	 * @return Returns an array of Group objects. The Group objects are
	 *          ordered by the group ID. If there are no groups stored in
	 *          the database an array of length 0 is returned.
	 */
	public Group[] fetchGroupsForProject(int projectId, boolean withChildren);
	
	/**
	 * Fetch all groups that are not a member of the given project including
	 * all users assigned to a group.
	 * 
	 * @param projectId The project ID for which the database will be queried.
	 * @return Returns an array of Group objects. The Group objects are
	 *          ordered by the group ID. If there are no groups stored in
	 *          the database an array of length 0 is returned.
	 */
	public Group[] fetchGroupsNotInProject(int projectId);
	
	/**
	 * Fetch all groups that are not a member of the given project.
	 * 
	 * @param projectId The project ID for which the database will be queried.
	 * @param withChildren If true all assigned users of a group will also
	 *         be fetched from the database.
	 * @return Returns an array of Group objects. The Group objects are
	 *          ordered by the group ID. If there are no groups stored in
	 *          the database an array of length 0 is returned.
	 */
	public Group[] fetchGroupsNotInProject(int projectId, boolean withChildren);
	
	/**
	 * Fetches group data for a given group ID including all users assigned
	 * to a group.
	 * 
	 * @param groupId The group ID for which the database will be queried.
	 * @param withChildren If true all assigned users of a group will also
	 *        be fetched from the database.
	 * @return Returns a Group object. If the ID does not exist null
	 *          is returned.
	 */
	public Group fetchGroupById(int groupId);
	
	/**
	 * Fetches group data for a given group ID
	 * 
	 * @param groupId The group ID for which the database will be queried.
	 * @param withChildren If true all assigned users of a group will also
	 *        be fetched from the database.
	 * @return Returns a Group object. If the ID does not exist null
	 *          is returned.
	 */
	public Group fetchGroupById(int groupId, boolean withChildren);
	
	/**
	 * Assign a user to a group.
	 * 
	 * @param groupId The group ID.
	 * @param userId The ID for the user to be added.
	 */
	public void addUserToGroup(int groupId, int userId);
	
	/**
	 * Remove a user from a group.
	 * 
	 * @param groupId The group ID.
	 * @param userId The ID for the user to be removed.
	 */
	public void removeUserFromGroup(int groupId, int userId);
	
	/**
	 * Removes a group from the database.
	 * 
	 * @param group The Group object for that data that should be removed.
	 */
	public void deleteGroup(Group group);
	
	final static String TYPE = "GroupAdaptor";	
}