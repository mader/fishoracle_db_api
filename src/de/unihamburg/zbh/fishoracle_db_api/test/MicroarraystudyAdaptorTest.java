package de.unihamburg.zbh.fishoracle_db_api.test;

import de.unihamburg.zbh.fishoracle_db_api.data.Chip;
import de.unihamburg.zbh.fishoracle_db_api.data.CnSegment;
import de.unihamburg.zbh.fishoracle_db_api.data.Microarraystudy;
import de.unihamburg.zbh.fishoracle_db_api.data.Organ;
import de.unihamburg.zbh.fishoracle_db_api.data.Property;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.ChipAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriver;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriverImpl;
import de.unihamburg.zbh.fishoracle_db_api.driver.MicroarraystudyAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.OrganAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.PropertyAdaptor;
import junit.framework.TestCase;

public class MicroarraystudyAdaptorTest extends TestCase{

	private FODriver driver;
	private MicroarraystudyAdaptor ma;
	private CnSegment segment1, segment2, segment3, segment4;
	private CnSegment[] testsegments1 = new CnSegment[2];
	private CnSegment[] testsegments2 = new CnSegment[2];
	
	private Microarraystudy mstudy1;
	private Microarraystudy mstudy2;
	private Microarraystudy[] teststudies = new Microarraystudy[2];
	
	private ChipAdaptor ca;
	private Chip chip1, chip2, chip3;
	
	private OrganAdaptor oa;
	private Organ organ1, organ2;
	private Organ[] testorgans = new Organ[2];
	
	private PropertyAdaptor pa;
	private Property property1, property2;
	private Property[] testproperties = new Property[2]; 
	
	
	protected void setUp() {
		driver = new FODriverImpl("localhost", "emptyoracle", "fouser", "fish4me", "3306");
		ma = (MicroarraystudyAdaptor) driver.getAdaptor("MicroarraystudyAdaptor");
		
		segment1 = new CnSegment(1, "1", 1, 5000, 0.5, 100);
		segment2 = new CnSegment(2, "1", 5001, 8000, 0.7, 300);
		segment3 = new CnSegment(3, "2", 1, 2000, 0.3, 600);
		segment4 = new CnSegment(4, "3", 1, 1000, 1.2, 250);
		
		testsegments1[0] = segment1;
		testsegments1[1] = segment2;
		testsegments2[0] = segment3;
		testsegments2[1] = segment4;
		
		ca = (ChipAdaptor) driver.getAdaptor("ChipAdaptor");
		
		chip1 = new Chip(1, "mapping250k_sty", "snp");
		chip2 = new Chip(2, "GenomeWideSNP_6", "snp");
		chip3 = new Chip(3, "hg-u133a_2", "expression");
		
		ca.storeChip(chip1);
		ca.storeChip(chip2);
		ca.storeChip(chip3);
		
		oa = (OrganAdaptor) driver.getAdaptor("OrganAdaptor");
		
		organ1 = new Organ(1, "Prostate", "Tumor tissue", "enabled");
		organ2 = new Organ(2, "Prostate", "Cell line", "enabled");
		
		testorgans[0] = organ1;
		testorgans[1] = organ2;

		oa.storeOrgan(organ1);
		oa.storeOrgan(organ2);
		
		pa = (PropertyAdaptor) driver.getAdaptor("PropertyAdaptor");
		
		property1 = new Property(1, "G0", "grade", "enabled");
		property2 = new Property(2, "G1", "grade", "enabled");
		
		testproperties[0] = property1;
		testproperties[1] = property2;
		
		pa.storeProperty(property1);
		pa.storeProperty(property2);
		
		int[] propertyIds = new int[testproperties.length];
		
		for(int i = 0; i < testproperties.length; i++){
			propertyIds[i] = testproperties[i].getId();
		}
		
		String description1 = "This is a description.";
		
		mstudy1 = new Microarraystudy(testsegments1, "teststudy1", description1, 1, 1, propertyIds, 1);
		mstudy2 = new Microarraystudy(testsegments2, "teststudy2", description1, 2, 2, propertyIds, 1);
		
		teststudies[0] = mstudy1;
		teststudies[1] = mstudy2;
		
	}
	
	public void testStoreMicroarraystudy(){
		
		ma.storeMicroarraystudy(mstudy1);
		assertTrue(((BaseAdaptor) ma).fetchCount() == 1);
		ma.storeMicroarraystudy(mstudy2);
		assertTrue(((BaseAdaptor) ma).fetchCount() == 2);
	}
	
	public void testFetchMicroarraystudyById() {
		Microarraystudy m1 = ma.fetchMicroarraystudyById(1);
		
		//TODO extend this...
		assertTrue(m1.getId() == 1);
		assertTrue(m1.getName().equals(mstudy1.getName()));
		assertTrue(m1.getDescription().equals(mstudy1.getDescription()));
		assertTrue(m1.getChip().getName().equals(chip1.getName()));
		assertTrue(m1.getTissue().getOrgan().getLabel().equals(organ1.getLabel()));
		assertTrue(m1.getTissue().getOrgan().getType().equals(organ1.getType()));
		assertTrue(m1.getTissue().getProperties()[0].getLabel().equals(property1.getLabel()));
		assertTrue(m1.getTissue().getProperties()[0].getType().equals(property1.getType()));
		assertTrue(m1.getSegments()[0].getId() == 1);
		assertTrue(m1.getSegments()[0].getChromosome().equals(segment1.getChromosome()));
		assertTrue(m1.getSegments()[0].getStart() == segment1.getStart());
		assertTrue(m1.getSegments()[0].getEnd() == segment1.getEnd());
	}
	
	public void testFetchAllMicroarraystudies(){
		Microarraystudy[] mstudies = ma.fetchAllMicroarraystudies();
		
		//TODO extend this...
		for(int i = 0; i < mstudies.length; i++) {
			assertTrue(mstudies[i].getId() == (i+1));
			assertTrue(mstudies[i].getName().equals(teststudies[i].getName()));
			assertTrue(mstudies[i].getDescription().equals(teststudies[i].getDescription()));
		}
	}
	
	public void testDeleteMicroarraystudy(){
		Microarraystudy m1 = ma.fetchMicroarraystudyById(1);
		Microarraystudy m2 = ma.fetchMicroarraystudyById(2);
		
		ma.deleteMicroarraystudy(m1);
		assertTrue(((BaseAdaptor) ma).fetchCount() == 1);
		ma.deleteMicroarraystudy(m2);
		assertTrue(((BaseAdaptor) ma).fetchCount() == 0);
	}
	
	protected void tearDown() {
		((BaseAdaptor) ca).truncateTable(((BaseAdaptor) ca).getPrimaryTableName());
		((BaseAdaptor) oa).truncateTable(((BaseAdaptor) oa).getPrimaryTableName());
		((BaseAdaptor) pa).truncateTable(((BaseAdaptor) pa).getPrimaryTableName());
		
		if(((BaseAdaptor) ma).fetchCount() == 0){
			((BaseAdaptor) ma).truncateTable(((BaseAdaptor) ma).getPrimaryTableName());
			((BaseAdaptor) ma).truncateTable("sample_on_chip");
			((BaseAdaptor) ma).truncateTable("tissue_sample");
			((BaseAdaptor) ma).truncateTable("tissue_sample_property");
			((BaseAdaptor) ma).truncateTable("cn_segment");
		}
	}
}
