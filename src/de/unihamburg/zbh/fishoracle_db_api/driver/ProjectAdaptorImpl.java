package de.unihamburg.zbh.fishoracle_db_api.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.unihamburg.zbh.fishoracle_db_api.data.Group;
import de.unihamburg.zbh.fishoracle_db_api.data.Microarraystudy;
import de.unihamburg.zbh.fishoracle_db_api.data.Project;
import de.unihamburg.zbh.fishoracle_db_api.data.ProjectAccess;

public class ProjectAdaptorImpl extends BaseAdaptor implements ProjectAdaptor {

	protected ProjectAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}

	@Override
	protected String[] columns() {
		return new String[]{"project_id",
							"name",
							"description"};
	}

	@Override
	public Object createObject(ResultSet rs) {
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
				
				MicroarraystudyAdaptor ma = (MicroarraystudyAdaptor) driver.getAdaptor("MicroarraystudyAdaptor");
				
				mstudies = ma.fetchMicroarraystudiesForProject(projectId);
				
				project.setMstudies(mstudies);
				
				ProjectAccess[] access = this.fetchProjectAccessForProject(projectId);
				
				project.setProjectAccess(access);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return project;
	}

	@Override
	protected String[] tables() {
		return new String[]{"project",
							"group_project_access",
							"microarraystudy_in_project"};
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
	
	//TODO test
	@Override
	public ProjectAccess[] fetchProjectAccessForProject(int id) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		ProjectAccess projectAccess = null;
		ArrayList<ProjectAccess> projectAccessContainer = new ArrayList<ProjectAccess>();
		ProjectAccess[] projectAccesses = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("group_project_access_id, group_id, access_type")
			.append(" FROM ").append("group_project_access")
			.append(" WHERE project_id = " + id)
			.append(" ORDER BY project_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			int projectAccessId;
			int groupId;
			String accessType;
			
			while (rs.next()) {
				projectAccessId = rs.getInt(1);
				groupId = rs.getInt(2);
				accessType = rs.getString(3);
				
				GroupAdaptor ga = (GroupAdaptor) driver.getAdaptor("GroupAdaptor");
				
				Group group = ga.fetchGroupById(groupId);
				
				projectAccess = new ProjectAccess(projectAccessId, group, accessType);
				
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
	public Project[] fetchAllProjects() throws Exception {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Project project = null;
		ArrayList<Project> projectContainer = new ArrayList<Project>();
		Project[] projects = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("project.project_id, project.name, project.description")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" ORDER BY project_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				project = (Project) o;
				projectContainer.add(project);
			}
			
			if(project == null){

					throw new AdaptorException("There are no projects available.");
			}
			
			projects = new Project[projectContainer.size()];
			
			projectContainer.toArray(projects);
			
		} catch (Exception e){
			throw new Exception(e.getMessage());
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return projects;
	}

	@Override
	public Project fetchProjectById(int id) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Project project = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("project.project_id, project.name, project.description")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" LEFT JOIN microarraystudy_in_project ON microarraystudy_in_project.project_id = project.project_id")
			.append(" WHERE ").append("project.project_id = " + id);
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				project = (Project) o;
				
			}
			
			if(project == null){
				
				throw new AdaptorException("A project with ID: " + id + " does not exist.");
				
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
	public Project[] fetchProjectsForGroup(String accessRights) {
		// TODO Auto-generated method stub
		return null;
	}

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
			
			query.append("INSERT INTO ").append(getPrimaryTableName())
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
	public Project[] fetchProjectsForMicroarraystudy(int mstudyId) {
		// TODO Auto-generated method stub
		return null;
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
		//TODO test return
		return projectAccess;
	}

	@Override
	public String fetchAccessRightforGroup(int projectId, int groupId) {
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
			
			if(right == null){
				
				throw new AdaptorException("The group with ID: " + groupId + " has no access right for the project with the ID: " + projectId + ".");
				
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
	public void modifyGroupAccessForProject(int groupId, int projectId,
			String accessRights) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			query.append("UDATE ")
			.append("groupp_project_access")
			.append(" SET access_type = '" + accessRights + "'")
			.append(" WHERE ").append("group_id = " + groupId + " AND projec_id = " + projectId);
			
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
	public void removeGroupAccessFromProject(int groupId, int projectId,
			String accessRights) {
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
	
}
