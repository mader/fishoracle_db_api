package de.unihamburg.zbh.fishoracle_db_api.example;

import de.unihamburg.zbh.fishoracle_db_api.data.User;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriver;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriverImpl;
import de.unihamburg.zbh.fishoracle_db_api.driver.UserAdaptor;

public class Example {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		FODriver exampleDriver = new FODriverImpl("localhost", "newfo", "fouser", "fish4me", "3306");
		
		int i;
		
		User user1 = new User("Bugs", "Bunny", "bugs", "bugs@bugs.bugs", "123geheim", true, true);
		
		UserAdaptor ua = (UserAdaptor) exampleDriver.getAdaptor("UserAdaptor");
		
		i = ua.storeUser(user1);
		
		//System.out.println(i);

		User user2 = ua.fetchUserByID(1);
		
		//System.out.println(user2.toString());
		
		//ua.deleteUser(user2);
		
	}

}
