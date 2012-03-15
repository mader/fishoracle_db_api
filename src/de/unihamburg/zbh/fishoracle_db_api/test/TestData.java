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

import de.unihamburg.zbh.fishoracle_db_api.data.Chip;
import de.unihamburg.zbh.fishoracle_db_api.data.Group;
import de.unihamburg.zbh.fishoracle_db_api.data.Organ;
import de.unihamburg.zbh.fishoracle_db_api.data.Project;
import de.unihamburg.zbh.fishoracle_db_api.data.ProjectAccess;
import de.unihamburg.zbh.fishoracle_db_api.data.User;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.ChipAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriver;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriverImpl;
import de.unihamburg.zbh.fishoracle_db_api.driver.GroupAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.OrganAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.ProjectAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.UserAdaptor;

/**
 * @author Malte Mader
 *
 */
public class TestData {

	private FODriver driver;
	private UserAdaptor ua;
	private GroupAdaptor ga;
	private ProjectAdaptor pa;
	private ChipAdaptor ca;
	private OrganAdaptor oa;
	
	public TestData() {
		driver = new FODriverImpl("localhost", "emptyoracle", "fouser", "fish4me", "3306");
		ua = (UserAdaptor) driver.getAdaptor("UserAdaptor");
		ga = (GroupAdaptor) driver.getAdaptor("GroupAdaptor");
		pa = (ProjectAdaptor) driver.getAdaptor("ProjectAdaptor");
		ca = (ChipAdaptor) driver.getAdaptor("ChipAdaptor");
		oa = (OrganAdaptor) driver.getAdaptor("OrganAdaptor");
	}

	public User[] createAndStoreUserData() throws Exception{
		
		User[] users = createUserData();
		
		for(int i = 0; i < users.length; i++){
			ua.storeUser(users[i]);
		}
		
		return users;
	}
	
	public void addUsersToGroups(){
		ga.addUserToGroup(1, 1);
		ga.addUserToGroup(1, 2);
		ga.addUserToGroup(2, 3);
		ga.addUserToGroup(2, 4);
		ga.addUserToGroup(3, 5);
		ga.addUserToGroup(3, 6);
	}
	
	public User[] createUserData(){
				
		User user1, user2, user3, user4, user5, user6;
		
		user1 = new User("Bugs", "Bunny", "bugs", "bugs@loony.tunes", "123secret", false, false);
		user2 = new User("Daffy", "Duck", "daffy", "daffy@loony.tunes", "123secret", false, false);
		user3 = new User("Porky", "Pig", "porky", "porky@loony.tunes", "123secret", true, false);
		user4 = new User("Elmer", "Fudd", "elmer", "elmer@loony.tunes", "123secret", false, true);
		user5 = new User("Yosemite", "Sam", "yosemite", "yosemite@loony.tunes", "123secret", true, true);
		user6 = new User("Sylvester", "Cat", "sylvester", "sylvester@loony.tunes", "123secret", true, true);
		
		User[] users = new User[]{user1, user2, user3, user4, user5, user6};
		
		return users;
	}
	
	public void createAndStoreGroupData(){
		
		Group[] groups = createGroupData();
		
		for(int i = 0; i < groups.length; i++){
			ga.storeGroup(groups[i]);
		}
		
	}
	
	public Group[] createGroupData(){
		
		Group group1, group2, group3;
		
		group1 = new Group(1, "Staff", true);
		group2 = new Group(2, "Students",  true);
		group3 = new Group(3, "Extern", false);
		
		Group[] groups = new Group[]{group1, group2, group3};
		
		return groups;
		
	}
	
	public void createAndStoreProjectAccessData(){
		
		ProjectAccess[] pas = createProjectAccessData();
		
		for(int i = 0; i < pas.length; i++){
			pa.addGroupAccessToProject(pas[i].getGroupId(), pas[i].getProjectId(), pas[i].getAccess());
		}
		
	}
	
	public ProjectAccess[] createProjectAccessData(){
		
		ProjectAccess pa1, pa2, pa3;
		
		pa1 = new ProjectAccess(0, 1, "rw");
		pa1.setProjectId(1);
		pa2 = new ProjectAccess(0, 2, "r");
		pa2.setProjectId(1);
		pa3 = new ProjectAccess(0, 3, "r");
		pa3.setProjectId(2);
		
		ProjectAccess[] pas = new ProjectAccess[]{pa1, pa2, pa3};
		
		return pas;
		
	}
	
	public void createAndStoreProjectData(){
		
		Project[] projects = createProjectData();
		
		for(int i = 0; i < projects.length; i++){
			pa.storeProject(projects[i]);
		}
		
	}
	
	public Project[] createProjectData(){
		
		Project project1, project2;
		
		project1 = new Project(0, "Internal", "Internal data");
		project2 = new Project(0, "External", "External data");
		
		Project[] projects = new Project[]{project1, project2};
		
		return projects;
		
	}
	
	public void createAndStoreChipData(){
		
		Chip[] chips = createChipData();
		
		for(int i = 0; i < chips.length; i++){
			ca.storeChip(chips[i]);
		}
	}
	
	public Chip[] createChipData(){
		
		Chip chip1, chip2, chip3;
		
		chip1 = new Chip(1, "mapping250k_sty", "snp");
		chip2 = new Chip(2, "GenomeWideSNP_6", "snp");
		chip3 = new Chip(3, "hg-u133a_2", "expression");
		
		Chip[] chips = new Chip[]{chip1, chip2, chip3};
		
		return chips;
	}
	
	public Organ[] createOrganData(){
		
		Organ organ1, organ2, organ3, organ4, organ5, organ6;
		
		organ1 = new Organ(1, "Prostate", "Tumor tissue", "enabled");
		organ2 = new Organ(2, "Prostate", "Cell line", "enabled");
		organ3 = new Organ(3, "Kidney", "Tumor tissue", "enabled");
		organ4 = new Organ(4, "Kidney", "Cell line", "disabled");
		organ5 = new Organ(5, "Lung", "Tumor tissue", "disabled");
		organ6 = new Organ(6, "Lung", "Cell line", "disabled");
		
		Organ[] organs = new Organ[]{organ1, organ2, organ3,organ4, organ5, organ6};
		
		return organs;
	}
	
	public void emptyChipTable(){
		((BaseAdaptor) ca).truncateTable(((BaseAdaptor) ca).getPrimaryTableName());
	}
	
	public void emptyOrganTable(){
		((BaseAdaptor) oa).truncateTable(((BaseAdaptor) oa).getPrimaryTableName());
	}
	
	public void emptyUserTable(){
		((BaseAdaptor) ua).truncateTable(((BaseAdaptor) ua).getPrimaryTableName());
	}
	
	public void emptyUserInGroupTable(){
		((BaseAdaptor) ua).truncateTable("user_in_group");
	}
	
	public void emptyGroupTable(){
		((BaseAdaptor) ga).truncateTable(((BaseAdaptor) ga).getPrimaryTableName());
	}

	public void emptyProjectTable(){
		((BaseAdaptor) pa).truncateTable(((BaseAdaptor) pa).getPrimaryTableName());
	}
	
	public void emptyProjectAccessTable(){
		((BaseAdaptor) pa).truncateTable("group_project_access");
	}
	
	public FODriver getDriver() {
		return driver;
	}

	public void setDriver(FODriver driver) {
		this.driver = driver;
	}
	
	public UserAdaptor getUa() {
		return ua;
	}

	public void setUa(UserAdaptor ua) {
		this.ua = ua;
	}

	public GroupAdaptor getGa() {
		return ga;
	}
	
	public void setGa(GroupAdaptor ga) {
		this.ga = ga;
	}
	
	public ProjectAdaptor getPa() {
		return pa;
	}

	public void setPa(ProjectAdaptor pa) {
		this.pa = pa;
	}

	public ChipAdaptor getCa() {
		return ca;
	}

	public void setCa(ChipAdaptor ca) {
		this.ca = ca;
	}

	public OrganAdaptor getOa() {
		return oa;
	}

	public void setOa(OrganAdaptor oa) {
		this.oa = oa;
	}
}