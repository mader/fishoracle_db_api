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

import de.unihamburg.zbh.fishoracle_db_api.data.User;

/**
 * @author Malte Mader
 *
 */
public interface UserAdaptor {
	
	/**
	 * Stores a new user in the database. 
	 * 
	 * @param user The User Object. The user ID of the object will not
	 *         be used.
	 * @return Returns the database ID of the newly added user.
	 * @throws Exception
	 */
	public int storeUser(User user) throws Exception;
	
	/**
	 * Stores a new user in the database.
	 * 
	 * @param firstName The first name of the user.
	 * @param lastName The last name of the user.
	 * @param userName The username.
	 * @param email The e-mail of the user.
	 * @param pw The users password.
	 * @param isActive Is the user activated?
	 * @param isAdmin Has the user administration rights?
	 * @return Returns the database ID of the newly added user.
	 * @throws Exception
	 */
	public int storeUser(String firstName,
			String lastName,
			String userName,
			String email,
			String pw,
			int isActive,
			int isAdmin) throws Exception;
	
	/**
	 * Fetches all users that are stored in the database.
	 * 
	 * @return Returns an array of User objects. The User objects are
	 *          ordered by the user ID. If there are no users stored in
	 *          the database an array of length 0 is returned.
	 */
	public User[] fetchAllUsers();
	
	/**
	 * Fetches all users for a given group.
	 * 
	 * @param groupId The group ID for which the database will be queried.
	 * @return Returns an array of User objects. The User objects are
	 *          ordered by the user ID. If there are no users stored in
	 *          the database an array of length 0 is returned.
	 */
	public User[] fetchUsersForGroup(int groupId);

	/**
	 * Fetch all users that are not a member of the given group.
	 * 
	 * @param groupId The group ID for which the database will be queried.
	 * @return Returns an array of User objects. The User objects are
	 *          ordered by the user ID. If there are no users stored in
	 *          the database an array of length 0 is returned.
	 */
	public User[] fetchAllUsersNotInGroup(int groupId);
	
	/**
	 * Fetches user data for login parameters.
	 * 
	 * @param userName The given username.
	 * @param pw The given password.
	 * @return Return an User Object. Throws an exception if the login
	 *          data do not match a database entry.
	 * @throws Exception
	 */
	public User fetchUserForLogin(String userName, String pw) throws Exception;
	
	/**
	 * Fetches user data for a given user ID
	 * 
	 * @param id The user ID for which the database will be queried.
	 * @return Returns a User object. If the ID does not exist null
	 *          is returned.
	 */
	public User fetchUserByID(int id);
	
	/**
	 * Toggles the activity status of a user. The status of a user can either
	 * be active or inactive.
	 * 
	 * @param user The user to be updated.
	 * @return Returns the new activity status.
	 */
	public int toggleUserActiveStatus(User user);
	
	/**
	 * Toggles the activity status of a user. The status of a user can either
	 * be active or inactive.
	 * 
	 * @param id The user id.
	 * @param isactive The current activity status. True equals active and
	 *         false equals inactive.
	 * @return Returns the new activity status.
	 */
	public int toggleUserActiveStatus(int id, boolean isactive);
	
	/**
	 * Toggles the administrator status of a user. The status of a user can either
	 * be administrator or not administrator.
	 * 
	 * @param user The user to be updated.
	 * @return Returns the new administrator status.
	 */
	public int toggleUserAdminStatus(User user);
	
	/**
	 * Toggles the administrator status of a user. The status of a user can either
	 * be administrator or not administrator.
	 * 
	 * @param id The user id.
	 * @param isadmin The current administrator status. True equals administrator
	 *         and false equals not administartor.
	 * @return Returns the new administrator status.
	 */
	public int toggleUserAdminStatus(int id, boolean isadmin);
	
	/**
	 * Updates the first name of a user in the database.
	 * 
	 * @param userId The user Id.
	 * @param firstName The first name.
	 */
	public void updateUserFistName(int userId, String firstName);
	
	/**
	 * Updates the last name of a user in the database.
	 * 
	 * @param userId The user Id.
	 * @param lastName The last name.
	 */
	public void updateUserLastName(int userId, String lastName);
	
	/**
	 * Updates the e-mail of a user in the database.
	 * 
	 * @param userId The user Id.
	 * @param email The e-mail address.
	 */
	public void updateUserEmail(int userId, String email);
	
	/**
	 * Updates the password of a user in the database.
	 * 
	 * @param userId The user Id.
	 * @param pw The password
	 */
	public void updateUserPassword(int userId, String pw);
	
	/**
	 * Removes a user from the database.
	 * 
	 * @param user The User object for that data that should be removed.
	 */
	public void deleteUser(User user);

	final static String TYPE = "UserAdaptor";	
}