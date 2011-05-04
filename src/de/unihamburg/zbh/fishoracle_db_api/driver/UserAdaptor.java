package de.unihamburg.zbh.fishoracle_db_api.driver;

import de.unihamburg.zbh.fishoracle_db_api.data.User;

public interface UserAdaptor {

	public User fetchUserByName(String Name);
		
	public User fetchUserByID(int id);
	
	public User fetchUserForLogin(User user);

	public int storeUser(User user);
	
	public void deleteUser(User user);
	
	public void setUserActive(User user);
	
	public void setUserAdmin(User user);
	
	public User[] fetchAllUsers();
	
	final static String TYPE = "UserAdaptor";	
}
