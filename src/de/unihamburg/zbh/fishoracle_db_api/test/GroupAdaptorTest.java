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
import de.unihamburg.zbh.fishoracle_db_api.data.User;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.GroupAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.UserAdaptor;
import junit.framework.TestCase;

public class GroupAdaptorTest extends TestCase{

	private TestData td;
	private GroupAdaptor ga;
	private UserAdaptor ua;
	private Group[] testgroups;
	private User[] testusers;
	
	protected void setUp() {
		
		td = new TestData();
		
		try {
			testusers = td.createAndStoreUserData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		testgroups = td.createGroupData();
		
		td.addUsersToGroups();
		
		ga = td.getGa();
		ua = td.getUa();
		
		td.createAndStoreProjectData();
		td.createAndStoreProjectAccessData();
		
	}

	public void testStoreGroup() {
		ga.storeGroup(testgroups[0]);
		assertTrue(((BaseAdaptor) ga).fetchCount() == 1);
		ga.storeGroup(testgroups[1]);
		assertTrue(((BaseAdaptor) ga).fetchCount() == 2);
		ga.storeGroup(testgroups[2]);
		assertTrue(((BaseAdaptor) ga).fetchCount() == 3);
	}
	
	public void testFetchAllGroups() {
		Group[] groups;
		try {
			groups = ga.fetchAllGroups();
			
			for (int i=0; i < groups.length; i++) {
				assertTrue(groups[i].getId() == (i+1));
				assertTrue(groups[i].getName().equals(testgroups[i].getName()));
				assertTrue(groups[i].isIsactive() == testgroups[i].isIsactive());
				assertTrue(groups[i].getUsers().length > 0);
				
				if(i == 0){
					for (int j=0; j < groups[i].getUsers().length; j++) {
						assertTrue(groups[i].getUsers()[j].getId() == (j+1));
						assertTrue(groups[i].getUsers()[j].getFirstName().equals(testusers[j].getFirstName()));
						assertTrue(groups[i].getUsers()[j].getLastName().equals(testusers[j].getLastName()));
						assertTrue(groups[i].getUsers()[j].getUserName().equals(testusers[j].getUserName()));
						assertTrue(groups[i].getUsers()[j].getEmail().equals(testusers[j].getEmail()));
						assertTrue(groups[i].getUsers()[j].getPw().equals(""));
						assertTrue(groups[i].getUsers()[j].getIsActive() == testusers[j].getIsActive());
						assertTrue(groups[i].getUsers()[j].getIsAdmin() == testusers[j].getIsAdmin());
					}
				}
			}
			
			groups = ga.fetchAllGroups(false);
			
			for (int i=0; i < groups.length; i++) {
				assertTrue(groups[i].getId() == (i+1));
				assertTrue(groups[i].getName().equals(testgroups[i].getName()));
				assertTrue(groups[i].isIsactive() == testgroups[i].isIsactive());
				assertTrue(groups[i].getUsers().length == 0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testFetchGroupsForUser(){
		
		Group[] groups = ga.fetchGroupsForUser(1, false);
		
		for (int i=0; i < groups.length; i++) {
			assertTrue(groups[i].getId() == (i+1));
			assertTrue(groups[i].getName().equals(testgroups[i].getName()));
			assertTrue(groups[i].isIsactive() == testgroups[i].isIsactive());
		}
		
		groups = ga.fetchGroupsForUser(2, false);
		
		for (int i=0; i < groups.length; i++) {
			assertTrue(groups[i].getId() == (i+1));
			assertTrue(groups[i].getName().equals(testgroups[i].getName()));
			assertTrue(groups[i].isIsactive() == testgroups[i].isIsactive());
		}
		
		groups = ga.fetchGroupsForUser(3, false);
		
		for (int i=0; i < groups.length; i++) {
			assertTrue(groups[i].getId() == (i+1+1));
			assertTrue(groups[i].getName().equals(testgroups[i + 1].getName()));
			assertTrue(groups[i].isIsactive() == testgroups[i + 1].isIsactive());
		}
		
		groups = ga.fetchGroupsForUser(5, false);
		
		for (int i=0; i < groups.length; i++) {
			assertTrue(groups[i].getId() == (i+2+1));
			assertTrue(groups[i].getName().equals(testgroups[i + 2].getName()));
			assertTrue(groups[i].isIsactive() == testgroups[i + 2].isIsactive());
		}
		
	}
	
	public void testFetchGroupsForProject(){
		
		Group[] groups = ga.fetchGroupsForProject(1, false);
		
		assertTrue(groups.length == 2);
		assertTrue(groups[0].getId() == 1);
		assertTrue(groups[0].getName().equals(testgroups[0].getName()));
		
		assertTrue(groups[1].getId() == 2);
		assertTrue(groups[1].getName().equals(testgroups[1].getName()));
		
		groups = ga.fetchGroupsForProject(2, false);
		
		assertTrue(groups.length == 1);
		assertTrue(groups[0].getId() == 3);
		assertTrue(groups[0].getName().equals(testgroups[2].getName()));
		
	}
	
	public void testFetchGroupsNotInProject(){
		
		Group[] groups = ga.fetchGroupsNotInProject(1, false);
		
		assertTrue(groups.length == 1);
		assertTrue(groups[0].getId() == 3);
		assertTrue(groups[0].getName().equals(testgroups[2].getName()));
		
		groups = ga.fetchGroupsNotInProject(2, false);
		
		assertTrue(groups.length == 2);
		assertTrue(groups[0].getId() == 1);
		assertTrue(groups[0].getName().equals(testgroups[0].getName()));
		
		assertTrue(groups[1].getId() == 2);
		assertTrue(groups[1].getName().equals(testgroups[1].getName()));
		
	}
	
	public void testFetchGroupById() {
		Group g1 = ga.fetchGroupById(1, false);
		Group g2 = ga.fetchGroupById(2, false);
		Group g3 = ga.fetchGroupById(3, false);

		assertTrue(g1.getId() == 1);
		assertTrue(g1.getName().equals(testgroups[0].getName()));
		assertTrue(g1.isIsactive() == testgroups[0].isIsactive());
		
		assertTrue(g2.getId() == 2);
		assertTrue(g2.getName().equals(testgroups[1].getName()));
		assertTrue(g2.isIsactive() == testgroups[1].isIsactive());
		
		assertTrue(g3.getId() == 3);
		assertTrue(g3.getName().equals(testgroups[2].getName()));
		assertTrue(g3.isIsactive() == testgroups[2].isIsactive());
	}
	
	public void testAddRemoveUserFromGroup(){
		
		User u1 = new User("Buster", "Bunny", "buster", "buster@tiny.toons", "123secret", true, false);
		User u2 = new User("Babs", "Bunny", "babs", "babs@tiny.toons", "123secret", true, false);
		int uid1 = 0, uid2 = 0;
		User[] dbu1, dbu2;
		
		try {
			uid1 = ua.storeUser(u1);
			uid2 = ua.storeUser(u2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dbu1 = ua.fetchUsersForGroup(1);
		assertTrue(dbu1.length == 2);
		
		dbu2 = ua.fetchUsersForGroup(2);
		assertTrue(dbu2.length == 2);
		
		ga.addUserToGroup(1, uid1);
		ga.addUserToGroup(2, uid2);
		
		dbu1 = ua.fetchUsersForGroup(1);
		assertTrue(dbu1.length == 3);
		assertTrue(dbu1[2].getId() == uid1);
		
		dbu2 = ua.fetchUsersForGroup(2);
		assertTrue(dbu2.length == 3);
		assertTrue(dbu2[2].getId() == uid2);
		
		ga.removeUserFromGroup(1, uid1);
		ga.removeUserFromGroup(2, uid2);
		
		dbu1 = ua.fetchUsersForGroup(1);
		assertTrue(dbu1.length == 2);
		
		dbu2 = ua.fetchUsersForGroup(2);
		assertTrue(dbu2.length == 2);
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
		td.emptyUserTable();
		td.emptyUserInGroupTable();
		td.emptyProjectTable();
		td.emptyProjectAccessTable();
		if(((BaseAdaptor) ga).fetchCount() == 0){
			td.emptyGroupTable();
		}
	}
}