package de.unihamburg.zbh.fishoracle_db_api.data;

public class ProjectAccess {

	private int id;
	private Group group;
	private int groupId;
	private String access;
	
	public ProjectAccess(int id, int groupId, String access) {
		this.id = id;
		this.groupId = groupId;
		this.access = access;
	}

	public ProjectAccess(int id, Group group, String access) {
		this.id = id;
		this.group = group;
		this.access = access;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
}
