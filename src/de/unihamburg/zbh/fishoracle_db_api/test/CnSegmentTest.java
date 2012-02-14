package de.unihamburg.zbh.fishoracle_db_api.test;

import de.unihamburg.zbh.fishoracle_db_api.data.Chip;
import de.unihamburg.zbh.fishoracle_db_api.data.CnSegment;
import de.unihamburg.zbh.fishoracle_db_api.data.Location;
import de.unihamburg.zbh.fishoracle_db_api.data.Microarraystudy;
import de.unihamburg.zbh.fishoracle_db_api.data.Organ;
import de.unihamburg.zbh.fishoracle_db_api.data.Project;
import de.unihamburg.zbh.fishoracle_db_api.data.Property;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.ChipAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.CnSegmentAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriver;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriverImpl;
import de.unihamburg.zbh.fishoracle_db_api.driver.MicroarraystudyAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.OrganAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.ProjectAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.PropertyAdaptor;
import junit.framework.TestCase;

public class CnSegmentTest extends TestCase{

	private FODriver driver;
	private CnSegmentAdaptor sa;
	private CnSegment segment1, segment2, segment3, segment4;
	private CnSegment[] testsegments = new CnSegment[4];
	private Location loc1, loc2, maxOverlapLoc;
	
	private ChipAdaptor ca;
	private Chip chip1;
	
	private OrganAdaptor oa;
	private Organ organ1, organ2;

	private PropertyAdaptor pa;
	private Property property1, property2;
	private Property[] testproperties = new Property[2];
	
	private MicroarraystudyAdaptor ma;
	private Microarraystudy mstudy1;
	
	private ProjectAdaptor pra;
	private Project project1;
	
	protected void setUp() {
		driver = new FODriverImpl("localhost", "emptyoracle", "fouser", "fish4me", "3306");
		sa = (CnSegmentAdaptor) driver.getAdaptor("CnSegmentAdaptor");
		
		segment1 = new CnSegment(1, "1", 1, 5000, 0.5, 100);
		segment2 = new CnSegment(2, "1", 5001, 8000, 0.7, 300);
		segment3 = new CnSegment(3, "2", 1, 2000, 0.3, 600);
		segment4 = new CnSegment(4, "3", 1, 1000, 1.2, 250);
		
		maxOverlapLoc = new Location("1", 1, 8000);
		
		loc1 = new Location("1", 1, 5000);
		loc2 = new Location("1", 5001, 8000);
		
		testsegments[0] = segment1;
		testsegments[1] = segment2;
		testsegments[2] = segment3;
		testsegments[3] = segment4;
		
		ca = (ChipAdaptor) driver.getAdaptor("ChipAdaptor");
		
		chip1 = new Chip(1, "mapping250k_sty", "snp");
		
		ca.storeChip(chip1);
		
		oa = (OrganAdaptor) driver.getAdaptor("OrganAdaptor");
		
		organ1 = new Organ(1, "Prostate", "Tumor tissue", "enabled");
		organ2 = new Organ(2, "Prostate", "Cell line", "enabled");

		oa.storeOrgan(organ1);
		oa.storeOrgan(organ2);
		
		pa = (PropertyAdaptor) driver.getAdaptor("PropertyAdaptor");
		
		property1 = new Property(1, "G0", "grade", "enabled");
		property2 = new Property(2, "G1", "grade", "enabled");
		
		pa.storeProperty(property1);
		pa.storeProperty(property2);
		
		testproperties[0] = property1;
		testproperties[1] = property2;
		
		int[] propertyIds = new int[testproperties.length];
		
		for(int i = 0; i < testproperties.length; i++){
			propertyIds[i] = testproperties[i].getId();
		}
		
		pra = (ProjectAdaptor) driver.getAdaptor("ProjectAdaptor");
		
		project1 = new Project(1, "Project1", "This is the description.");
		
		ma = (MicroarraystudyAdaptor) driver.getAdaptor("MicroarraystudyAdaptor");
		
		CnSegment msegment = new CnSegment(1, "4", 1, 5000, 0.5, 100);
		
		mstudy1 = new Microarraystudy(new CnSegment[]{msegment}, "teststudy1", "D here.", 1, 1, propertyIds, 1);
		
	}
	
	public void testStoreCnSegment(){
		
		sa.storeCnSegment(segment1, 1);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 1);
		sa.storeCnSegment(segment2, 1);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 2);
		sa.storeCnSegment(segment3, 2);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 3);
		sa.storeCnSegment(segment4, 2);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 4);
		
		pra.storeProject(project1);
		
		ma.storeMicroarraystudy(mstudy1, 1);
	}
	
	public void testFetchCnSegmentById(){
		CnSegment s1 = sa.fetchCnSegmentById(1);
		CnSegment s2 = sa.fetchCnSegmentById(2);
		CnSegment s3 = sa.fetchCnSegmentById(3);
		CnSegment s4 = sa.fetchCnSegmentById(4);
		
		assertTrue(s1.getId() == 1);
		assertTrue(s1.getChromosome().equals(segment1.getChromosome()));
		assertTrue(s1.getStart() == segment1.getStart());
		assertTrue(s1.getEnd() == segment1.getEnd());
		assertTrue(s1.getMean() == segment1.getMean());
		assertTrue(s1.getNumberOfMarkers() == segment1.getNumberOfMarkers());
		assertTrue(s1.getMicroarraystudyId() == 1);
		
		assertTrue(s2.getId() == 2);
		assertTrue(s2.getChromosome().equals(segment2.getChromosome()));
		assertTrue(s2.getStart() == segment2.getStart());
		assertTrue(s2.getEnd() == segment2.getEnd());
		assertTrue(s2.getMean() == segment2.getMean());
		assertTrue(s2.getNumberOfMarkers() == segment2.getNumberOfMarkers());
		assertTrue(s2.getMicroarraystudyId() == 1);
		
		assertTrue(s3.getId() == 3);
		assertTrue(s3.getChromosome().equals(segment3.getChromosome()));
		assertTrue(s3.getStart() == segment3.getStart());
		assertTrue(s3.getEnd() == segment3.getEnd());
		assertTrue(s3.getMean() == segment3.getMean());
		assertTrue(s3.getNumberOfMarkers() == segment3.getNumberOfMarkers());
		assertTrue(s3.getMicroarraystudyId() == 2);
		
		assertTrue(s4.getId() == 4);
		assertTrue(s4.getChromosome().equals(segment4.getChromosome()));
		assertTrue(s4.getStart() == segment4.getStart());
		assertTrue(s4.getEnd() == segment4.getEnd());
		assertTrue(s4.getMean() == segment4.getMean());
		assertTrue(s4.getNumberOfMarkers() == segment4.getNumberOfMarkers());
		assertTrue(s4.getMicroarraystudyId() == 2);
	}
	
	 public void testFetchLocationForCnSegmentId(){
		 Location l1 = sa.fetchLocationForCnSegmentId(1);
		 Location l2 = sa.fetchLocationForCnSegmentId(2);
		 
		 assertTrue(l1.getChrosmome().equals(loc1.getChrosmome()));
		 assertTrue(l1.getStart() == loc1.getStart());
		 assertTrue(l1.getEnd() == loc1.getEnd());
		 
		 assertTrue(l2.getChrosmome().equals(loc2.getChrosmome()));
		 assertTrue(l2.getStart() == loc2.getStart());
		 assertTrue(l2.getEnd() == loc2.getEnd());
	 }
	
	public void testFetchCnSegmentsForMicroarraystudyId(){
		CnSegment[] segments = sa.fetchCnSegmentsForMicroarraystudyId(1);
		
		assertTrue(segments[0].getId() == segment1.getId());
		assertTrue(segments[0].getChromosome().equals(segment1.getChromosome()));
		assertTrue(segments[0].getStart() == segment1.getStart());
		assertTrue(segments[0].getEnd() == segment1.getEnd());
		
		assertTrue(segments[1].getId() == segment2.getId());
		assertTrue(segments[1].getChromosome().equals(segment2.getChromosome()));
		assertTrue(segments[1].getStart() == segment2.getStart());
		assertTrue(segments[1].getEnd() == segment2.getEnd());
	}
	 
	public void testFetchMaximalOverlappingCnSegmentRange(){
		int[] organs = new int[]{1,2};
		
		Location loc = sa.fetchMaximalOverlappingCnSegmentRange("1", 3000, 6000, null, 0.0, organs, null, null);
		
		 assertTrue(loc.getChrosmome().equals(maxOverlapLoc.getChrosmome()));
		 assertTrue(loc.getStart() == maxOverlapLoc.getStart());
		 assertTrue(loc.getEnd() == maxOverlapLoc.getEnd());
		
	}
	 
	public void testDeleteChip(){
		
		
		CnSegment s1 = sa.fetchCnSegmentById(1);
		CnSegment s2 = sa.fetchCnSegmentById(2);
		CnSegment s3 = sa.fetchCnSegmentById(3);
		CnSegment s4 = sa.fetchCnSegmentById(4);
		
		sa.deleteCnSegment(s1);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 4);
		sa.deleteCnSegment(s2);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 3);
		sa.deleteCnSegment(s3);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 2);
		sa.deleteCnSegment(s4);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 1);
		
		Microarraystudy m1 = ma.fetchMicroarraystudyById(1);
		
		ma.deleteMicroarraystudy(m1);
		
		pra.deleteProject(project1);
	}
	
	protected void tearDown() {
		((BaseAdaptor) oa).truncateTable(((BaseAdaptor) oa).getPrimaryTableName());
		((BaseAdaptor) pa).truncateTable(((BaseAdaptor) pa).getPrimaryTableName());
		
		if(((BaseAdaptor) sa).fetchCount() == 0){
			((BaseAdaptor) sa).truncateTable(((BaseAdaptor) sa).getPrimaryTableName());
			((BaseAdaptor) ma).truncateTable(((BaseAdaptor) ma).getPrimaryTableName());
			((BaseAdaptor) ma).truncateTable("sample_on_chip");
			((BaseAdaptor) pra).truncateTable("microarraystudy_in_project");
			((BaseAdaptor) pra).truncateTable(((BaseAdaptor) pra).getPrimaryTableName());
			
		}
	}
}
