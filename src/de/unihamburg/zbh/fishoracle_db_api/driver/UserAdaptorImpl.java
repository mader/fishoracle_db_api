package de.unihamburg.zbh.fishoracle_db_api.driver;

import java.sql.ResultSet;

import de.unihamburg.zbh.fishoracle_db_api.data.util.User;

public class UserAdaptorImpl extends BaseAdaptor implements UserAdaptor{
	
	protected UserAdaptorImpl(FODriverImpl driver) {
		super(driver);
	}

	@Override
	protected String[] columns() {
		return new String[]{"user_id",
							"first_name",
							"last_name",
							"username",
							"email",
							"isactive",
							"isadmin"};
	}

	@Override
	public Object createObject(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] tables() {
		return new String[]{"user"};
	}

	@Override
	public void deleteUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User[] fetchAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User fetchUserByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User fetchUserByName(String Name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User fetchUserForLogin(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUserActive(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUserAdmin(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int storeUser(User user) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
