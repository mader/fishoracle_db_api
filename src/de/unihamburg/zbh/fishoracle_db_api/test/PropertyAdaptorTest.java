package de.unihamburg.zbh.fishoracle_db_api.test;

import junit.framework.TestCase;
import de.unihamburg.zbh.fishoracle_db_api.data.Organ;
import de.unihamburg.zbh.fishoracle_db_api.data.Property;
import de.unihamburg.zbh.fishoracle_db_api.data.TissueSample;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriver;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriverImpl;
import de.unihamburg.zbh.fishoracle_db_api.driver.OrganAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.PropertyAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.TissueSampleAdaptor;

public class PropertyAdaptorTest  extends TestCase {

	private FODriver driver;
	private PropertyAdaptor pa;
	private Property property1, property2, property3, property4, property5, property6;
	private Property[] testproperties = new Property[6];
	
	private OrganAdaptor oa;
	private Organ organ1, organ2;
	
	private TissueSampleAdaptor ta;
	private TissueSample tissue1, tissue2;
	
	protected void setUp() {
		
		driver = new FODriverImpl("localhost", "emptyoracle", "fouser", "fish4me", "3306");
		pa = (PropertyAdaptor) driver.getAdaptor("PropertyAdaptor");
		
		property1 = new Property(1, "G0", "grade", "enabled");
		property2 = new Property(2, "G1", "grade", "enabled");
		property3 = new Property(3, "G2", "grade", "enabled");
		property4 = new Property(4, "pT1", "stage", "disabled");
		property5 = new Property(5, "pT2", "stage", "disabled");
		property6 = new Property(6, "pT3", "stage", "disabled");
		
		testproperties[0] = property1;
		testproperties[1] = property2;
		testproperties[2] = property3;
		testproperties[3] = property4;
		testproperties[4] = property5;
		testproperties[5] = property6;
		
		oa = (OrganAdaptor) driver.getAdaptor("OrganAdaptor");
		ta = (TissueSampleAdaptor) driver.getAdaptor("TissueSampleAdaptor");
		
	}
	
	public void testStoreProperty() {
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
	
	public void testFetchProperties() {
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
			assertTrue(properties[i].getLabel().equals(testproperties[i].getLabel()));
			assertTrue(properties[i].getType().equals(testproperties[i].getType()));
			assertTrue(properties[i].getActivty().equals(testproperties[i].getActivty()));
		}
		
	}
	
	public void fetchPropertiesForTissueSampleId(){
		organ1 = new Organ(1, "Prostate", "Tumor tissue", "enabled");
		organ2 = new Organ(2, "Prostate", "Cell line", "enabled");

		oa.storeOrgan(organ1);
		oa.storeOrgan(organ2);
		
		tissue1 = new TissueSample(1, oa.fetchOrganById(1), pa.fetchAllProperties());
		tissue2 = new TissueSample(2, oa.fetchOrganById(2), pa.fetchAllProperties());
		
		ta.storeTissueSample(tissue1);
		ta.storeTissueSample(tissue2);
		
		Property[] properties1 = pa.fetchPropertiesForTissueSampleId(1);
		Property[] properties2 = pa.fetchPropertiesForTissueSampleId(2);
		
		for(int i=0; i < properties1.length; i++){
			assertTrue(properties1[i].getLabel().equals(testproperties[i].getLabel()));
			assertTrue(properties1[i].getType().equals(testproperties[i].getType()));
			assertTrue(properties1[i].getActivty().equals(testproperties[i].getActivty()));
		}
		
		for(int i=0; i < properties2.length; i++){
			assertTrue(properties2[i].getLabel().equals(testproperties[i].getLabel()));
			assertTrue(properties2[i].getType().equals(testproperties[i].getType()));
			assertTrue(properties2[i].getActivty().equals(testproperties[i].getActivty()));
		}
	}
	
	public void testDeleteProperty() {
		Property p1 = pa.fetchPropertyById(1);
		Property p2 = pa.fetchPropertyById(2);
		Property p3 = pa.fetchPropertyById(3);
		Property p4 = pa.fetchPropertyById(4);
		Property p5 = pa.fetchPropertyById(5);
		Property p6 = pa.fetchPropertyById(6);
		
		pa.deleteProperty(p1);
		assertTrue(((BaseAdaptor) pa).fetchCount() == 5);
		pa.deleteProperty(p2);
		assertTrue(((BaseAdaptor) pa).fetchCount() == 4);
		pa.deleteProperty(p3);
		assertTrue(((BaseAdaptor) pa).fetchCount() == 3);
		pa.deleteProperty(p4);
		assertTrue(((BaseAdaptor) pa).fetchCount() == 2);
		pa.deleteProperty(p5);
		assertTrue(((BaseAdaptor) pa).fetchCount() == 1);
		pa.deleteProperty(p6);
		assertTrue(((BaseAdaptor) pa).fetchCount() == 0);
		
	}
	
	protected void tearDown() {
		((BaseAdaptor) ta).truncateTable(((BaseAdaptor) ta).getPrimaryTableName());
		((BaseAdaptor) ta).truncateTable("tissue_sample_property");
		((BaseAdaptor) oa).truncateTable(((BaseAdaptor) oa).getPrimaryTableName());
		if(((BaseAdaptor) pa).fetchCount() == 0){
			((BaseAdaptor) pa).truncateTable(((BaseAdaptor) pa).getPrimaryTableName());
		}
	}
	
}
