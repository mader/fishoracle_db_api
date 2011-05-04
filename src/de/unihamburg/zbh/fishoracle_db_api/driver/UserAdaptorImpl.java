package de.unihamburg.zbh.fishoracle_db_api.driver;

import java.sql.Connection;
import java.sql.ResultSet;

import de.unihamburg.zbh.fishoracle_db_api.data.User;
import de.unihamburg.zbh.fishoracle_db_api.util.SimpleSHA;

public class UserAdaptorImpl extends BaseAdaptor implements UserAdaptor{
	
	protected UserAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}

	@Override
	protected String[] columns() {
		return new String[]{"user_id",
							"first_name",
							"last_name",
							"username",
							"email",
							"password",
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
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		int nor = 0;
		
		try{
			
			conn = getConnection();

			long userCount;
			
			userCount = fetchCount(columns()[3] + " = '" + user.getUserName() + "'");
			
			query.append("INSERT INTO ").append(getPrimaryTableName() + " ").append("(")
			.append(columns()[1]).append(",")
			.append(columns()[2]).append(",")
			.append(columns()[3]).append(",")
			.append(columns()[4]).append(",")
			.append(columns()[5]).append(",")
			.append(columns()[6]).append(",")
			.append(columns()[7]).append(")")
			.append(" VALUES ").append("('" + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.getUserName() +
					"', '" + user.getEmail() + "', '" + SimpleSHA.SHA1(user.getPw()) + "', '" + user.getIsActiveAsInt() + 
					"', '"+ user.getIsAdminAsInt() + "')");
			
			System.out.println(query.toString());
			
			if(userCount == 0){
			
				nor = executeUpdate(conn, query.toString());
			} else {
				 //throw new DBQueryException("User name is already taken! Choose another one.");
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		
		return nor;
	}
	
}
