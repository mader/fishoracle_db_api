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
import de.unihamburg.zbh.fishoracle_db_api.data.Microarraystudy;
import de.unihamburg.zbh.fishoracle_db_api.data.Project;
import de.unihamburg.zbh.fishoracle_db_api.data.ProjectAccess;

/**
 * @author Malte Mader
 *
 */
public class ProjectAdaptorImpl extends BaseAdaptor implements ProjectAdaptor {

	protected ProjectAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}

	@Override
	protected String[] tables() {
		return new String[]{"project",
							"group_project_access",
							"microarraystudy_in_project"};
	}

	@Override
	protected String[] columns() {
		return new String[]{"project_id",
							"name",
							"description"};
	}
	
	@Override
	protected String[][] leftJoins() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int storeProject(Project project){
		
		return storeProject(project.getName(), project.getDescription());
	}
	
	@Override
	public int storeProject(String name, String description) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		int newProjectId = 0;
		
		try{
			
			conn = getConnection();
			
			query.append("INSERT INTO ").append(super.getPrimaryTableName())
			.append(" (name, description)")
			.append(" VALUES ")
			.append("('" + name + "', '" + description + "')");
			
			ResultSet rs = executeUpdateGetKeys(conn, query.toString());
			
			if(rs.next()){
				newProjectId = rs.getInt(1);
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return newProjectId;
	}

	/**
	 * Create an ProjectAccess object from a database result set.
	 * 
	 * @param rs The SQL result set.
	 * @param withChildren Fetches a complete Group object if true.
	 */
	private ProjectAccess createPAObject(ResultSet rs, boolean withChildren){
		ProjectAccess projectAccess = null;
		int projectAccessId;
		int projectId;
		int groupId;
		String accessType;
		
		try {
			if(rs.next()) {
				projectAccessId = rs.getInt(1);
				projectId = rs.getInt(2);
				groupId = rs.getInt(3);
				accessType = rs.getString(4);
				
				if(withChildren){
					
					GroupAdaptor ga = (GroupAdaptor) driver.getAdaptor("GroupAdaptor");
				
					Group group = ga.fetchGroupById(groupId);
				
					projectAccess = new ProjectAccess(projectAccessId, projectId, group, accessType);
					
				} else {
					projectAccess = new ProjectAccess(projectAccessId, projectId, groupId, accessType);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return projectAccess;
	}
	
	@Override
	public Object createObject(ResultSet rs){
		return createObject(rs, true);
	}
	
	public Object createObject(ResultSet rs, boolean withChildren) {
		Microarraystudy[] mstudies = null;
		Project project = null;
		int projectId = 0;
		String name = null;
		String description;
		
		try {
			if(rs.next()){
				projectId = rs.getInt(1);
				name = rs.getString(2);
				description = rs.getString(3);

				project = new Project(projectId, name, description);
				
				if(withChildren){
				
					MicroarraystudyAdaptor ma = (MicroarraystudyAdaptor) driver.getAdaptor("MicroarraystudyAdaptor");
				
					mstudies = ma.fetchMicroarraystudiesForProject(projectId);
				
					project.setMstudies(mstudies);
				
					ProjectAccess[] access = this.fetchProjectAccessForProject(projectId);
				
					project.setProjectAccess(access);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return project;
	}

	@Override
	public Project[] fetchAllProjects() {
		return fetchAllProjects(true);
	}
	
	@Override
	public Project[] fetchAllProjects(boolean withChrildren) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Project project = null;
		ArrayList<Project> projectContainer = new ArrayList<Project>();
		Project[] projects = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" ORDER BY project_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs, withChrildren)) != null) {
				project = (Project) o;
				projectContainer.add(project);
			}
			
			projects = new Project[projectContainer.size()];
			
			projectContainer.toArray(projects);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return projects;
	}
	
	@Override
	public Project fetchProjectById(int projectId) {
		return fetchProjectById(projectId, true);
	}
	
	@Override
	public Project fetchProjectById(int projectId, boolean withChildren) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Project project = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" LEFT JOIN microarraystudy_in_project ON microarraystudy_in_project.project_id = project.project_id")
			.append(" WHERE ").append("project.project_id = " + projectId);
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			if ((o = createObject(rs, withChildren)) != null) {
				project = (Project) o;
				
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return project;
	}

	@Override
	public String fetchAccessRightForGroup(int projectId, int groupId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		String right = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("access_type")
			.append(" FROM ").append("group_project_access")
			.append(" WHERE ").append("group_id = " + groupId + " AND project_id = " + projectId);
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			while (rs.next()) {
				right = rs.getString(1);
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return right;
	}
	
	@Override
	public ProjectAccess[] fetchProjectAccessForProject(int projectId) {
		return fetchProjectAccessForProject(projectId, true);
	}
	
	@Override
	public ProjectAccess[] fetchProjectAccessForProject(int projectId, boolean withChildren) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		ProjectAccess projectAccess = null;
		ArrayList<ProjectAccess> projectAccessContainer = new ArrayList<ProjectAccess>();
		ProjectAccess[] projectAccesses = null;
		
		try{
			
			conn = getConnection();
			
			query.append("SELECT ").append("group_project_access_id, project_id, group_id, access_type")
			.append(" FROM ").append("group_project_access")
			.append(" WHERE project_id = " + projectId)
			.append(" ORDER BY project_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			ProjectAccess pa;
			
			while ((pa = createPAObject(rs, withChildren)) != null) {
				projectAccess = pa;
				projectAccessContainer.add(projectAccess);
				
			}
			
			projectAccesses = new ProjectAccess[projectAccessContainer.size()];
			
			projectAccessContainer.toArray(projectAccesses);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return projectAccesses;
	}
	
	@Override
	public ProjectAccess[] fetchProjectAccessForGroups(Group[] groups){
		return fetchProjectAccessForGroups(groups, false, true);
	}
	
	public ProjectAccess[] fetchProjectAccessForGroups(Group[] groups, boolean ReadWrite, boolean withChildren){
		
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		ProjectAccess projectAccess = null;
		ArrayList<ProjectAccess> projectAccessContainer = new ArrayList<ProjectAccess>();
		ProjectAccess[] projectAccesses = null;
		
		try{
			
			conn = getConnection();	
			
			String whereClause = "";
			for(int i=0; i < groups.length; i++){
				if(i == groups.length -1){
					whereClause += "group_id = " + groups[i].getId();
				} else {
					whereClause += "group_id = " + groups[i].getId() + " OR ";
				}
			}
			
			if(ReadWrite){
				whereClause += " AND access_type = 'rw'";
			}
			
			query.append("SELECT DISTINCT ").append("group_project_access_id, project_id, group_id, access_type")
			.append(" FROM ").append("group_project_access")
			.append(" WHERE ").append(whereClause)
			.append(" ORDER BY project_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			ProjectAccess pa;
			
			while ((pa = createPAObject(rs, withChildren)) != null) {
				projectAccess = pa;
				projectAccessContainer.add(projectAccess);
				
			}
			
			projectAccesses = new ProjectAccess[projectAccessContainer.size()];
			
			projectAccessContainer.toArray(projectAccesses);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		
		return projectAccesses;
	}
	
	@Override
	public Project[] fetchProjectsForProjectAccess(ProjectAccess[] pAccess){
		return fetchProjectsForProjectAccess(pAccess, true);
	}
	
	@Override
	public Project[] fetchProjectsForProjectAccess(ProjectAccess[] pAccess, boolean withChildren){
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Project project = null;
		ArrayList<Project> projectContainer = new ArrayList<Project>();
		Project[] projects = null;
		
		try{
			
			conn = getConnection();
			
			String whereClause = "";
			for(int i=0; i < pAccess.length; i++){
				if(i == pAccess.length -1){
					whereClause += "project_id = " + pAccess[i].getProjectId();
				} else {
					whereClause += "project_id = " + pAccess[i].getProjectId() + " OR ";
				}
			}
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" WHERE ").append(whereClause)
			.append(" ORDER BY project_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs, withChildren)) != null) {
				project = (Project) o;
				projectContainer.add(project);
			}
				
			projects = new Project[projectContainer.size()];
			
			projectContainer.toArray(projects);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return projects;
	}
	
	public ProjectAccess addGroupAccessToProject(ProjectAccess pac){
		return addGroupAccessToProject(pac.getGroupId(), pac.getProjectId(), pac.getAccess());
	}
	
	@Override
	public ProjectAccess addGroupAccessToProject(int groupId, int projectId,
			String accessRights) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		int newProjectAccessId = 0;
		ProjectAccess projectAccess = null;
		
		try{
			
			conn = getConnection();
			
			query.append("INSERT INTO ").append("group_project_access")
			.append(" (project_id, group_id, access_type)")
			.append(" VALUES ")
			.append("('" + projectId + "', '" + groupId + "', '" + accessRights + "')");
			
			ResultSet rs = executeUpdateGetKeys(conn, query.toString());
			
			while(rs.next()){
				newProjectAccessId = rs.getInt(1);
			}
			
			GroupAdaptor ga = (GroupAdaptor) driver.getAdaptor("GroupAdaptor");
			
			Group group = ga.fetchGroupById(groupId);
			
			projectAccess = new ProjectAccess(newProjectAccessId, group, accessRights);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return projectAccess;
	}

	@Override
	public void removeGroupAccessFromProject(int groupId, int projectId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			query.append("DELETE FROM ")
			.append("group_project_access")
			.append(" WHERE ").append("group_id = " + groupId + " AND project_id = " + projectId);
			
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
	public void removeGroupAccessFromProject(int projectAccessId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			query.append("DELETE FROM ")
			.append("group_project_access")
			.append(" WHERE ").append("group_project_access_id = " + projectAccessId);
			
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
	public void modifyGroupAccessForProject(int groupId, int projectId,
			String accessRights) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			query.append("UPDATE ")
			.append("group_project_access")
			.append(" SET access_type = '" + accessRights + "'")
			.append(" WHERE ").append("group_id = " + groupId + " AND project_id = " + projectId);
			
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
	public void addMicroarraystudyToProject(int microarraystudyId, int projectId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			query.append("INSERT INTO ").append("microarraystudy_in_project")
			.append(" (project_id, microarraystudy_id)")
			.append(" VALUES ")
			.append("('" + projectId + "', '" + microarraystudyId + "')");
			
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
	public void removeMicroarraystudyFromProject(int microarraystudyId,
			int projectId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			query.append("DELETE FROM ")
			.append("microarraystudy_in_project")
			.append(" WHERE ").append("project_id = " + projectId + " AND microarraystudy_id = " + microarraystudyId);
			
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
	public void deleteProject(Project project) {
	
		deleteProject(project.getId());
	}

	@Override
	public void deleteProject(int projectId) {
		Connection conn = null;
		StringBuffer projectQuery = new StringBuffer();
		StringBuffer groupProjectAccessQuery = new StringBuffer();
		StringBuffer microarraystudyInProjectQuery = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			groupProjectAccessQuery.append("DELETE FROM ")
			.append("group_project_access")
			.append(" WHERE ").append("project_id = " + projectId);
			
			executeUpdate(conn, groupProjectAccessQuery.toString());
			
			microarraystudyInProjectQuery.append("DELETE FROM ")
			.append("microarraystudy_in_project")
			.append(" WHERE ").append("project_id = " + projectId);
			
			executeUpdate(conn, microarraystudyInProjectQuery.toString());
			
			projectQuery.append("DELETE FROM ")
			.append(getPrimaryTableName())
			.append(" WHERE ").append("project_id = " + projectId);
			
			executeUpdate(conn, projectQuery.toString());
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}		
	}
}