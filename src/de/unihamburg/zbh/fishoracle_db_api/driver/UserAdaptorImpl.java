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
		return null;
	}

	@Override
	protected String[] tables() {
		return new String[]{"user"};
	}

	@Override
	public User[] fetchAllUsers() {
		
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		User[] users = null;
		
		try{
			
			conn = getConnection();
			int userCount = (int) fetchCount();
			
			query.append("SELECT ").append("user_id, first_name, last_name, username, email, isActive, isadmin")
			.append(" FROM ").append(getPrimaryTableName()).append(" ORDER BY user_id ASC");
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			int id = 0;
			String firstName = null;
			String lastName = null;
			String dbUserName = null;
			String email = null;
			Boolean isActive = null;
			Boolean isAdmin = null;
			
			users = new User[userCount];
			int i = 0;
			
			while(userRs.next()){
				
				id = userRs.getInt(1);
				firstName = userRs.getString(2);
				lastName = userRs.getString(3);
				dbUserName = userRs.getString(4);
				email = userRs.getString(5);
				isActive = userRs.getBoolean(6);
				isAdmin = userRs.getBoolean(7);
				
				User user = new User(id, firstName, lastName, dbUserName, email, isActive, isAdmin);
				
				users[i] = user;
				
				i++;
			}
			
		} catch (Exception e){
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return users;
	}

	@Override
	public User fetchUserByID(int id) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		User user = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("user_id, first_name, last_name, username, email, isactive, isadmin")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" WHERE ").append("user_id = " + id);
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			int userId = 0;
			String fistName = null;
			String lastName = null;
			String dbUserName = null;
			String email = null;
			Boolean isActive = null;
			Boolean isAdmin = null;
			
			while(userRs.next()){
				
				userId = userRs.getInt(1);
				fistName = userRs.getString(2);
				lastName = userRs.getString(3);
				dbUserName = userRs.getString(4);
				email = userRs.getString(5);
				isActive = userRs.getBoolean(6);
				isAdmin = userRs.getBoolean(7);
			}
			
			user = new User(id, fistName, lastName, dbUserName, email, isActive, isAdmin);
			
			if(userId == 0){
				
				throw new AdaptorException("A user with ID: " + id + " does not exist.");
				
			}
			
			user = new User(id, fistName, lastName, dbUserName, email, isActive, isAdmin);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return user;
	}

	@Override
	public User fetchUserForLogin(String userName, String pw) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		User user = null;
		
		try{
			
			conn = getConnection();
			
			query.append("SELECT ").append("user_id, first_name, last_name, username, email, isactive, isadmin")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" WHERE ").append("username = '" + userName + "' AND password = '" + SimpleSHA.SHA1(pw) + "'");
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			int id = 0;
			String fistName = null;
			String lastName = null;
			String dbUserName = null;
			String email = null;
			Boolean isActive = null;
			Boolean isAdmin = null;
			
			while(userRs.next()){
				
				id = userRs.getInt(1);
				fistName = userRs.getString(2);
				lastName = userRs.getString(3);
				dbUserName = userRs.getString(4);
				email = userRs.getString(5);
				isActive = userRs.getBoolean(6);
				isAdmin = userRs.getBoolean(7);
			}
			
			if(id == 0){
				
				throw new AdaptorException("User name or password incorrect!");
				
			}
			if(isActive == false){
				
				throw new AdaptorException("Your account has not been activated. If you registered recently" +
						                    " this means that your acount has not been verified yet. Just try to log in later." +
						                    " If your account has been deactivated or your registration was more than 3 days ago" +
						                    " then contact the webmaster.");
				
			}
			
			user = new User(id, fistName, lastName, dbUserName, email, isActive, isAdmin);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return user;
	}

	@Override
	public int toggleUserActiveStatus(User user) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		int activate = 0;
		
		if(user.getIsActive() == true){
				activate = 0;
			} else {
				activate = 1;
			}
		
		try{
			
			conn = getConnection();	
			
			query.append("UPDATE ").append(getPrimaryTableName()).append(" SET isactive = '").append(activate)
			.append("' WHERE user_id = ").append(user.getId());

			executeUpdate(conn, query.toString());
			
		} catch (Exception e){
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return activate;
	}

	@Override
	public int toggleUserAdminStatus(User user) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		int admin = 0;
		
		if(user.getIsAdmin() == true){
				admin = 0;
			} else {
				admin = 1;
			}
		
		try{
			
			conn = getConnection();	
			
			query.append("UPDATE ").append(getPrimaryTableName()).append(" SET isadmin = '").append(admin)
			.append("' WHERE user_id = ").append(user.getId());

			executeUpdate(conn, query.toString());
			
		} catch (Exception e){
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return admin;
	}

	@Override
	public int storeUser(User user) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		int nor = 0;
		
		try{
			
			conn = getConnection();

			long userCount;
			
			userCount = fetchCount("username = '" + user.getUserName() + "'");
			
			query.append("INSERT INTO ").append(getPrimaryTableName())
			.append(" (first_name, last_name, username, email, password, isactive, isadmin)")
			.append(" VALUES ").append("('" + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.getUserName() +
					"', '" + user.getEmail() + "', '" + SimpleSHA.SHA1(user.getPw()) + "', '" + user.getIsActiveAsInt() + 
					"', '"+ user.getIsAdminAsInt() + "')");
			
			if(userCount == 0){
			
				nor = executeUpdate(conn, query.toString());
			} else {
				 throw new AdaptorException("User name is already taken! Choose another one.");
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
	
	public void deleteUser(User user) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			query.append("DELETE FROM ")
			.append(getPrimaryTableName())
			.append(" WHERE ").append("user_id = " + user.getId());
			
			executeUpdate(conn, query.toString());
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
	}
}
