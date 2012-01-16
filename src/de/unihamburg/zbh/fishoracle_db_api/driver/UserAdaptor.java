package de.unihamburg.zbh.fishoracle_db_api.driver;

import de.unihamburg.zbh.fishoracle_db_api.data.User;

public interface UserAdaptor {
		
	public User fetchUserByID(int id);
	
	public User fetchUserForLogin(String userName, String pw) throws Exception;

	public User[] fetchUsersForGroup(int groupId);
	public User[] fetchAllUsersNotInGroup(int groupId);
	
	public int storeUser(User user) throws Exception;
	public int storeUser(String firstName, String lastName, String userName, String email, String pw, int isactive, int isadmin) throws Exception;
	
	public void updateUserFistName(int userId, String firstName);
	public void updateUserLastName(int userId, String lastName);
	public void updateUserEmail(int userId, String email);
	public void updateUserPassword(int userId, String pw);
	
	public void deleteUser(User user);
	
	public int toggleUserActiveStatus(User user);
	public int toggleUserActiveStatus(int id, boolean isactive);
	
	public int toggleUserAdminStatus(User user);
	public int toggleUserAdminStatus(int id, boolean isadmin);
	
	public User[] fetchAllUsers();
	
	final static String TYPE = "UserAdaptor";	
}
