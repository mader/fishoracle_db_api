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

package de.unihamburg.zbh.fishoracle_db_api.driver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

import de.unihamburg.zbh.fishoracle_db_api.data.User;
import de.unihamburg.zbh.fishoracle_db_api.util.SimpleSHA;

public class UserAdaptorImpl extends BaseAdaptor implements UserAdaptor{
	
	protected UserAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}

	@Override
	protected String[] tables() {
		return new String[]{"user"};
	}

	/* we do not need the 'password' column most of the time */
	@Override
	protected String[] columns() {
		return new String[]{"user.user_id",
							"user.first_name",
							"user.last_name",
							"user.username",
							"user.email",
							"user.isactive",
							"user.isadmin"};
	}
	
	@Override
	protected String[][] leftJoins() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int storeUser(User user) throws Exception {
		return storeUser(user.getFirstName(),
				user.getLastName(),
				user.getUserName(),
				user.getEmail(),
				user.getPw(),
				user.getIsActiveAsInt(),
				user.getIsAdminAsInt());
	}
	
	@Override
	public int storeUser(String firstName,
							String lastName,
							String userName,
							String email,
							String pw,
							int isActive,
							int isAdmin) throws Exception {
		Connection conn = null;
		StringBuffer userQuery = new StringBuffer();
		int newUserId = 0;
		
		try{
			
			conn = getConnection();
	
			long userCount;
			
			userCount = super.fetchCount(super.getPrimaryTableName(),"username = '" + userName + "'");
			
			if(userCount == 0){
				
				userQuery.append("INSERT INTO ").append(super.getPrimaryTableName())
					.append(" (first_name, last_name, username, email, password, isactive, isadmin)")
					.append(" VALUES ").append("('" + firstName + "', '" + lastName +
					"', '" + userName + "', '" + email + "', '" +
					SimpleSHA.SHA1(pw) + "', '" + isActive + 
					"', '"+ isAdmin + "')");
			
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

	@Override
	public Object createObject(ResultSet rs) {
		
		User user = null;
		int id = 0;
		String firstName = null;
		String lastName = null;
		String dbUserName = null;
		String email = null;
		Boolean isActive = null;
		Boolean isAdmin = null;
		
		try {
			if(rs.next()){
				id = rs.getInt(1);
				firstName = rs.getString(2);
				lastName = rs.getString(3);
				dbUserName = rs.getString(4);
				email = rs.getString(5);
				isActive = rs.getBoolean(6);
				isAdmin = rs.getBoolean(7);
				
				user = new User(id, firstName, lastName, dbUserName, email, isActive, isAdmin);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;

	}

	@Override
	public User[] fetchAllUsers() {
		
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		User user = null;
		User[] users = null;
		
		try{
			
			conn = getConnection();
			int userCount = (int) fetchCount();
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" ORDER BY user_id ASC");
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			users = new User[userCount];
			int i = 0;
			Object o;
			
			while ((o = createObject(userRs)) != null) {
				user = (User) o;
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
	public User[] fetchUsersForGroup(int groupId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		ArrayList<User> userContainer = new ArrayList<User>();
		User user = null;
		User[] users = null;
		
		try{
			
			conn = getConnection();
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(getPrimaryTableName())
			.append(" LEFT JOIN user_in_group ON user.user_id = user_in_group.user_id")
			.append(" WHERE user_in_group.group_id = " + groupId)
			.append(" ORDER BY user_id ASC");
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(userRs)) != null) {
				user = (User) o;
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
	
	@Override
	public User[] fetchAllUsersNotInGroup(int groupId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		User userNotInGroup = null;
		ArrayList<User> userContainer = new ArrayList<User>();
		User[] users = null;
		
		try{
			
			conn = getConnection();
			
			/* First get all users that _are_ in the group.*/
			User[] usersInGroup = fetchUsersForGroup(groupId);

			/* Then get all users but the users we just received. */
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName());
			
			String whereClause = " ";
			
			boolean where = true;
			if(usersInGroup.length > 0){
				for(int i=0; i < usersInGroup.length; i++){
					
					if(where){
						whereClause += " WHERE user.user_id != " + usersInGroup[i].getId();	
						where = false;
					} else {
						whereClause += " AND user.user_id != " + usersInGroup[i].getId();
					}
				}
			}
			query.append(whereClause).append(" ORDER BY user.user_id ASC");
			
			userContainer = new ArrayList<User>();
			
			ResultSet userRs2 = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(userRs2)) != null) {
				userNotInGroup = (User) o;
				userContainer.add(userNotInGroup);
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

	@Override
	public User fetchUserForLogin(String userName, String pw) throws Exception {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		User user = null;
		
		try{
			
			conn = getConnection();
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" WHERE ").append("user.username = '" + userName +
								"' AND user.password = '" + SimpleSHA.SHA1(pw) + "'");
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(userRs)) != null) {
				user = (User) o;
			}
			
			if(user == null){
				
				throw new AdaptorException("User name or password incorrect!");
				
			}
			if(!user.getIsActive()){
				
				throw new AdaptorException("Your account has not been activated. If you registered recently" +
						                    " this means that your acount has not been verified yet. Just try to log in later." +
						                    " If your account has been deactivated or your registration was more than 3 days ago" +
						                    " then contact the webmaster.");
			}
			
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
	public User fetchUserByID(int id) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		User user = null;
		
		try{
			
			conn = getConnection();
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" WHERE ").append("user_id = " + id);
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			Object o;
			
			if ((o = createObject(userRs)) != null) {
				user = (User) o;
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return user;
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
			
			query.append("UPDATE ").append(super.getPrimaryTableName())
			.append(" SET isactive = '").append(activate)
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
			
			query.append("UPDATE ").append(super.getPrimaryTableName())
			.append(" SET isadmin = '").append(admin)
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
	
	@Override
	public void updateUserFistName(int userId, String firstName) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try {
			
			conn = getConnection();	
			
			query.append("UPDATE ").append(super.getPrimaryTableName())
			.append(" SET first_name = '").append(firstName)
			.append("' WHERE user_id = ").append(userId);

			executeUpdate(conn, query.toString());
			
		} catch (Exception e){
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		} finally {
			if(conn != null){
				close(conn);
			}
		}
	}
	
	@Override
	public void updateUserLastName(int userId, String lastName) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try {
			
			conn = getConnection();	
			
			query.append("UPDATE ").append(super.getPrimaryTableName())
			.append(" SET last_name = '").append(lastName)
			.append("' WHERE user_id = ").append(userId);

			executeUpdate(conn, query.toString());
			
		} catch (Exception e){
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		} finally {
			if(conn != null){
				close(conn);
			}
		}
	}

	@Override
	public void updateUserEmail(int userId, String email) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try {
			
			conn = getConnection();	
			
			query.append("UPDATE ").append(super.getPrimaryTableName())
			.append(" SET email = '").append(email)
			.append("' WHERE user_id = ").append(userId);

			executeUpdate(conn, query.toString());
			
		} catch (Exception e){
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		} finally {
			if(conn != null){
				close(conn);
			}
		}
	}
	
	@Override
	public void updateUserPassword(int userId, String pw) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try {
			
			conn = getConnection();	
			
			query.append("UPDATE ").append(super.getPrimaryTableName())
			.append(" SET password = '").append(SimpleSHA.SHA1(pw))
			.append("' WHERE user_id = ").append(userId);

			executeUpdate(conn, query.toString());
			
		} catch (Exception e){
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		} finally {
			if(conn != null){
				close(conn);
			}
		}
	}
	
	@Override
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
			.append(super.getPrimaryTableName())
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