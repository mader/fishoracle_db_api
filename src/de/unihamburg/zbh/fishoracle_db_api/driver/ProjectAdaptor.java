package de.unihamburg.zbh.fishoracle_db_api.driver;

import de.unihamburg.zbh.fishoracle_db_api.data.Project;

public interface ProjectAdaptor {

	public void addGroupAccessToProject(int groupId, int projectId, String accessRights);
	public void removeGroupAccessFromProject(int groupId, int projectId, String accessRights);
	public void modifyGroupAccessForProject(int groupId, int projectId, String accessRights);
	
	public String fetchAccessRightforGroup(int projectId, int groupId);
	
	public void addMicroarraystudyToProject(int microarraystudyId, int projectId);
	public void removeMicroarraystudyFromProject(int microarraystudyId, int projectId);
	public Project[] fetchProjectsForMicroarraystudy(int mstudyId);
	
	public Project[] fetchAllProjects();
	public Project fetchProjectById(int id);
	public Project[] fetchProjectsForGroup(String accessRights);
	
	public int storeProject(Project project);
	public void deleteProject(Project project);
	
	final static String TYPE = "ProjectAdaptor";
	
}
