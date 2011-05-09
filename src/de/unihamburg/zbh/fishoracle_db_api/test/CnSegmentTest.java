package de.unihamburg.zbh.fishoracle_db_api.test;

import de.unihamburg.zbh.fishoracle_db_api.data.CnSegment;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.CnSegmentAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriver;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriverImpl;
import junit.framework.TestCase;

public class CnSegmentTest extends TestCase{

	private FODriver driver;
	private CnSegmentAdaptor sa;
	private CnSegment segment1, segment2, segment3, segment4;
	private CnSegment[] testsegments = new CnSegment[4];
	
	protected void setUp() {
		driver = new FODriverImpl("localhost", "emptyoracle", "fouser", "fish4me", "3306");
		sa = (CnSegmentAdaptor) driver.getAdaptor("CnSegmentAdaptor");
		
		segment1 = new CnSegment(1, "1", 1, 5000, 0.5, 100, 1);
		segment2 = new CnSegment(2, "1", 5001, 8000, 0.7, 300, 1);
		segment3 = new CnSegment(3, "2", 1, 2000, 0.3, 600, 2);
		segment4 = new CnSegment(4, "3", 1, 1000, 1.2, 250, 2);
		
		testsegments[0] = segment1;
		testsegments[1] = segment2;
		testsegments[2] = segment3;
		testsegments[3] = segment4;
	}
	
	public void testStoreCnSegment(){
		
		sa.storeCnSegment(segment1);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 1);
		sa.storeCnSegment(segment2);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 2);
		sa.storeCnSegment(segment3);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 3);
		sa.storeCnSegment(segment4);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 4);
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
		assertTrue(s1.getMicroarraystudyId() == segment1.getMicroarraystudyId());
		
		assertTrue(s2.getId() == 2);
		assertTrue(s2.getChromosome().equals(segment2.getChromosome()));
		assertTrue(s2.getStart() == segment2.getStart());
		assertTrue(s2.getEnd() == segment2.getEnd());
		assertTrue(s2.getMean() == segment2.getMean());
		assertTrue(s2.getNumberOfMarkers() == segment2.getNumberOfMarkers());
		assertTrue(s2.getMicroarraystudyId() == segment2.getMicroarraystudyId());
		
		assertTrue(s3.getId() == 3);
		assertTrue(s3.getChromosome().equals(segment3.getChromosome()));
		assertTrue(s3.getStart() == segment3.getStart());
		assertTrue(s3.getEnd() == segment3.getEnd());
		assertTrue(s3.getMean() == segment3.getMean());
		assertTrue(s3.getNumberOfMarkers() == segment3.getNumberOfMarkers());
		assertTrue(s3.getMicroarraystudyId() == segment3.getMicroarraystudyId());
		
		assertTrue(s4.getId() == 4);
		assertTrue(s4.getChromosome().equals(segment4.getChromosome()));
		assertTrue(s4.getStart() == segment4.getStart());
		assertTrue(s4.getEnd() == segment4.getEnd());
		assertTrue(s4.getMean() == segment4.getMean());
		assertTrue(s4.getNumberOfMarkers() == segment4.getNumberOfMarkers());
		assertTrue(s4.getMicroarraystudyId() == segment4.getMicroarraystudyId());
	}
	
	public void testDeleteChip(){
		CnSegment s1 = sa.fetchCnSegmentById(1);
		CnSegment s2 = sa.fetchCnSegmentById(2);
		CnSegment s3 = sa.fetchCnSegmentById(3);
		CnSegment s4 = sa.fetchCnSegmentById(4);
		
		sa.deleteCnSegment(s1);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 3);
		sa.deleteCnSegment(s2);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 2);
		sa.deleteCnSegment(s3);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 1);
		sa.deleteCnSegment(s4);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 0);
		
	}
	
	protected void tearDown() {
		if(((BaseAdaptor) sa).fetchCount() == 0){
			((BaseAdaptor) sa).truncateTable(((BaseAdaptor) sa).getPrimaryTableName());
		}
	}
}
