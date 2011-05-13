package de.unihamburg.zbh.fishoracle_db_api.driver;

import de.unihamburg.zbh.fishoracle_db_api.data.Group;

public interface GroupAdaptor {

	public Group[] fetchAllGroups();
	public Group fetchGroupById(int id);
	public void addUserToGroup(int userId, int groupId);
	public void removeUserFromGroup(int userId, int groupId);
	public Group[] fetchGroupsForUser(int userId);
	public int storeGroup(Group group);
	public void deleteGroup(Group group);
	
	final static String TYPE = "GroupAdaptor";
	
}
