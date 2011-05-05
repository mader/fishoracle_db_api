package de.unihamburg.zbh.fishoracle_db_api.driver;

import de.unihamburg.zbh.fishoracle_db_api.data.User;

public interface UserAdaptor {
		
	public User fetchUserByID(int id);
	
	public User fetchUserForLogin(String userName, String pw);

	public int storeUser(User user);
	
	public void deleteUser(User user);
	
	public int toggleUserActiveStatus(User user);
	
	public int toggleUserAdminStatus(User user);
	
	public User[] fetchAllUsers();
	
	final static String TYPE = "UserAdaptor";	
}
