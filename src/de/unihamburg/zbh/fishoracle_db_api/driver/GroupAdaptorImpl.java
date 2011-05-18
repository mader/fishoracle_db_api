package de.unihamburg.zbh.fishoracle_db_api.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.unihamburg.zbh.fishoracle_db_api.data.Group;
import de.unihamburg.zbh.fishoracle_db_api.data.User;

public class GroupAdaptorImpl extends BaseAdaptor implements GroupAdaptor{

	//TODO projects...
	protected GroupAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}

	@Override
	protected String[] columns() {
		return new String[]{"fo_group_id",
							"name",
							"isactive"};
	}

	@Override
	public Object createObject(ResultSet rs) {
		User[] users;
		Group group = null;
		int groupId = 0;
		String name = null;
		boolean isActive;
		
		try {
			if(rs.next()){
				groupId = rs.getInt(1);
				name = rs.getString(2);
				isActive = rs.getBoolean(3);

				group = new Group(groupId, name, isActive);
				
				UserAdaptor ua = (UserAdaptor) driver.getAdaptor("UserAdaptor");
				
				users = ua.fetchUsersForGroup(groupId);
				
				group.setUsers(users);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return group;
	}

	@Override
	protected String[] tables() {
		return new String[]{"fo_group",
							"user_in_group",
							"group_project_access"};
	}

	@Override
	public void deleteGroup(Group group) {
		Connection conn = null;
		StringBuffer groupQuery = new StringBuffer();
		StringBuffer userInGroupQuery = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			userInGroupQuery.append("DELETE FROM ")
			.append("user_in_group")
			.append(" WHERE ").append("group_id = " + group.getId());
			
			executeUpdate(conn, userInGroupQuery.toString());
			
			groupQuery.append("DELETE FROM ")
			.append(getPrimaryTableName())
			.append(" WHERE ").append("fo_group_id = " + group.getId());
			
			executeUpdate(conn, groupQuery.toString());
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
	}

	@Override
	public Group[] fetchAllGroups() throws Exception {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Group group = null;
		ArrayList<Group> groupContainer = new ArrayList<Group>();
		Group[] groups = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("fo_group.fo_group_id, fo_group.name, fo_group.isactive")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" ORDER BY fo_group_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				group = (Group) o;
				groupContainer.add(group);
			}
			
			if(group == null){

					throw new AdaptorException("There are no groups available.");
			}
			
			groups = new Group[groupContainer.size()];
			
			groupContainer.toArray(groups);
			
		} catch (Exception e){
			throw new Exception(e.getMessage());
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return groups;
	}

	@Override
	public Group fetchGroupById(int id) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Group group = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("fo_group.fo_group_id, fo_group.name, fo_group.isactive")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" LEFT JOIN user_in_group ON user_in_group.group_id = fo_group.fo_group_id")
			.append(" WHERE ").append("fo_group.fo_group_id = " + id);
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				group = (Group) o;
				
			}
			
			if(group == null){
				
				throw new AdaptorException("A group with ID: " + id + " does not exist.");
				
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return group;
	}

	public int storeGroup(Group group){
		
		return storeGroup(group.getName(), group.getIsactiveAsInt()); 
	}
	
	@Override
	public int storeGroup(String name, int isactive) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		int newGroupId = 0;
		
		try{
			
			conn = getConnection();
			
			query.append("INSERT INTO ").append(getPrimaryTableName())
			.append(" (name, isactive)")
			.append(" VALUES ")
			.append("('" + name + "', '" + isactive + "')");
			
			ResultSet rs = executeUpdateGetKeys(conn, query.toString());
			
			if(rs.next()){
				newGroupId = rs.getInt(1);
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return newGroupId;
	}

	public Group[] fetchGroupsForUser(int userId){
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Group group = null;
		ArrayList<Group> groupContainer = new ArrayList<Group>();
		Group[] groups = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("fo_group_id, name, isactive")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" LEFT JOIN user_in_group ON user_in_group.group_id = fo_group.fo_group_id")
			.append(" WHERE ").append("user_in_group.user_id = " + userId)
			.append(" ORDER BY fo_group_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				group = (Group) o;
				groupContainer.add(group);
			}
			
			if(group == null){

					throw new AdaptorException("There are no groups available.");
			}
			
			groups = new Group[groupContainer.size()];
			
			groupContainer.toArray(groups);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return groups;
	}
	
	//TODO test
	@Override
	public Group[] fetchGroupsNotInProject(int projectId) {
		Connection conn = null;
		StringBuffer query1 = new StringBuffer();
		StringBuffer query2 = new StringBuffer();
		Group group = null;
		ArrayList<Group> groupContainer = new ArrayList<Group>();
		Group[] groups = null;
		
		try{
			
			conn = getConnection();	
			
			query1.append("SELECT ").append("fo_group.fo_group_id")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" LEFT JOIN group_project_access ON fo_group.fo_group_id = group_project_access.group_id")
			.append(" WHERE ").append("group_project_access.project_id = " + projectId)
			.append(" ORDER BY fo_group.fo_group_id ASC");
			
			ResultSet rs1 = executeQuery(conn, query1.toString());
			
			int groupId = 0;
			ArrayList<Integer> groupIdContainer = new ArrayList<Integer>();
			
			while(rs1.next()){
				
				groupId = rs1.getInt(1);
				
				groupIdContainer.add(groupId);
			}
			
			query2.append("SELECT ").append("fo_group.fo_group_id, fo_group.name, fo_group.isactive")
			.append(" FROM ").append(getPrimaryTableName());
			
			String whereClause = " ";
			boolean where = true;
			if(groupIdContainer.size() > 0){
				for(int i=0; i < groupIdContainer.size(); i++){
					
					if(where){
						whereClause += " WHERE fo_group.fo_group_id != " + groupIdContainer.get(i);	
						where = false;
					} else {
						whereClause += " AND fo_group.fo_group_id != " + groupIdContainer.get(i);
					}
				}
			}
			query2.append(whereClause).append(" ORDER BY fo_group.fo_group_id ASC");
			
			ResultSet rs2 = executeQuery(conn, query2.toString());
			
			Object o;
			
			while ((o = createObject(rs2)) != null) {
				group = (Group) o;
				groupContainer.add(group);
			}
			
			groups = new Group[groupContainer.size()];
			
			groupContainer.toArray(groups);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return groups;
	}
	
	@Override
	public void addUserToGroup(int userId, int groupId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			query.append("INSERT INTO ").append("user_in_group")
			.append(" (user_id, group_id)")
			.append(" VALUES ")
			.append("('" + userId + "', '" + groupId + "')");
			
			executeUpdate(conn, query.toString());
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
	}

	@Override
	public void removeUserFromGroup(int userId, int groupId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			query.append("DELETE FROM ")
			.append("user_in_group")
			.append(" WHERE ").append("user_id = " + userId + " AND group_id = " + groupId);
			
			executeUpdate(conn, query.toString());
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
	}
}
