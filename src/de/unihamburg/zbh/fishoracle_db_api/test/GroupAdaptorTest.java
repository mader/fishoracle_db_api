package de.unihamburg.zbh.fishoracle_db_api.test;

import de.unihamburg.zbh.fishoracle_db_api.data.Group;
import de.unihamburg.zbh.fishoracle_db_api.data.User;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriver;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriverImpl;
import de.unihamburg.zbh.fishoracle_db_api.driver.GroupAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.UserAdaptor;
import junit.framework.TestCase;

public class GroupAdaptorTest extends TestCase{
	private FODriver driver;
	private GroupAdaptor ga;
	private Group group1, group2, group3;
	private Group[] testgroups = new Group[3]; 
	private UserAdaptor ua;
	private User user1;
	
	protected void setUp() {
		
		driver = new FODriverImpl("localhost", "emptyoracle", "fouser", "fish4me", "3306");
		
		ua = (UserAdaptor) driver.getAdaptor("UserAdaptor");
		
		user1 = new User("Bugs", "Bunny", "bugs", "bugs@loony.tunes", "123secret", false, false);
		
		try {
			ua.storeUser(user1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ga = (GroupAdaptor) driver.getAdaptor("GroupAdaptor");
		
		group1 = new Group(1, "Staff", true);
		group2 = new Group(2, "Students",  true);
		group3 = new Group(3, "Extern", false);
		
		testgroups[0] = group1;
		testgroups[1] = group2;
		testgroups[2] = group3;
	}

	public void testStoreGroup() {
		ga.storeGroup(group1);
		assertTrue(((BaseAdaptor) ga).fetchCount() == 1);
		ga.storeGroup(group2);
		assertTrue(((BaseAdaptor) ga).fetchCount() == 2);
		ga.storeGroup(group3);
		assertTrue(((BaseAdaptor) ga).fetchCount() == 3);
	}
	
	public void testFetchGroupById() {
		Group g1 = ga.fetchGroupById(1);
		Group g2 = ga.fetchGroupById(2);
		Group g3 = ga.fetchGroupById(3);

		assertTrue(g1.getId() == 1);
		assertTrue(g1.getName().equals(group1.getName()));
		assertTrue(g1.isIsactive() == group1.isIsactive());
		
		assertTrue(g2.getId() == 2);
		assertTrue(g2.getName().equals(group2.getName()));
		assertTrue(g2.isIsactive() == group2.isIsactive());
		
		assertTrue(g3.getId() == 3);
		assertTrue(g3.getName().equals(group3.getName()));
		assertTrue(g3.isIsactive() == group3.isIsactive());
	}
	
	public void testFetchAllGroups() {
		Group[] groups;
		try {
			groups = ga.fetchAllGroups();
			
			for (int i=0; i < groups.length; i++) {
			assertTrue(groups[i].getId() == (i+1));
			assertTrue(groups[i].getName().equals(testgroups[i].getName()));
			assertTrue(groups[i].isIsactive() == testgroups[i].isIsactive());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testFetchGroupsForUser(){
		ga.addUserToGroup(1, 1);
		ga.addUserToGroup(1, 2);
		ga.addUserToGroup(1, 3);
		
		Group[] groups = ga.fetchGroupsForUser(1);
		
		for (int i=0; i < groups.length; i++) {
			assertTrue(groups[i].getId() == (i+1));
			assertTrue(groups[i].getName().equals(testgroups[i].getName()));
			assertTrue(groups[i].isIsactive() == testgroups[i].isIsactive());
		}
	}
	
	public void testRemoveUserFromGroup(){
		ga.removeUserFromGroup(1, 1);
		ga.removeUserFromGroup(1, 2);
		ga.removeUserFromGroup(1, 3);
	} 
	
	
	public void testDeleteGroup() {
		Group g1 = ga.fetchGroupById(1);
		Group g2 = ga.fetchGroupById(2);
		Group g3 = ga.fetchGroupById(3);
		
		ga.deleteGroup(g1);
		assertTrue(((BaseAdaptor) ga).fetchCount() == 2);
		ga.deleteGroup(g2);
		assertTrue(((BaseAdaptor) ga).fetchCount() == 1);
		ga.deleteGroup(g3);
		assertTrue(((BaseAdaptor) ga).fetchCount() == 0);
	}
	
	
	protected void tearDown() {
		((BaseAdaptor) ua).truncateTable("user");
		if(((BaseAdaptor) ga).fetchCount() == 0){
			((BaseAdaptor) ga).truncateTable(((BaseAdaptor) ga).getPrimaryTableName());
		}
	}
	
}
