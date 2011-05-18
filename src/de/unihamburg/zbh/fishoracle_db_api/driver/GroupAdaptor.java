package de.unihamburg.zbh.fishoracle_db_api.driver;

import de.unihamburg.zbh.fishoracle_db_api.data.Group;

public interface GroupAdaptor {

	public Group[] fetchAllGroups() throws Exception;
	public Group fetchGroupById(int id);
	public void addUserToGroup(int userId, int groupId);
	public void removeUserFromGroup(int userId, int groupId);
	public Group[] fetchGroupsForUser(int userId);
	public Group[] fetchGroupsNotInProject(int projectId);
	public int storeGroup(Group group);
	public int storeGroup(String name, int isactive);
	public void deleteGroup(Group group);
	
	final static String TYPE = "GroupAdaptor";
	
}
