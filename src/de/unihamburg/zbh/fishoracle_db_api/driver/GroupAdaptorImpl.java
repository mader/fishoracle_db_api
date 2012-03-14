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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.unihamburg.zbh.fishoracle_db_api.data.Group;
import de.unihamburg.zbh.fishoracle_db_api.data.User;

public class GroupAdaptorImpl extends BaseAdaptor implements GroupAdaptor{
	
	protected GroupAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}

	@Override
	protected String[] tables() {
		return new String[]{"fo_group",
							"user_in_group",
							"group_project_access"};
	}

	@Override
	protected String[] columns() {
		return new String[]{"fo_group_id",
							"name",
							"isactive"};
	}
	
	@Override
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
			
			query.append("INSERT INTO ").append(super.getPrimaryTableName())
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
	
	@Override
	public Object createObject(ResultSet rs) {
		return createObject(rs, true);
	}
	
	public Object createObject(ResultSet rs, boolean withChildren) {
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
				
				if(withChildren){
					
					UserAdaptor ua = (UserAdaptor) driver.getAdaptor("UserAdaptor");
				
					users = ua.fetchUsersForGroup(groupId);
				
					group.setUsers(users);
					
				} else {
					users = new User[0];
					group.setUsers(users);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return group;
	}
	
	@Override
	public Group[] fetchAllGroups(){
		return fetchAllGroups(true);
	}
	
	@Override
	public Group[] fetchAllGroups(boolean withChildren) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Group group = null;
		ArrayList<Group> groupContainer = new ArrayList<Group>();
		Group[] groups = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" ORDER BY fo_group_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs, withChildren)) != null) {
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
	public Group[] fetchGroupsForUser(int userId){
		return fetchGroupsForUser(userId, true);
	}
	
	@Override
	public Group[] fetchGroupsForUser(int userId, boolean withChildren){
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Group group = null;
		ArrayList<Group> groupContainer = new ArrayList<Group>();
		Group[] groups = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" LEFT JOIN user_in_group ON user_in_group.group_id = fo_group.fo_group_id")
			.append(" WHERE ").append("user_in_group.user_id = " + userId)
			.append(" ORDER BY fo_group_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs, withChildren)) != null) {
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
	public Group[] fetchGroupsForProject(int projectId){
		return fetchGroupsForProject(projectId, true);
	}
	
	@Override
	public Group[] fetchGroupsForProject(int projectId, boolean withChildren){
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Group group = null;
		ArrayList<Group> groupContainer = new ArrayList<Group>();
		Group[] groups = null;
		
		try{
			
			conn = getConnection();
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" LEFT JOIN group_project_access ON fo_group.fo_group_id = group_project_access.group_id")
			.append(" WHERE ").append("group_project_access.project_id = " + projectId)
			.append(" ORDER BY fo_group.fo_group_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs, withChildren)) != null) {
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
	public Group[] fetchGroupsNotInProject(int projectId) {
		return fetchGroupsNotInProject(projectId, true);
	}
	
	@Override
	public Group[] fetchGroupsNotInProject(int projectId, boolean withChildren) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Group group = null;
		ArrayList<Group> groupContainer = new ArrayList<Group>();
		Group[] groups = null;
		
		try{
			
			conn = getConnection();
			
			/* First get all groups that _are_ in the project.*/
			groups = fetchGroupsForProject(projectId, withChildren);
			
			/* Then get all groups but the groups we just received. */
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(getPrimaryTableName());
			
			String whereClause = " ";
			boolean where = true;
			if(groups.length > 0){
				for(int i=0; i < groups.length; i++){
					
					if(where){
						whereClause += " WHERE fo_group.fo_group_id != " + groups[i].getId();
						where = false;
					} else {
						whereClause += " AND fo_group.fo_group_id != " + groups[i].getId();
					}
				}
			}
			query.append(whereClause).append(" ORDER BY fo_group.fo_group_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs, withChildren)) != null) {
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
	public Group fetchGroupById(int groupId){
		return fetchGroupById(groupId, true);
	}
	
	@Override
	public Group fetchGroupById(int groupId, boolean withChildren) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Group group = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" WHERE ").append("fo_group.fo_group_id = " + groupId);
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			if ((o = createObject(rs, withChildren)) != null) {
				group = (Group) o;
				
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

	@Override
	public void addUserToGroup(int groupId, int userId) {
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
	public void removeUserFromGroup(int groupId, int userId) {
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
			.append(super.getPrimaryTableName())
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
}