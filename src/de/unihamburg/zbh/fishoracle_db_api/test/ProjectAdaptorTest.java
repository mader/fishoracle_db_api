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

package de.unihamburg.zbh.fishoracle_db_api.test;

import de.unihamburg.zbh.fishoracle_db_api.data.Group;
import de.unihamburg.zbh.fishoracle_db_api.data.Project;
import de.unihamburg.zbh.fishoracle_db_api.data.ProjectAccess;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.ProjectAdaptor;
import junit.framework.TestCase;

/**
 * @author Malte Mader
 *
 */
public class ProjectAdaptorTest extends TestCase{
	
	private TestData td;
	private Group[] dbtestgroups;
	private ProjectAccess[] dbtestpacs;
	private ProjectAdaptor pa;
	private Project[] testprojects;
	
	protected void setUp() {
		
		td = new TestData();
		
		pa = td.getPa();
		
		dbtestgroups = td.createAndStoreGroupData();
		
		dbtestpacs = td.createProjectAccessData();
		
		testprojects = td.createProjectData();
	}
	
	public void testStoreProject(){
		pa.storeProject(testprojects[0]);
		assertTrue(((BaseAdaptor) pa).fetchCount() == 1);
		pa.storeProject(testprojects[1]);
		assertTrue(((BaseAdaptor) pa).fetchCount() == 2);
	}
	
	public void testFetchAllProjects(){
		Project[] projects;
		projects = pa.fetchAllProjects(false);
			
		for(int i = 0; i < projects.length; i++){
			assertTrue(projects[i].getId() == testprojects[i].getId());
			assertTrue(projects[i].getName().equals(testprojects[i].getName()));
			assertTrue(projects[i].getDescription().equals(testprojects[i].getDescription()));
		}
	}
	
	public void testFetchProjectById(){
		Project[] projects;
		Project project1, project2;
		project1 = pa.fetchProjectById(1, false);
		project2 = pa.fetchProjectById(2, false);
		projects = new Project[]{project1, project2};
		
		for(int i = 0; i < projects.length; i++){
			assertTrue(projects[i].getId() == testprojects[i].getId());
			assertTrue(projects[i].getName().equals(testprojects[i].getName()));
			assertTrue(projects[i].getDescription().equals(testprojects[i].getDescription()));
		}
	}
	
	public void testAddGroupAccessToProject(){
		pa.addGroupAccessToProject(dbtestpacs[0]);
		assertTrue(((BaseAdaptor) pa).fetchCount("group_project_access","") == 1);
		pa.addGroupAccessToProject(dbtestpacs[1]);
		assertTrue(((BaseAdaptor) pa).fetchCount("group_project_access","") == 2);
		pa.addGroupAccessToProject(dbtestpacs[2]);
		assertTrue(((BaseAdaptor) pa).fetchCount("group_project_access","") == 3);
	}
	
	public void testFetchAccessRightforGroup(){
		
		String r1 = pa.fetchAccessRightForGroup(1, 1);
		assertTrue(r1.equals("rw"));
		
		String r2 = pa.fetchAccessRightForGroup(1, 2);
		assertTrue(r2.equals("r"));
		
		String r3 = pa.fetchAccessRightForGroup(2, 3);
		assertTrue(r3.equals("r"));
	}
	
	public void testFetchProjectAccessForProject(){
		ProjectAccess[] pac1 = pa.fetchProjectAccessForProject(1, false);
		ProjectAccess[] pac2 = pa.fetchProjectAccessForProject(2, false);
		
		assertTrue(pac1.length == 2);
		assertTrue(pac1[0].getGroupId() == 1);
		assertTrue(pac1[0].getProjectId() == 1);
		
		assertTrue(pac1[1].getGroupId() == 2);
		assertTrue(pac1[1].getProjectId() == 1);
		
		assertTrue(pac2.length == 1);
		assertTrue(pac2[0].getGroupId() == 3);
		assertTrue(pac2[0].getProjectId() == 2);
	}
	
	public void testFetchProjectAccessForGroups(){
		
		ProjectAccess[] pacs = pa.fetchProjectAccessForGroups(dbtestgroups, true, false);
		
		for(int i = 0; i < pacs.length; i++){
			assertTrue(pacs[i].getId() == dbtestpacs[i].getId());
			assertTrue(pacs[i].getGroupId() == dbtestpacs[i].getGroupId());
			assertTrue(pacs[i].getProjectId() == dbtestpacs[i].getProjectId());
			assertTrue(pacs[i].getAccess().equals(dbtestpacs[i].getAccess()));
		}
	}
	
	public void testFetchProjectsForProjectAccess(){
		
		Project[] projects = pa.fetchProjectsForProjectAccess(dbtestpacs, false);
		
		for(int i = 0; i < projects.length; i++){
			assertTrue(projects[i].getId() == testprojects[i].getId());
			assertTrue(projects[i].getName().equals(testprojects[i].getName()));
			assertTrue(projects[i].getDescription().equals(testprojects[i].getDescription()));
		}
	}
	
	public void testModifyGroupAccessForProject(){
		
		pa.modifyGroupAccessForProject(dbtestpacs[0].getGroupId(), dbtestpacs[0].getProjectId(), "r");
		String right = pa.fetchAccessRightForGroup(1, 1);
		assertTrue(right.equals("r"));
	}
	
	public void testRemoveGroupAccessFromProject(){
		
		assertTrue(((BaseAdaptor) pa).fetchCount("group_project_access","") == 3);
		pa.removeGroupAccessFromProject(1,1);
		assertTrue(((BaseAdaptor) pa).fetchCount("group_project_access","") == 2);
		pa.removeGroupAccessFromProject(dbtestpacs[1].getId());
		assertTrue(((BaseAdaptor) pa).fetchCount("group_project_access","") == 1);
		pa.removeGroupAccessFromProject(dbtestpacs[2].getId());
		System.out.println(((BaseAdaptor) pa).fetchCount("group_project_access",""));
		assertTrue(((BaseAdaptor) pa).fetchCount("group_project_access","") == 0);
	}
	
	//TODO
	public void testAddMicroarraystudyToProject(){}
	
	public void testRemoveMicroarraystudyToProject(){}
	
	public void testDeleteProject(){
		Project p1 = pa.fetchProjectById(1);
		Project p2 = pa.fetchProjectById(2);
		
		pa.deleteProject(p1);
		assertTrue(((BaseAdaptor) pa).fetchCount() == 1);
		pa.deleteProject(p2);
		assertTrue(((BaseAdaptor) pa).fetchCount() == 0);
	}
	
	protected void tearDown() {
		td.emptyGroupTable();
		if(((BaseAdaptor) pa).fetchCount() == 0){
			td.emptyProjectTable();
			td.emptyProjectAccessTable();
		}
	}
}