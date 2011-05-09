package de.unihamburg.zbh.fishoracle_db_api.test;

import de.unihamburg.zbh.fishoracle_db_api.data.Organ;
import de.unihamburg.zbh.fishoracle_db_api.data.Property;
import de.unihamburg.zbh.fishoracle_db_api.data.TissueSample;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriver;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriverImpl;
import de.unihamburg.zbh.fishoracle_db_api.driver.OrganAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.PropertyAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.TissueSampleAdaptor;
import junit.framework.TestCase;

public class TissueAdaptorTest extends TestCase{

	private FODriver driver;
	private OrganAdaptor oa;
	private Organ organ1, organ2;
	private Organ[] testorgans = new Organ[2];
	
	private PropertyAdaptor pa;
	private Property property1, property2;
	private Property[] testpropertys = new Property[2]; 
	
	private TissueSampleAdaptor ta;
	private TissueSample tissue1, tissue2;
	private TissueSample[] testtissues = new TissueSample[2];
	
	protected void setUp() {
		
		driver = new FODriverImpl("localhost", "emptyoracle", "fouser", "fish4me", "3306");
		oa = (OrganAdaptor) driver.getAdaptor("OrganAdaptor");
		
		organ1 = new Organ(1, "Prostate", "Tumor tissue", "enabled");
		organ2 = new Organ(2, "Prostate", "Cell line", "enabled");
		
		testorgans[0] = organ1;
		testorgans[1] = organ2;

		pa = (PropertyAdaptor) driver.getAdaptor("PropertyAdaptor");
		
		property1 = new Property(1, "G0", "grade", "enabled");
		property2 = new Property(2, "G1", "grade", "enabled");
		
		testpropertys[0] = property1;
		testpropertys[1] = property2;
		
		ta = (TissueSampleAdaptor) driver.getAdaptor("TissueSampleAdaptor");
		
		tissue1 = new TissueSample(1, oa.fetchOrganById(1), pa.fetchAllProperties());
		tissue2 = new TissueSample(2, oa.fetchOrganById(2), pa.fetchAllProperties());
		
		testtissues[0] = tissue1;
		testtissues[1] = tissue2;
		
	}
	
	public void testStoreTissueSample() {
		ta.storeTissueSample(tissue1);
		assertTrue(((BaseAdaptor) ta).fetchCount() == 1);
		ta.storeTissueSample(tissue2);
		assertTrue(((BaseAdaptor) ta).fetchCount() == 2);
	}
	
	public void testFetchTissueById() {
		TissueSample t1 = ta.fetchTissueSampleById(1);
		TissueSample t2 = ta.fetchTissueSampleById(2);
		
		assertTrue(t1.getId() == 1);
		assertTrue(t1.getOrgan().getId() == tissue1.getOrgan().getId());
		for(int i = 0; i < t1.getProperties().length; i++) {
			assertTrue(t1.getProperties()[i].getId() == tissue1.getProperties()[i].getId());
		}
		
		assertTrue(t2.getId() == 2);
		assertTrue(t2.getOrgan().getId() == tissue2.getOrgan().getId());
		for(int i = 0; i < t2.getProperties().length; i++) {
			assertTrue(t2.getProperties()[i].getId() == tissue2.getProperties()[i].getId());
		}
	}
	
	public void testDeleteOrgan() {
		TissueSample t1 = ta.fetchTissueSampleById(1);
		TissueSample t2 = ta.fetchTissueSampleById(2);
		
		ta.deleteTissueSample(t1);
		assertTrue(((BaseAdaptor) ta).fetchCount() == 1);
		ta.deleteTissueSample(t2);
		assertTrue(((BaseAdaptor) ta).fetchCount() == 2);
	}
	
	protected void tearDown() {
		if(((BaseAdaptor) oa).fetchCount() == 0){
			((BaseAdaptor) oa).truncateTable(((BaseAdaptor) oa).getPrimaryTableName());
		}
	}
	
}
