package de.unihamburg.zbh.fishoracle_db_api.test;

import de.unihamburg.zbh.fishoracle_db_api.data.User;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriver;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriverImpl;
import de.unihamburg.zbh.fishoracle_db_api.driver.UserAdaptor;
import junit.framework.TestCase;

public class UserAdaptorTest extends TestCase {

	private FODriver driver;
	private UserAdaptor ua;
	private User user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12;
	private User[] testusers = new User[12];
	
	protected void setUp() {

		driver = new FODriverImpl("localhost", "newfo", "fouser", "fish4me", "3306");
		ua = (UserAdaptor) driver.getAdaptor("UserAdaptor");
		
		user1 = new User("Bugs", "Bunny", "bugs", "bugs@loony.tunes", "123secret", false, false);
		user2 = new User("Daffy", "Duck", "daffy", "daffy@loony.tunes", "123secret", false, false);
		user3 = new User("Porky", "Pig", "porky", "porky@loony.tunes", "123secret", false, false);
		user4 = new User("Elmer", "Fudd", "elmer", "elmer@loony.tunes", "123secret", false, false);
		user5 = new User("Yosemite", "Sam", "yosemite", "yosemite@loony.tunes", "123secret", false, false);
		user6 = new User("Sylvester", "Cat", "sylvester", "sylvester@loony.tunes", "123secret", false, false);
		user7 = new User("Tweety", "Bird", "tweety", "tweety@loony.tunes", "123secret", true, true);
		user8 = new User("Speedy", "Gonzales", "speedy", "speedy@loony.tunes", "123secret", true, true);
		user9 = new User("Wile", "Coyote", "wile", "wile@loony.tunes", "123secret", true, true);
		user10 = new User("Road", "Runner", "road", "road@loony.tunes", "123secret", true, true);
		user11 = new User("Foghorn", "Leghorn", "foghorn", "foghorn@loony.tunes", "123secret", true, true);
		user12 = new User("Tasmanian", "Devil", "tasmanian", "tasmanian@loony.tunes", "123secret", true, true);
		
		testusers[0] = user1;
		testusers[1] = user2;
		testusers[2] = user3;
		testusers[3] = user4;
		testusers[4] = user5;
		testusers[5] = user6;
		testusers[6] = user7;
		testusers[7] = user8;
		testusers[8] = user9;
		testusers[9] = user10;
		testusers[10] = user11;
		testusers[11] = user12;
		
	}
	
	public void testStoreUser() {
		if(((BaseAdaptor) ua).fetchCount() == 0){
			ua.storeUser(user1);
			assertTrue(((BaseAdaptor) ua).fetchCount() == 1);
			ua.storeUser(user2);
			assertTrue(((BaseAdaptor) ua).fetchCount() == 2);
			ua.storeUser(user3);
			assertTrue(((BaseAdaptor) ua).fetchCount() == 3);
			ua.storeUser(user4);
			assertTrue(((BaseAdaptor) ua).fetchCount() == 4);
			ua.storeUser(user5);
			assertTrue(((BaseAdaptor) ua).fetchCount() == 5);
			ua.storeUser(user6);
			assertTrue(((BaseAdaptor) ua).fetchCount() == 6);
			ua.storeUser(user7);
			assertTrue(((BaseAdaptor) ua).fetchCount() == 7);
			ua.storeUser(user8);
			assertTrue(((BaseAdaptor) ua).fetchCount() == 8);
			ua.storeUser(user9);
			assertTrue(((BaseAdaptor) ua).fetchCount() == 9);
			ua.storeUser(user10);
			assertTrue(((BaseAdaptor) ua).fetchCount() == 10);
			ua.storeUser(user11);
			assertTrue(((BaseAdaptor) ua).fetchCount() == 11);
			ua.storeUser(user12);
			assertTrue(((BaseAdaptor) ua).fetchCount() == 12);
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
		assertTrue(u3.getIsActive() == false);
		ua.toggleUserActiveStatus(u3);
		u3 = ua.fetchUserByID(3);
		assertTrue(u3.getIsActive() == true);
		
		User u7 = ua.fetchUserByID(7);
		assertTrue(u7.getIsActive() == true);
		ua.toggleUserActiveStatus(u7);
		u7 = ua.fetchUserByID(7);
		assertTrue(u7.getIsActive() == false);
		
		User u8 = ua.fetchUserByID(8);
		assertTrue(u8.getIsActive() == true);
		ua.toggleUserActiveStatus(u8);
		u8 = ua.fetchUserByID(8);
		assertTrue(u8.getIsActive() == false);
		
		User u9 = ua.fetchUserByID(9);
		assertTrue(u9.getIsActive() == true);
		ua.toggleUserActiveStatus(u9);
		u9 = ua.fetchUserByID(9);
		assertTrue(u9.getIsActive() == false);
		
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
		
		User u7 = ua.fetchUserByID(7);
		assertTrue(u7.getIsAdmin() == true);
		ua.toggleUserAdminStatus(u7);
		u7 = ua.fetchUserByID(7);
		assertTrue(u7.getIsAdmin() == false);
		
		User u8 = ua.fetchUserByID(8);
		assertTrue(u8.getIsAdmin() == true);
		ua.toggleUserAdminStatus(u8);
		u8 = ua.fetchUserByID(8);
		assertTrue(u8.getIsAdmin() == false);
		
		User u9 = ua.fetchUserByID(9);
		assertTrue(u9.getIsAdmin() == true);
		ua.toggleUserAdminStatus(u9);
		u9 = ua.fetchUserByID(9);
		assertTrue(u9.getIsAdmin() == false);
		
	}
	
	public void testFetchAllUsers() {
		
		User[] users = ua.fetchAllUsers();
		
		for(int i = 0; i < users.length; i++) {
			assertTrue(users[i].getId() == (i+1));
			assertTrue(users[i].getLastName().equals(testusers[i].getLastName()));
			assertTrue(users[i].getFirstName().equals(testusers[i].getFirstName()));
			assertTrue(users[i].getUserName().equals(testusers[i].getUserName()));
			assertTrue(users[i].getEmail().endsWith(testusers[i].getEmail()));
			assertTrue(users[i].getPw().equals(""));
		}
		
		assertTrue(users[3].getIsActive() == false);
		assertTrue(users[3].getIsAdmin() == false);
		
		assertTrue(users[4].getIsActive() == false);
		assertTrue(users[4].getIsAdmin() == false);
		
		assertTrue(users[5].getIsActive() == false);
		assertTrue(users[5].getIsAdmin() == false);
		
		assertTrue(users[9].getIsActive() == true);
		assertTrue(users[9].getIsAdmin() == true);
		
		assertTrue(users[10].getIsActive() == true);
		assertTrue(users[10].getIsAdmin() == true);
		
		assertTrue(users[11].getIsActive() == true);
		assertTrue(users[11].getIsAdmin() == true);
		
	}
	
	public void fetchUserForLogin(){
		User u;
		
		for(int i = 0; i < testusers.length; i++) {
			u = ua.fetchUserForLogin(testusers[i].getUserName(), testusers[i].getPw());
			assertTrue(u.getId() == testusers[i].getId());
			assertTrue(u.getFirstName().equals(testusers[i].getFirstName()));
			assertTrue(u.getLastName().equals(testusers[i].getLastName()));
			assertTrue(u.getUserName().equals(testusers[i].getUserName()));
			assertTrue(u.getEmail().equals(testusers[i].getEmail()));
			assertTrue(u.getPw().equals(""));
		}
	}
	
	public void testFetchUserByID() {
		User u1 = ua.fetchUserByID(1);
		User u2 = ua.fetchUserByID(2);
		User u3 = ua.fetchUserByID(3);
		User u4 = ua.fetchUserByID(4);
		User u5 = ua.fetchUserByID(5);
		User u6 = ua.fetchUserByID(6);
		User u7 = ua.fetchUserByID(7);
		User u8 = ua.fetchUserByID(8);
		User u9 = ua.fetchUserByID(9);
		User u10 = ua.fetchUserByID(10);
		User u11 = ua.fetchUserByID(11);
		User u12 = ua.fetchUserByID(12);
		
		assertTrue(u1.getId() == 1);
		assertTrue(u1.getFirstName().equals(user1.getFirstName()));
		assertTrue(u1.getLastName().equals(user1.getLastName()));
		assertTrue(u1.getUserName().equals(user1.getUserName()));
		assertTrue(u1.getEmail().equals(user1.getEmail()));
		assertTrue(u1.getPw().equals(""));
		
		assertTrue(u2.getId() == 2);
		assertTrue(u2.getFirstName().equals(user2.getFirstName()));
		assertTrue(u2.getLastName().equals(user2.getLastName()));
		assertTrue(u2.getUserName().equals(user2.getUserName()));
		assertTrue(u2.getEmail().equals(user2.getEmail()));
		assertTrue(u2.getPw().equals(""));
		
		assertTrue(u3.getId() == 3);
		assertTrue(u3.getFirstName().equals(user3.getFirstName()));
		assertTrue(u3.getLastName().equals(user3.getLastName()));
		assertTrue(u3.getUserName().equals(user3.getUserName()));
		assertTrue(u3.getEmail().equals(user3.getEmail()));
		assertTrue(u3.getPw().equals(""));
		
		assertTrue(u4.getId() == 4);
		assertTrue(u5.getId() == 5);
		assertTrue(u6.getId() == 6);
		assertTrue(u7.getId() == 7);
		assertTrue(u8.getId() == 8);
		assertTrue(u9.getId() == 9);
		assertTrue(u10.getId() == 10);
		assertTrue(u11.getId() == 11);
		assertTrue(u12.getId() == 12);
	}
	
	public void testDeleteUser() {
		User u1 = ua.fetchUserByID(1);
		User u2 = ua.fetchUserByID(2);
		User u3 = ua.fetchUserByID(3);
		User u4 = ua.fetchUserByID(4);
		User u5 = ua.fetchUserByID(5);
		User u6 = ua.fetchUserByID(6);
		User u7 = ua.fetchUserByID(7);
		User u8 = ua.fetchUserByID(8);
		User u9 = ua.fetchUserByID(9);
		User u10 = ua.fetchUserByID(10);
		User u11 = ua.fetchUserByID(11);
		User u12 = ua.fetchUserByID(12);
		
		ua.deleteUser(u1);
		assertTrue(((BaseAdaptor) ua).fetchCount() == 11);
		ua.deleteUser(u2);
		assertTrue(((BaseAdaptor) ua).fetchCount() == 10);
		ua.deleteUser(u3);
		assertTrue(((BaseAdaptor) ua).fetchCount() == 9);
		ua.deleteUser(u4);
		assertTrue(((BaseAdaptor) ua).fetchCount() == 8);
		ua.deleteUser(u5);
		assertTrue(((BaseAdaptor) ua).fetchCount() == 7);
		ua.deleteUser(u6);
		assertTrue(((BaseAdaptor) ua).fetchCount() == 6);
		ua.deleteUser(u7);
		assertTrue(((BaseAdaptor) ua).fetchCount() == 5);
		ua.deleteUser(u8);
		assertTrue(((BaseAdaptor) ua).fetchCount() == 4);
		ua.deleteUser(u9);
		assertTrue(((BaseAdaptor) ua).fetchCount() == 3);
		ua.deleteUser(u10);
		assertTrue(((BaseAdaptor) ua).fetchCount() == 2);
		ua.deleteUser(u11);
		assertTrue(((BaseAdaptor) ua).fetchCount() == 1);
		ua.deleteUser(u12);
		assertTrue(((BaseAdaptor) ua).fetchCount() == 0);
	}
	
	protected void tearDown() {
		if(((BaseAdaptor) ua).fetchCount() == 0){
			((BaseAdaptor) ua).truncateTable(((BaseAdaptor) ua).getPrimaryTableName());
		}
	}
}
