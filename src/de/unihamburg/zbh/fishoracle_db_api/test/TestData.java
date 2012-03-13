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

package de.unihamburg.zbh.fishoracle_db_api.test;

import de.unihamburg.zbh.fishoracle_db_api.data.Chip;
import de.unihamburg.zbh.fishoracle_db_api.data.Group;
import de.unihamburg.zbh.fishoracle_db_api.data.User;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.ChipAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriver;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriverImpl;
import de.unihamburg.zbh.fishoracle_db_api.driver.GroupAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.UserAdaptor;

/**
 * @author Malte Mader
 *
 */
public class TestData {

	private FODriver driver;
	private UserAdaptor ua;
	private GroupAdaptor ga;
	private ChipAdaptor ca;
	
	public TestData() {
		driver = new FODriverImpl("localhost", "emptyoracle", "fouser", "fish4me", "3306");
		ua = (UserAdaptor) driver.getAdaptor("UserAdaptor");
		ga = (GroupAdaptor) driver.getAdaptor("GroupAdaptor");
		ca = (ChipAdaptor) driver.getAdaptor("ChipAdaptor");
	}

	public void createAndStoreUserData() throws Exception{
		
		User[] users = createUserData();
		
		for(int i = 0; i < users.length; i++){
			ua.storeUser(users[i]);
		}
	}
	
	public User[] createUserData(){
				
		User user1, user2, user3, user4, user5, user6;
		
		user1 = new User("Bugs", "Bunny", "bugs", "bugs@loony.tunes", "123secret", false, false);
		user2 = new User("Daffy", "Duck", "daffy", "daffy@loony.tunes", "123secret", false, false);
		user3 = new User("Porky", "Pig", "porky", "porky@loony.tunes", "123secret", true, false);
		user4 = new User("Elmer", "Fudd", "elmer", "elmer@loony.tunes", "123secret", false, true);
		user5 = new User("Yosemite", "Sam", "yosemite", "yosemite@loony.tunes", "123secret", true, true);
		user6 = new User("Sylvester", "Cat", "sylvester", "sylvester@loony.tunes", "123secret", true, true);
		
		User[] users = new User[]{user1, user2, user3, user4, user5, user6};
		
		return users;
	}
	
	public void createAndStoreGroupData(){
		
		Group[] groups = createGroupData();
		
		for(int i = 0; i < groups.length; i++){
			ga.storeGroup(groups[i]);
		}
		
	}
	
	public Group[] createGroupData(){
		
		Group group1, group2, group3;
		
		group1 = new Group(1, "Staff", true);
		group2 = new Group(2, "Students",  true);
		group3 = new Group(3, "Extern", false);
		
		Group[] groups = new Group[]{group1, group2, group3};
		
		return groups;
		
	}
	
	public void emptyUserTable(){
		((BaseAdaptor) ua).truncateTable(((BaseAdaptor) ua).getPrimaryTableName());
	}
	
	public void emptyUserInGroupTable(){
		((BaseAdaptor) ua).truncateTable("user_in_group");
	}
	
	public void createAndStoreChipData(){
		
		Chip[] chips = createChipData();
		
		for(int i = 0; i < chips.length; i++){
			ca.storeChip(chips[i]);
		}
	}
	
	public Chip[] createChipData(){
		Chip chip1, chip2, chip3;
		
		chip1 = new Chip(1, "mapping250k_sty", "snp");
		chip2 = new Chip(2, "GenomeWideSNP_6", "snp");
		chip3 = new Chip(3, "hg-u133a_2", "expression");
		
		Chip[] chips = new Chip[]{chip1, chip2, chip3};
		
		return chips;
	}
	
	public void emptyChipTable(){
		((BaseAdaptor) ca).truncateTable(((BaseAdaptor) ca).getPrimaryTableName());
	}

	public FODriver getDriver() {
		return driver;
	}

	public void setDriver(FODriver driver) {
		this.driver = driver;
	}
	
	public UserAdaptor getUa() {
		return ua;
	}

	public void setUa(UserAdaptor ua) {
		this.ua = ua;
	}

	public GroupAdaptor getGa() {
		return ga;
	}
	
	public void setGa(GroupAdaptor ga) {
		this.ga = ga;
	}

	public ChipAdaptor getCa() {
		return ca;
	}

	public void setCa(ChipAdaptor ca) {
		this.ca = ca;
	}
}