package de.unihamburg.zbh.fishoracle_db_api.test;

import de.unihamburg.zbh.fishoracle_db_api.data.Chip;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.ChipAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriver;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriverImpl;
import junit.framework.TestCase;

public class ChipAdaptorTest extends TestCase {
	private FODriver driver;
	private ChipAdaptor ca;
	private Chip chip1, chip2, chip3;
	private Chip[] testchips = new Chip[3];
	
	protected void setUp() {
		driver = new FODriverImpl("localhost", "emptyoracle", "fouser", "fish4me", "3306");
		ca = (ChipAdaptor) driver.getAdaptor("ChipAdaptor");
		
		chip1 = new Chip(1, "mapping250k_sty", "snp");
		chip2 = new Chip(2, "GenomeWideSNP_6", "snp");
		chip3 = new Chip(3, "hg-u133a_2", "expression");
		
		testchips[0] = chip1;
		testchips[1] = chip2;
		testchips[2] = chip3;
	}
	
	public void testStoreChip(){
		
		ca.storeChip(chip1);
		assertTrue(((BaseAdaptor) ca).fetchCount() == 1);
		ca.storeChip(chip2);
		assertTrue(((BaseAdaptor) ca).fetchCount() == 2);
		ca.storeChip(chip3);
		assertTrue(((BaseAdaptor) ca).fetchCount() == 3);
	}
	
	public void testFetchChipById(){
		Chip c1 = ca.fetchChipById(1);
		Chip c2 = ca.fetchChipById(2);
		Chip c3 = ca.fetchChipById(3);
		
		assertTrue(c1.getId() == 1);
		assertTrue(c1.getName().equals(chip1.getName()));
		assertTrue(c1.getType().equals(chip1.getType()));
		
		assertTrue(c2.getId() == 2);
		assertTrue(c2.getName().equals(chip2.getName()));
		assertTrue(c2.getType().equals(chip2.getType()));
		
		assertTrue(c3.getId() == 3);
		assertTrue(c3.getName().equals(chip3.getName()));
		assertTrue(c3.getType().equals(chip3.getType()));
	}
		
	public void testDeleteChip(){
		Chip c1 = ca.fetchChipById(1);
		Chip c2 = ca.fetchChipById(2);
		Chip c3 = ca.fetchChipById(3);
		
		ca.deleteChip(c1);
		assertTrue(((BaseAdaptor) ca).fetchCount() == 2);
		ca.deleteChip(c2);
		assertTrue(((BaseAdaptor) ca).fetchCount() == 1);
		ca.deleteChip(c3);
		assertTrue(((BaseAdaptor) ca).fetchCount() == 0);
	}
	
	protected void tearDown() {
		if(((BaseAdaptor) ca).fetchCount() == 0){
			((BaseAdaptor) ca).truncateTable(((BaseAdaptor) ca).getPrimaryTableName());
		}
	}
}
