package de.unihamburg.zbh.fishoracle_db_api.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

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
	public User fetchUserForLogin(String userName, String pw) throws Exception {
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
			throw new Exception(e.getMessage());
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return user;
	}

	@Override
	public User[] fetchUsersForGroup(int groupId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		ArrayList<User> userContainer = new ArrayList<User>();
		User[] users = null;
		
		try{
			
			conn = getConnection();
			
			query.append("SELECT ").append("user.user_id, user.first_name, user.last_name, user.username, user.email, user.isActive, user.isadmin")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" LEFT JOIN user_in_group ON user.user_id = user_in_group.user_id")
			.append(" WHERE user_in_group.group_id = " + groupId)
			.append(" ORDER BY user_id ASC");
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			int id = 0;
			String firstName = null;
			String lastName = null;
			String dbUserName = null;
			String email = null;
			Boolean isActive = null;
			Boolean isAdmin = null;
			
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
				
				userContainer.add(user);
				
				i++;
			}
			
			users = new User[userContainer.size()];
			userContainer.toArray(users);
			
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
	
	//TODO test this
	@Override
	public User[] fetchAllUsersNotInGroup(int groupId) {
		Connection conn = null;
		StringBuffer query1 = new StringBuffer();
		StringBuffer query2 = new StringBuffer();
		ArrayList<User> userContainer = new ArrayList<User>();
		User[] users = null;
		
		try{
			
			conn = getConnection();
			
			query1.append("SELECT ").append("user.user_id, user.first_name, user.last_name, user.username, user.email, user.isActive, user.isadmin")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" LEFT JOIN user_in_group ON user.user_id = user_in_group.user_id")
			.append(" WHERE user_in_group.group_id = " + groupId)
			.append(" ORDER BY user_id ASC");
			
			ResultSet userRs = executeQuery(conn, query1.toString());
			
			int id = 0;
			String firstName = null;
			String lastName = null;
			String dbUserName = null;
			String email = null;
			Boolean isActive = null;
			Boolean isAdmin = null;
			
			while(userRs.next()){
				
				id = userRs.getInt(1);
				firstName = userRs.getString(2);
				lastName = userRs.getString(3);
				dbUserName = userRs.getString(4);
				email = userRs.getString(5);
				isActive = userRs.getBoolean(6);
				isAdmin = userRs.getBoolean(7);
				
				User user = new User(id, firstName, lastName, dbUserName, email, isActive, isAdmin);
				
				userContainer.add(user);
			}
			
			query2.append("SELECT ").append("user.user_id, user.first_name, user.last_name, user.username, user.email, user.isActive, user.isadmin")
			.append(" FROM ").append(getPrimaryTableName());
			
			String whereClause = " ";
			
			boolean where = true;
			if(userContainer.size() > 0){
				for(int i=0; i < userContainer.size(); i++){
					
					if(where){
						whereClause += " WHERE user.user_id != " + userContainer.get(i).getId();	
						where = false;
					} else {
						whereClause += " AND user.user_id != " + userContainer.get(i).getId();
					}
				}
			}
			query2.append(whereClause).append(" ORDER BY user.user_id ASC");
			
			userContainer = new ArrayList<User>();
			
			ResultSet userRs2 = executeQuery(conn, query2.toString());
			
			while(userRs2.next()){
				
				id = userRs2.getInt(1);
				firstName = userRs2.getString(2);
				lastName = userRs2.getString(3);
				dbUserName = userRs2.getString(4);
				email = userRs2.getString(5);
				isActive = userRs2.getBoolean(6);
				isAdmin = userRs2.getBoolean(7);
				
				User user = new User(id, firstName, lastName, dbUserName, email, isActive, isAdmin);
				
				userContainer.add(user);
			}
			
			users = new User[userContainer.size()];
			userContainer.toArray(users);
			
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
	
	public int toggleUserActiveStatus(User user) {
		
		return toggleUserActiveStatus(user.getId(), user.getIsActive());
		
	}
	
	@Override
	public int toggleUserActiveStatus(int id, boolean isactive) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		int activate = 0;
		
		if(isactive == true){
				activate = 0;
			} else {
				activate = 1;
			}
		
		try{
			
			conn = getConnection();	
			
			query.append("UPDATE ").append(getPrimaryTableName()).append(" SET isactive = '").append(activate)
			.append("' WHERE user_id = ").append(id);

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

	public int toggleUserAdminStatus(User user){
		
		return toggleUserAdminStatus(user.getId(), user.getIsAdmin());
		
	}
	
	@Override
	public int toggleUserAdminStatus(int id, boolean isadmin) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		int admin = 0;
		
		if(isadmin == true){
				admin = 0;
			} else {
				admin = 1;
			}
		
		try{
			
			conn = getConnection();	
			
			query.append("UPDATE ").append(getPrimaryTableName()).append(" SET isadmin = '").append(admin)
			.append("' WHERE user_id = ").append(id);

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

	public int storeUser(User user) throws Exception{
		return storeUser(user.getFirstName(), user.getLastName(), user.getUserName(), user.getEmail(), user.getPw(), user.getIsActiveAsInt(), user.getIsAdminAsInt());
	}
	
	@Override
	public int storeUser(String firstName, String lastName, String userName, String email, String pw, int isactive, int isadmin) throws Exception {
		Connection conn = null;
		StringBuffer userQuery = new StringBuffer();
		StringBuffer userGroupQuery = new StringBuffer();
		int newUserId = 0;
		
		try{
			
			conn = getConnection();

			long userCount;
			
			userCount = fetchCount("username = '" + userName  + "'");
			
			userQuery.append("INSERT INTO ").append(getPrimaryTableName())
			.append(" (first_name, last_name, username, email, password, isactive, isadmin)")
			.append(" VALUES ").append("('" + firstName + "', '" + lastName + "', '" + userName +
					"', '" + email + "', '" + SimpleSHA.SHA1(pw) + "', '" + isactive + 
					"', '"+ isadmin + "')");
			
			if(userCount == 0){
			
				ResultSet rs = executeUpdateGetKeys(conn, userQuery.toString());
				
				if(rs.next()){
					newUserId = rs.getInt(1);
				}
				
			} else {
				 throw new AdaptorException("User name is already taken! Choose another one.");
			}
		} catch (Exception e){
			throw new Exception(e.getMessage());
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return newUserId;
	}
	
	public void deleteUser(User user) {
		Connection conn = null;
		StringBuffer userInGroupQuery = new StringBuffer();
		StringBuffer userQuery = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			userInGroupQuery.append("DELETE FROM ")
			.append("user_in_group")
			.append(" WHERE ").append("user_id = " + user.getId());
			
			executeUpdate(conn, userInGroupQuery.toString());
			
			userQuery.append("DELETE FROM ")
			.append(getPrimaryTableName())
			.append(" WHERE ").append("user_id = " + user.getId());
			
			executeUpdate(conn, userQuery.toString());
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
	}
}
