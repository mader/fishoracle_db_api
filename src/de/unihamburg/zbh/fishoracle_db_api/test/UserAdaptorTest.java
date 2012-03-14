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

import de.unihamburg.zbh.fishoracle_db_api.data.User;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.GroupAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.UserAdaptor;
import junit.framework.TestCase;

public class UserAdaptorTest extends TestCase {

	private TestData td;
	private UserAdaptor ua;
	private User[] testusers;
	private GroupAdaptor ga;

	protected void setUp() {
		
		td = new TestData();
		
		ua = td.getUa();
		
		testusers = td.createUserData();
		
		ga = td.getGa();
	}
	
	public void testStoreUser() {
		if(((BaseAdaptor) ua).fetchCount() == 0){
			try {
				ua.storeUser(testusers[0]);
				assertTrue(((BaseAdaptor) ua).fetchCount() == 1);
				ua.storeUser(testusers[1]);
				assertTrue(((BaseAdaptor) ua).fetchCount() == 2);
				ua.storeUser(testusers[2]);
				assertTrue(((BaseAdaptor) ua).fetchCount() == 3);
				ua.storeUser(testusers[3]);
				assertTrue(((BaseAdaptor) ua).fetchCount() == 4);
				ua.storeUser(testusers[4]);
				assertTrue(((BaseAdaptor) ua).fetchCount() == 5);
				ua.storeUser(testusers[5]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ga.addUserToGroup(1, 1);
			ga.addUserToGroup(1, 2);
			ga.addUserToGroup(2, 3);
			ga.addUserToGroup(2, 4);
			ga.addUserToGroup(3, 5);
			ga.addUserToGroup(3, 6);
		}
	}
	
	public void testFetchAllUsers() {
		
		User[] users = ua.fetchAllUsers();
		
		for(int i = 0; i < users.length; i++) {
			assertTrue(users[i].getId() == (i+1));
			assertTrue(users[i].getFirstName().equals(testusers[i].getFirstName()));
			assertTrue(users[i].getLastName().equals(testusers[i].getLastName()));
			assertTrue(users[i].getUserName().equals(testusers[i].getUserName()));
			assertTrue(users[i].getEmail().equals(testusers[i].getEmail()));
			assertTrue(users[i].getPw().equals(""));
			assertTrue(users[i].getIsActive() == testusers[i].getIsActive());
			assertTrue(users[i].getIsAdmin() == testusers[i].getIsAdmin());	
		}
	}
	
	public void testFetchUsersForGroup(){
		User[] u1 = ua.fetchUsersForGroup(1);
		
		for(int i=0; i < u1.length; i++){
			assertTrue(u1[i].getId() == (i+1));
			assertTrue(u1[i].getFirstName().equals(testusers[i].getFirstName()));
			assertTrue(u1[i].getLastName().equals(testusers[i].getLastName()));
			assertTrue(u1[i].getUserName().equals(testusers[i].getUserName()));
			assertTrue(u1[i].getEmail().equals(testusers[i].getEmail()));
			assertTrue(u1[i].getPw().equals(""));
			assertTrue(u1[i].getIsActive() == testusers[i].getIsActive());
			assertTrue(u1[i].getIsAdmin() == testusers[i].getIsAdmin());
		}
		
		User[] u2 = ua.fetchUsersForGroup(2);
		
		for(int i=0; i < u2.length; i++){
			assertTrue(u2[i].getId() == (i+2+1));
			assertTrue(u2[i].getFirstName().equals(testusers[i + 2].getFirstName()));
			assertTrue(u2[i].getLastName().equals(testusers[i + 2].getLastName()));
			assertTrue(u2[i].getUserName().equals(testusers[i + 2].getUserName()));
			assertTrue(u2[i].getEmail().equals(testusers[i + 2].getEmail()));
			assertTrue(u2[i].getPw().equals(""));
			assertTrue(u2[i].getIsActive() == testusers[i + 2].getIsActive());
			assertTrue(u2[i].getIsAdmin() == testusers[i + 2].getIsAdmin());
		}
		
		User[] u3 = ua.fetchUsersForGroup(3);
		
		for(int i=0; i < u3.length; i++){
			assertTrue(u3[i].getId() == (i+4+1));
			assertTrue(u3[i].getFirstName().equals(testusers[i + 4].getFirstName()));
			assertTrue(u3[i].getLastName().equals(testusers[i + 4].getLastName()));
			assertTrue(u3[i].getUserName().equals(testusers[i + 4].getUserName()));
			assertTrue(u3[i].getEmail().equals(testusers[i + 4].getEmail()));
			assertTrue(u3[i].getPw().equals(""));
			assertTrue(u3[i].getIsActive() == testusers[i + 4].getIsActive());
			assertTrue(u3[i].getIsAdmin() == testusers[i + 4].getIsAdmin());
		}
	}
	
	public void testFetchAllUsersNotInGroup(){
		User[] u1 = ua.fetchAllUsersNotInGroup(1);
		
		for(int i=0; i < u1.length; i++){
			assertTrue(u1[i].getId() == (i+2+1));
			assertTrue(u1[i].getFirstName().equals(testusers[i + 2].getFirstName()));
			assertTrue(u1[i].getLastName().equals(testusers[i + 2].getLastName()));
			assertTrue(u1[i].getUserName().equals(testusers[i + 2].getUserName()));
			assertTrue(u1[i].getEmail().equals(testusers[i + 2].getEmail()));
			assertTrue(u1[i].getPw().equals(""));
			assertTrue(u1[i].getIsActive() == testusers[i + 2].getIsActive());
			assertTrue(u1[i].getIsAdmin() == testusers[i + 2].getIsAdmin());
		}
		
		User[] u2 = ua.fetchAllUsersNotInGroup(2);
		
		for(int i=0; i < u2.length - 4; i++){
			assertTrue(u2[i].getId() == (i+1));
			assertTrue(u2[i].getFirstName().equals(testusers[i].getFirstName()));
			assertTrue(u2[i].getLastName().equals(testusers[i].getLastName()));
			assertTrue(u2[i].getUserName().equals(testusers[i].getUserName()));
			assertTrue(u2[i].getEmail().equals(testusers[i].getEmail()));
			assertTrue(u2[i].getPw().equals(""));
			assertTrue(u2[i].getIsActive() == testusers[i].getIsActive());
			assertTrue(u2[i].getIsAdmin() == testusers[i].getIsAdmin());
		}
		
		for(int i=4; i < u2.length; i++){
			assertTrue(u2[i].getId() == (i+1));
			assertTrue(u2[i].getFirstName().equals(testusers[i].getFirstName()));
			assertTrue(u2[i].getLastName().equals(testusers[i].getLastName()));
			assertTrue(u2[i].getUserName().equals(testusers[i].getUserName()));
			assertTrue(u2[i].getEmail().equals(testusers[i].getEmail()));
			assertTrue(u2[i].getPw().equals(""));
			assertTrue(u2[i].getIsActive() == testusers[i].getIsActive());
			assertTrue(u2[i].getIsAdmin() == testusers[i].getIsAdmin());
		}
		
		User[] u3 = ua.fetchAllUsersNotInGroup(3);
		
		for(int i=0; i < u1.length - 2; i++){
			assertTrue(u3[i].getId() == (i+1));
			assertTrue(u3[i].getFirstName().equals(testusers[i].getFirstName()));
			assertTrue(u3[i].getLastName().equals(testusers[i].getLastName()));
			assertTrue(u3[i].getUserName().equals(testusers[i].getUserName()));
			assertTrue(u3[i].getEmail().equals(testusers[i].getEmail()));
			assertTrue(u3[i].getPw().equals(""));
			assertTrue(u3[i].getIsActive() == testusers[i].getIsActive());
			assertTrue(u3[i].getIsAdmin() == testusers[i].getIsAdmin());
		}
	}
	
	public void fetchUserForLogin(){
		User u;
		
		for(int i = 0; i < testusers.length; i++) {
			
			try {
				u = ua.fetchUserForLogin(testusers[i].getUserName(), testusers[i].getPw());
				
				assertTrue(u.getId() == testusers[i].getId());
				assertTrue(u.getFirstName().equals(testusers[i].getFirstName()));
				assertTrue(u.getLastName().equals(testusers[i].getLastName()));
				assertTrue(u.getUserName().equals(testusers[i].getUserName()));
				assertTrue(u.getEmail().equals(testusers[i].getEmail()));
				assertTrue(u.getPw().equals(""));
				assertTrue(u.getIsActive() == testusers[i].getIsActive());
				assertTrue(u.getIsAdmin() == testusers[i].getIsAdmin());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void testFetchUserByID() {
		User u1 = ua.fetchUserByID(1);
		User u2 = ua.fetchUserByID(2);
		User u3 = ua.fetchUserByID(3);
		User u4 = ua.fetchUserByID(4);
		User u5 = ua.fetchUserByID(5);
		User u6 = ua.fetchUserByID(6);
		
		User[] users = new User[]{u1, u2, u3, u4, u5, u6};
		
		for(int i = 0; i < users.length; i++) {
			assertTrue(users[i].getId() == (i+1));
			assertTrue(users[i].getFirstName().equals(testusers[i].getFirstName()));
			assertTrue(users[i].getLastName().equals(testusers[i].getLastName()));
			assertTrue(users[i].getUserName().equals(testusers[i].getUserName()));
			assertTrue(users[i].getEmail().equals(testusers[i].getEmail()));
			assertTrue(users[i].getPw().equals(""));
			assertTrue(users[i].getIsActive() == testusers[i].getIsActive());
			assertTrue(users[i].getIsAdmin() == testusers[i].getIsAdmin());	
		}
	}
	
	public void testToggleUserActiveStatus(){
		
		User u1 = ua.fetchUserByID(1);
		assertTrue(u1.getIsActive() == false);
		ua.toggleUserActiveStatus(u1);
		u1 = ua.fetchUserByID(1);
		assertTrue(u1.getIsActive() == true);
		
		User u2 = ua.fetchUserByID(2);
		assertTrue(u2.getIsActive() == false);
		ua.toggleUserActiveStatus(u2);
		u2 = ua.fetchUserByID(2);
		assertTrue(u2.getIsActive() == true);
		
		User u3 = ua.fetchUserByID(3);
		assertTrue(u3.getIsActive() == true);
		ua.toggleUserActiveStatus(u3);
		u3 = ua.fetchUserByID(3);
		assertTrue(u3.getIsActive() == false);
		
		User u4 = ua.fetchUserByID(4);
		assertTrue(u4.getIsActive() == false);
		ua.toggleUserActiveStatus(u4);
		u4 = ua.fetchUserByID(4);
		assertTrue(u4.getIsActive() == true);
		
		User u5 = ua.fetchUserByID(5);
		assertTrue(u5.getIsActive() == true);
		ua.toggleUserActiveStatus(u5);
		u5 = ua.fetchUserByID(5);
		assertTrue(u5.getIsActive() == false);
		
		User u6 = ua.fetchUserByID(6);
		assertTrue(u6.getIsActive() == true);
		ua.toggleUserActiveStatus(u6);
		u6 = ua.fetchUserByID(6);
		assertTrue(u6.getIsActive() == false);
		
	}
	
	public void testToggleUserAdminStatus(){
		
		User u1 = ua.fetchUserByID(1);
		assertTrue(u1.getIsAdmin() == false);
		ua.toggleUserAdminStatus(u1);
		u1 = ua.fetchUserByID(1);
		assertTrue(u1.getIsAdmin() == true);
		
		User u2 = ua.fetchUserByID(2);
		assertTrue(u2.getIsAdmin() == false);
		ua.toggleUserAdminStatus(u2);
		u2 = ua.fetchUserByID(2);
		assertTrue(u2.getIsAdmin() == true);
		
		User u3 = ua.fetchUserByID(3);
		assertTrue(u3.getIsAdmin() == false);
		ua.toggleUserAdminStatus(u3);
		u3 = ua.fetchUserByID(3);
		assertTrue(u3.getIsAdmin() == true);
		
		User u4 = ua.fetchUserByID(4);
		assertTrue(u4.getIsAdmin() == true);
		ua.toggleUserAdminStatus(u4);
		u4 = ua.fetchUserByID(4);
		assertTrue(u4.getIsAdmin() == false);
		
		User u5 = ua.fetchUserByID(5);
		assertTrue(u5.getIsAdmin() == true);
		ua.toggleUserAdminStatus(u5);
		u5 = ua.fetchUserByID(5);
		assertTrue(u5.getIsAdmin() == false);
		
		User u6 = ua.fetchUserByID(6);
		assertTrue(u6.getIsAdmin() == true);
		ua.toggleUserAdminStatus(u6);
		u6 = ua.fetchUserByID(6);
		assertTrue(u6.getIsAdmin() == false);
		
	}
	
	public void testUpdateUserFirstName() {
		
		User u1 = ua.fetchUserByID(1);
		assertTrue(u1.getFirstName().equals(testusers[0].getFirstName()));
		ua.updateUserFistName(u1.getId(), "Thunder");
		u1 = ua.fetchUserByID(1);
		assertTrue(u1.getFirstName().equals("Thunder"));
		
		User u2 = ua.fetchUserByID(2);
		assertTrue(u2.getFirstName().equals(testusers[1].getFirstName()));
		ua.updateUserFistName(u2.getId(), "Tiny");
		u2 = ua.fetchUserByID(2);
		assertTrue(u2.getFirstName().equals("Tiny"));
		
		User u3 = ua.fetchUserByID(3);
		assertTrue(u3.getFirstName().equals(testusers[2].getFirstName()));
		ua.updateUserFistName(u3.getId(), "Big");
		u3 = ua.fetchUserByID(3);
		assertTrue(u3.getFirstName().equals("Big"));
		
	}
	
	public void testUpdateUserLastName() {
		
		User u1 = ua.fetchUserByID(1);
		assertTrue(u1.getLastName().equals(testusers[0].getLastName()));
		ua.updateUserLastName(u1.getId(), "Rabbit");
		u1 = ua.fetchUserByID(1);
		assertTrue(u1.getLastName().equals("Rabbit"));
		
		User u2 = ua.fetchUserByID(2);
		assertTrue(u2.getLastName().equals(testusers[1].getLastName()));
		ua.updateUserLastName(u2.getId(), "Bird");
		u2 = ua.fetchUserByID(2);
		assertTrue(u2.getLastName().equals("Bird"));
		
		User u3 = ua.fetchUserByID(3);
		assertTrue(u3.getLastName().equals(testusers[2].getLastName()));
		ua.updateUserLastName(u3.getId(), "Whatever");
		u3 = ua.fetchUserByID(3);
		assertTrue(u3.getLastName().equals("Whatever"));
	}
	
	public void testUpdateUserEmailNameUser() {
		
		User u1 = ua.fetchUserByID(1);
		assertTrue(u1.getEmail().equals(testusers[0].getEmail()));
		ua.updateUserEmail(u1.getId(), "bunny@loony.tunes");
		u1 = ua.fetchUserByID(1);
		assertTrue(u1.getEmail().equals("bunny@loony.tunes"));
		
		User u2 = ua.fetchUserByID(2);
		assertTrue(u2.getEmail().equals(testusers[1].getEmail()));
		ua.updateUserEmail(u2.getId(), "duck@loony.tunes");
		u2 = ua.fetchUserByID(2);
		assertTrue(u2.getEmail().equals("duck@loony.tunes"));
		
		User u3 = ua.fetchUserByID(3);
		assertTrue(u3.getEmail().equals(testusers[2].getEmail()));
		ua.updateUserEmail(u3.getId(), "pig@loony.tunes");
		u3 = ua.fetchUserByID(3);
		assertTrue(u3.getEmail().equals("pig@loony.tunes"));
		
	}
	
	public void testUpdateUserPassword() {

		try {
			
			User u1 = ua.fetchUserForLogin(testusers[0].getUserName(), "123secret");
			assertTrue(u1.getId() == 1);
			ua.updateUserPassword(u1.getId(), "secret123");
			u1 = ua.fetchUserForLogin(u1.getUserName(), "secret123");
			assertTrue(u1.getId() == 1);
			
			User u2 = ua.fetchUserForLogin(testusers[1].getUserName(), "123secret");
			assertTrue(u2.getId() == 2);
			ua.updateUserPassword(u2.getId(), "somethingnew");
			u2 = ua.fetchUserForLogin(u2.getUserName(), "somethingnew");
			assertTrue(u2.getId() == 2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testDeleteUser() {
		User u1 = ua.fetchUserByID(1);
		User u2 = ua.fetchUserByID(2);
		User u3 = ua.fetchUserByID(3);
		User u4 = ua.fetchUserByID(4);
		User u5 = ua.fetchUserByID(5);
		User u6 = ua.fetchUserByID(6);
		
		ua.deleteUser(u1);
		assertTrue(((BaseAdaptor) ua).fetchCount() == 5);
		ua.deleteUser(u2);
		assertTrue(((BaseAdaptor) ua).fetchCount() == 4);
		ua.deleteUser(u3);
		assertTrue(((BaseAdaptor) ua).fetchCount() == 3);
		ua.deleteUser(u4);
		assertTrue(((BaseAdaptor) ua).fetchCount() == 2);
		ua.deleteUser(u5);
		assertTrue(((BaseAdaptor) ua).fetchCount() == 1);
		ua.deleteUser(u6);
		assertTrue(((BaseAdaptor) ua).fetchCount() == 0);
	}
	
	protected void tearDown() {
		if(((BaseAdaptor) ua).fetchCount() == 0){
			td.emptyUserInGroupTable();
			td.emptyUserTable();
		}
	}
}