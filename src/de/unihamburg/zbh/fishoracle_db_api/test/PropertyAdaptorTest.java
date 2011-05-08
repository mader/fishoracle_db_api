package de.unihamburg.zbh.fishoracle_db_api.test;

import junit.framework.TestCase;
import de.unihamburg.zbh.fishoracle_db_api.data.Property;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriver;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriverImpl;
import de.unihamburg.zbh.fishoracle_db_api.driver.PropertyAdaptor;

public class PropertyAdaptorTest  extends TestCase {

	private FODriver driver;
	private PropertyAdaptor pa;
	private Property property1, property2, property3, property4, property5, property6;
	private Property[] testpropertys = new Property[6]; 
	
	protected void setUp() {
		
		driver = new FODriverImpl("localhost", "emptyoracle", "fouser", "fish4me", "3306");
		pa = (PropertyAdaptor) driver.getAdaptor("PropertyAdaptor");
		
		property1 = new Property(1, "G0", "grade", "enabled");
		property2 = new Property(2, "G1", "grade", "enabled");
		property3 = new Property(3, "G2", "grade", "enabled");
		property4 = new Property(4, "pT1", "stage", "disabled");
		property5 = new Property(5, "pT2", "stage", "disabled");
		property6 = new Property(6, "pT3", "stage", "disabled");
		
		testpropertys[0] = property1;
		testpropertys[1] = property2;
		testpropertys[2] = property3;
		testpropertys[3] = property4;
		testpropertys[4] = property5;
		testpropertys[5] = property6;
		
	}
	
	public void testStoreProperty() {
		if(((BaseAdaptor) pa).fetchCount() == 0){
			pa.storeProperty(property1);
			assertTrue(((BaseAdaptor) pa).fetchCount() == 1);
			pa.storeProperty(property2);
			assertTrue(((BaseAdaptor) pa).fetchCount() == 2);
			pa.storeProperty(property3);
			assertTrue(((BaseAdaptor) pa).fetchCount() == 3);
			pa.storeProperty(property4);
			assertTrue(((BaseAdaptor) pa).fetchCount() == 4);
			pa.storeProperty(property5);
			assertTrue(((BaseAdaptor) pa).fetchCount() == 5);
			pa.storeProperty(property6);
			assertTrue(((BaseAdaptor) pa).fetchCount() == 6);
		}
	}
	
	public void testFetchPropertyById() {
		Property p1 = pa.fetchPropertyById(1);
		Property p2 = pa.fetchPropertyById(2);
		Property p3 = pa.fetchPropertyById(3);
		Property p4 = pa.fetchPropertyById(4);
		Property p5 = pa.fetchPropertyById(5);
		Property p6 = pa.fetchPropertyById(6);
		
		assertTrue(p1.getId() == 1);
		assertTrue(p1.getLabel().equals(property1.getLabel()));
		assertTrue(p1.getType().equals(property1.getType()));
		assertTrue(p1.getActivty().equals(property1.getActivty()));
		
		assertTrue(p2.getId() == 2);
		assertTrue(p2.getLabel().equals(property2.getLabel()));
		assertTrue(p2.getType().equals(property2.getType()));
		assertTrue(p2.getActivty().equals(property2.getActivty()));
		
		assertTrue(p3.getId() == 3);
		assertTrue(p3.getLabel().equals(property3.getLabel()));
		assertTrue(p3.getType().equals(property3.getType()));
		assertTrue(p3.getActivty().equals(property3.getActivty()));
		
		assertTrue(p4.getId() == 4);
		assertTrue(p4.getLabel().equals(property4.getLabel()));
		assertTrue(p4.getType().equals(property4.getType()));
		assertTrue(p4.getActivty().equals(property4.getActivty()));
		
		assertTrue(p5.getId() == 5);
		assertTrue(p5.getLabel().equals(property5.getLabel()));
		assertTrue(p5.getType().equals(property5.getType()));
		assertTrue(p5.getActivty().equals(property5.getActivty()));
		
		assertTrue(p6.getId() == 6);
		assertTrue(p6.getLabel().equals(property6.getLabel()));
		assertTrue(p6.getType().equals(property6.getType()));
		assertTrue(p6.getActivty().equals(property6.getActivty()));
	}
	
	public void testFetchPropertyByType(){
		Property[] p1 = pa.fetchPropertiesByType("grade");
		Property[] p2 = pa.fetchPropertiesByType("stage");
		
		assertTrue(p1[0].getId() == 1);
		assertTrue(p1[1].getId() == 2);
		assertTrue(p1[2].getId() == 3);
		
		assertTrue(p2[0].getId() == 4);
		assertTrue(p2[1].getId() == 5);
		assertTrue(p2[2].getId() == 6);
		
	}
	
	public void testFetchAllTypes() {
		String[] types = pa.fetchAllTypes();
		
		types[0].equals("grade");
		types[1].equals("stage");
		
	}
	
	public void stFetchAllTypes() {
		Property[] p1 = pa.fetchProperties(true);
		Property[] p2 = pa.fetchProperties(false);
		
		assertTrue(p1[0].getId() == 1);
		assertTrue(p1[1].getId() == 2);
		assertTrue(p1[2].getId() == 3);
	
		assertTrue(p2[0].getId() == 4);
		assertTrue(p2[1].getId() == 5);
		assertTrue(p2[2].getId() == 6);
	}
	
	public void fetchAllPropertys() {
		Property[] properties = pa.fetchAllProperties();
		
		for (int i=0; i < properties.length; i++) {
			assertTrue(properties[i].getId() == 1);
			assertTrue(properties[i].getLabel().equals(testpropertys[i].getLabel()));
			assertTrue(properties[i].getType().equals(testpropertys[i].getType()));
			assertTrue(properties[i].getActivty().equals(testpropertys[i].getActivty()));
		}
		
	}
	
	public void testDeleteProperty() {
		Property o1 = pa.fetchPropertyById(1);
		Property o2 = pa.fetchPropertyById(2);
		Property o3 = pa.fetchPropertyById(3);
		Property o4 = pa.fetchPropertyById(4);
		Property o5 = pa.fetchPropertyById(5);
		Property o6 = pa.fetchPropertyById(6);
		
		pa.deleteProperty(o1);
		assertTrue(((BaseAdaptor) pa).fetchCount() == 5);
		pa.deleteProperty(o2);
		assertTrue(((BaseAdaptor) pa).fetchCount() == 4);
		pa.deleteProperty(o3);
		assertTrue(((BaseAdaptor) pa).fetchCount() == 3);
		pa.deleteProperty(o4);
		assertTrue(((BaseAdaptor) pa).fetchCount() == 2);
		pa.deleteProperty(o5);
		assertTrue(((BaseAdaptor) pa).fetchCount() == 1);
		pa.deleteProperty(o6);
		assertTrue(((BaseAdaptor) pa).fetchCount() == 0);
		
	}
	
	protected void tearDown() {
		if(((BaseAdaptor) pa).fetchCount() == 0){
			((BaseAdaptor) pa).truncateTable(((BaseAdaptor) pa).getPrimaryTableName());
		}
	}
	
}
