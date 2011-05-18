package de.unihamburg.zbh.fishoracle_db_api.driver;

import de.unihamburg.zbh.fishoracle_db_api.data.Project;
import de.unihamburg.zbh.fishoracle_db_api.data.ProjectAccess;

public interface ProjectAdaptor {

	public ProjectAccess addGroupAccessToProject(int groupId, int projectId, String accessRights);
	public void removeGroupAccessFromProject(int groupId, int projectId, String accessRights);
	public void modifyGroupAccessForProject(int groupId, int projectId, String accessRights);
	
	public String fetchAccessRightforGroup(int projectId, int groupId);
	public ProjectAccess[] fetchProjectAccessForProject(int id);
	//public ProjectAccess fetchProjectAccess(int projectId, int groupId);
	
	public void addMicroarraystudyToProject(int microarraystudyId, int projectId);
	public void removeMicroarraystudyFromProject(int microarraystudyId, int projectId);
	public Project[] fetchProjectsForMicroarraystudy(int mstudyId);
	
	public Project[] fetchAllProjects() throws Exception;
	public Project fetchProjectById(int id);
	public Project[] fetchProjectsForGroup(String accessRights);
	
	public int storeProject(Project project);
	public int storeProject(String name, String description);
	public void deleteProject(Project project);
	
	final static String TYPE = "ProjectAdaptor";
	
}
