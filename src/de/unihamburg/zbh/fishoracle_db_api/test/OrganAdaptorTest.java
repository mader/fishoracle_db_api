package de.unihamburg.zbh.fishoracle_db_api.test;

import de.unihamburg.zbh.fishoracle_db_api.data.Organ;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriver;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriverImpl;
import de.unihamburg.zbh.fishoracle_db_api.driver.OrganAdaptor;
import junit.framework.TestCase;

public class OrganAdaptorTest extends TestCase {

	private FODriver driver;
	private OrganAdaptor oa;
	private Organ organ1, organ2, organ3, organ4, organ5, organ6;
	private Organ[] testorgans = new Organ[6]; 
	
	protected void setUp() {
		
		driver = new FODriverImpl("localhost", "emptyoracle", "fouser", "fish4me", "3306");
		oa = (OrganAdaptor) driver.getAdaptor("OrganAdaptor");
		
		organ1 = new Organ(1, "Prostate", "Tumor tissue", "enabled");
		organ2 = new Organ(2, "Prostate", "Cell line", "enabled");
		organ3 = new Organ(3, "Kidney", "Tumor tissue", "enabled");
		organ4 = new Organ(4, "Kidney", "Cell line", "disabled");
		organ5 = new Organ(5, "Lung", "Tumor tissue", "disabled");
		organ6 = new Organ(6, "Lung", "Cell line", "disabled");
		
		testorgans[0] = organ1;
		testorgans[1] = organ2;
		testorgans[2] = organ3;
		testorgans[3] = organ4;
		testorgans[4] = organ5;
		testorgans[5] = organ6;
		
	}
	
	public void testStoreOrgan() {
		if(((BaseAdaptor) oa).fetchCount() == 0){
			oa.storeOrgan(organ1);
			assertTrue(((BaseAdaptor) oa).fetchCount() == 1);
			oa.storeOrgan(organ2);
			assertTrue(((BaseAdaptor) oa).fetchCount() == 2);
			oa.storeOrgan(organ3);
			assertTrue(((BaseAdaptor) oa).fetchCount() == 3);
			oa.storeOrgan(organ4);
			assertTrue(((BaseAdaptor) oa).fetchCount() == 4);
			oa.storeOrgan(organ5);
			assertTrue(((BaseAdaptor) oa).fetchCount() == 5);
			oa.storeOrgan(organ6);
			assertTrue(((BaseAdaptor) oa).fetchCount() == 6);
		}
	}
	
	public void testFetchOrganById() {
		Organ o1 = oa.fetchOrganById(1);
		Organ o2 = oa.fetchOrganById(2);
		Organ o3 = oa.fetchOrganById(3);
		Organ o4 = oa.fetchOrganById(4);
		Organ o5 = oa.fetchOrganById(5);
		Organ o6 = oa.fetchOrganById(6);
		
		assertTrue(o1.getId() == 1);
		assertTrue(o1.getLabel().equals(organ1.getLabel()));
		assertTrue(o1.getType().equals(organ1.getType()));
		assertTrue(o1.getActivty().equals(organ1.getActivty()));
		
		assertTrue(o2.getId() == 2);
		assertTrue(o2.getLabel().equals(organ2.getLabel()));
		assertTrue(o2.getType().equals(organ2.getType()));
		assertTrue(o2.getActivty().equals(organ2.getActivty()));
		
		assertTrue(o3.getId() == 3);
		assertTrue(o3.getLabel().equals(organ3.getLabel()));
		assertTrue(o3.getType().equals(organ3.getType()));
		assertTrue(o3.getActivty().equals(organ3.getActivty()));
		
		assertTrue(o4.getId() == 4);
		assertTrue(o4.getLabel().equals(organ4.getLabel()));
		assertTrue(o4.getType().equals(organ4.getType()));
		assertTrue(o4.getActivty().equals(organ4.getActivty()));
		
		assertTrue(o5.getId() == 5);
		assertTrue(o5.getLabel().equals(organ5.getLabel()));
		assertTrue(o5.getType().equals(organ5.getType()));
		assertTrue(o5.getActivty().equals(organ5.getActivty()));
		
		assertTrue(o6.getId() == 6);
		assertTrue(o6.getLabel().equals(organ6.getLabel()));
		assertTrue(o6.getType().equals(organ6.getType()));
		assertTrue(o6.getActivty().equals(organ6.getActivty()));
	}
	
	public void testFetchOrganByType(){
		Organ[] o1 = oa.fetchOrgansByType("Tumor tissue");
		Organ[] o2 = oa.fetchOrgansByType("Cell line");
		
		assertTrue(o1[0].getId() == 1);
		assertTrue(o1[1].getId() == 3);
		assertTrue(o1[2].getId() == 5);
		
		assertTrue(o2[0].getId() == 2);
		assertTrue(o2[1].getId() == 4);
		assertTrue(o2[2].getId() == 6);
		
	}
	
	public void testFetchAllTypes() {
		String[] types = oa.fetchAllTypes();
		
		types[0].equals("Tumor tissue");
		types[1].equals("Cell line");
		
	}
	
	public void stFetchAllTypes() {
		Organ[] o1 = oa.fetchOrgans(true);
		Organ[] o2 = oa.fetchOrgans(false);
		
		assertTrue(o1[0].getId() == 1);
		assertTrue(o1[1].getId() == 2);
		assertTrue(o1[2].getId() == 3);
	
		assertTrue(o2[0].getId() == 4);
		assertTrue(o2[1].getId() == 5);
		assertTrue(o2[2].getId() == 6);
	}
	
	public void fetchAllOrgans() {
		Organ[] organs = oa.fetchAllOrgans();
		
		for (int i=0; i < organs.length; i++) {
			assertTrue(organs[i].getId() == 1);
			assertTrue(organs[i].getLabel().equals(testorgans[i].getLabel()));
			assertTrue(organs[i].getType().equals(testorgans[i].getType()));
			assertTrue(organs[i].getActivty().equals(testorgans[i].getActivty()));
		}
		
	}
	
	public void testDeleteOrgan() {
		Organ o1 = oa.fetchOrganById(1);
		Organ o2 = oa.fetchOrganById(2);
		Organ o3 = oa.fetchOrganById(3);
		Organ o4 = oa.fetchOrganById(4);
		Organ o5 = oa.fetchOrganById(5);
		Organ o6 = oa.fetchOrganById(6);
		
		oa.deleteOrgan(o1);
		assertTrue(((BaseAdaptor) oa).fetchCount() == 5);
		oa.deleteOrgan(o2);
		assertTrue(((BaseAdaptor) oa).fetchCount() == 4);
		oa.deleteOrgan(o3);
		assertTrue(((BaseAdaptor) oa).fetchCount() == 3);
		oa.deleteOrgan(o4);
		assertTrue(((BaseAdaptor) oa).fetchCount() == 2);
		oa.deleteOrgan(o5);
		assertTrue(((BaseAdaptor) oa).fetchCount() == 1);
		oa.deleteOrgan(o6);
		assertTrue(((BaseAdaptor) oa).fetchCount() == 0);
		
	}
	
	protected void tearDown() {
		if(((BaseAdaptor) oa).fetchCount() == 0){
			((BaseAdaptor) oa).truncateTable(((BaseAdaptor) oa).getPrimaryTableName());
		}
	}
}