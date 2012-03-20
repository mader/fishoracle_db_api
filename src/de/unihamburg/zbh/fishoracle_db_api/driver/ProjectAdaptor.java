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
import de.unihamburg.zbh.fishoracle_db_api.data.Project;
import de.unihamburg.zbh.fishoracle_db_api.data.ProjectAccess;

public interface ProjectAdaptor {

	public ProjectAccess addGroupAccessToProject(int groupId, int projectId, String accessRights);
	public void removeGroupAccessFromProject(int groupId, int projectId, String accessRights);
	public void removeGroupAccessFromProject(int projectAccessId);
	public void modifyGroupAccessForProject(int groupId, int projectId, String accessRights);
	
	public String fetchAccessRightForGroup(int projectId, int groupId);
	ProjectAccess[] fetchProjectAccessForProject(int id);
	public ProjectAccess[] fetchProjectAccessForGroups(Group[] groups);
	public ProjectAccess[] fetchProjectAccessForGroups(Group[] groups, boolean write);
	//public ProjectAccess fetchProjectAccess(int projectId, int groupId);
	
	public void addMicroarraystudyToProject(int microarraystudyId, int projectId);
	public void removeMicroarraystudyFromProject(int microarraystudyId, int projectId);
	public Project[] fetchProjectsForMicroarraystudy(int mstudyId);
	
	public Project[] fetchAllProjects() throws Exception;
	public Project fetchProjectById(int id);
	//TODO test
	public Project[] fetchProjectsForProjectAccess(ProjectAccess[] projectAccess);
	public Project[] fetchProjectsForGroup(String accessRights);
	
	public int storeProject(Project project);
	public int storeProject(String name, String description);
	public void deleteProject(Project project);
	public void deleteProject(int projectId);
	
	final static String TYPE = "ProjectAdaptor";

	
	
}
