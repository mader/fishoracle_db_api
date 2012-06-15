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

import de.unihamburg.zbh.fishoracle_db_api.data.CnSegment;
import de.unihamburg.zbh.fishoracle_db_api.data.Location;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.CnSegmentAdaptor;
import junit.framework.TestCase;

/**
 * @author Malte Mader
 *
 */
public class CnSegmentTest extends TestCase{

	private TestData td;
	private CnSegmentAdaptor sa;
	private CnSegment[] testsegments;
	
	protected void setUp() {
		
		td = new TestData();
		
		sa = td.getCsa();
		
		testsegments = td.createCnSegmentData();
	}
	
	public void testStoreCnSegment(){
		
		sa.storeCnSegment(testsegments[0], 1);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 1);
		sa.storeCnSegment(testsegments[1], 1);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 2);
		sa.storeCnSegment(testsegments[2], 1);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 3);
		sa.storeCnSegment(testsegments[3], 1);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 4);
		sa.storeCnSegment(testsegments[4], 2);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 5);
		sa.storeCnSegment(testsegments[5], 2);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 6);
		sa.storeCnSegment(testsegments[6], 2);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 7);
		sa.storeCnSegment(testsegments[7], 2);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 8);
		sa.storeCnSegment(testsegments[8], 3);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 9);
		sa.storeCnSegment(testsegments[9], 3);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 10);
		sa.storeCnSegment(testsegments[10], 3);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 11);
		sa.storeCnSegment(testsegments[11], 3);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 12);
	}
	
	public void testFetchCnSegmentById(){
		CnSegment s1 = sa.fetchCnSegmentById(1);
		CnSegment s2 = sa.fetchCnSegmentById(2);
		CnSegment s3 = sa.fetchCnSegmentById(3);
		
		assertTrue(s1.getId() == 1);
		assertTrue(s1.getLocation().getChromosome().equals(testsegments[0].getLocation().getChromosome()));
		assertTrue(s1.getLocation().getStart() == testsegments[0].getLocation().getStart());
		assertTrue(s1.getLocation().getEnd() == testsegments[0].getLocation().getEnd());
		assertTrue(s1.getMean() == testsegments[0].getMean());
		assertTrue(s1.getNumberOfMarkers() == testsegments[0].getNumberOfMarkers());
		assertTrue(s1.getStudyId() == 1);
		
		assertTrue(s2.getId() == 2);
		assertTrue(s2.getLocation().getChromosome().equals(testsegments[1].getLocation().getChromosome()));
		assertTrue(s2.getLocation().getStart() == testsegments[1].getLocation().getStart());
		assertTrue(s2.getLocation().getEnd() == testsegments[1].getLocation().getEnd());
		assertTrue(s2.getMean() == testsegments[1].getMean());
		assertTrue(s2.getNumberOfMarkers() == testsegments[1].getNumberOfMarkers());
		assertTrue(s2.getStudyId() == 1);
		
		assertTrue(s3.getId() == 3);
		assertTrue(s3.getLocation().getChromosome().equals(testsegments[2].getLocation().getChromosome()));
		assertTrue(s3.getLocation().getStart() == testsegments[2].getLocation().getStart());
		assertTrue(s3.getLocation().getEnd() == testsegments[2].getLocation().getEnd());
		assertTrue(s3.getMean() == testsegments[2].getMean());
		assertTrue(s3.getNumberOfMarkers() == testsegments[2].getNumberOfMarkers());
		assertTrue(s3.getStudyId() == 1);
		
	}
	
	 public void testFetchLocationForCnSegmentId(){
		 Location l1 = sa.fetchLocationForCnSegmentId(1);
		 Location l2 = sa.fetchLocationForCnSegmentId(2);
		 
		 assertTrue(l1.getChromosome().equals(testsegments[0].getLocation().getChromosome()));
		 assertTrue(l1.getStart() == testsegments[0].getLocation().getStart());
		 assertTrue(l1.getEnd() == testsegments[0].getLocation().getEnd());
		 
		 assertTrue(l2.getChromosome().equals(testsegments[1].getLocation().getChromosome()));
		 assertTrue(l2.getStart() == testsegments[1].getLocation().getStart());
		 assertTrue(l2.getEnd() == testsegments[1].getLocation().getEnd());
	 }
	
	public void testFetchCnSegmentsForStudyId(){
		CnSegment[] segments = sa.fetchCnSegmentsForStudyId(1);
		
		assertTrue(segments[0].getId() == testsegments[0].getId());
		assertTrue(segments[0].getLocation().getChromosome().equals(testsegments[0].getLocation().getChromosome()));
		assertTrue(segments[0].getLocation().getStart() == testsegments[0].getLocation().getStart());
		assertTrue(segments[0].getLocation().getEnd() == testsegments[0].getLocation().getEnd());
		
		assertTrue(segments[1].getId() == testsegments[1].getId());
		assertTrue(segments[1].getLocation().getChromosome().equals(testsegments[1].getLocation().getChromosome()));
		assertTrue(segments[1].getLocation().getStart() == testsegments[1].getLocation().getStart());
		assertTrue(segments[1].getLocation().getEnd() == testsegments[1].getLocation().getEnd());
		
		assertTrue(segments[2].getId() == testsegments[2].getId());
		assertTrue(segments[2].getLocation().getChromosome().equals(testsegments[2].getLocation().getChromosome()));
		assertTrue(segments[2].getLocation().getStart() == testsegments[2].getLocation().getStart());
		assertTrue(segments[2].getLocation().getEnd() == testsegments[2].getLocation().getEnd());
		
		assertTrue(segments[3].getId() == testsegments[3].getId());
		assertTrue(segments[3].getLocation().getChromosome().equals(testsegments[3].getLocation().getChromosome()));
		assertTrue(segments[3].getLocation().getStart() == testsegments[3].getLocation().getStart());
		assertTrue(segments[3].getLocation().getEnd() == testsegments[3].getLocation().getEnd());
		
	}
	
	public void testFetchMaximalOverlappingCnSegmentRange(){
		
		//TODO Test this in more detail...
		Location loc = sa.fetchMaximalOverlappingCnSegmentRange("1", 2000, 3000, null, 0.0, null, null, null);
		
		 assertTrue(loc.getChromosome().equals("1"));
		 assertTrue(loc.getStart() == 1);
		 assertTrue(loc.getEnd() == 4000);
		
	}
	
	public void testDeleteCnSegment(){
		
		CnSegment s1 = sa.fetchCnSegmentById(1);
		CnSegment s2 = sa.fetchCnSegmentById(2);
		CnSegment s3 = sa.fetchCnSegmentById(3);
		CnSegment s4 = sa.fetchCnSegmentById(4);
		CnSegment s5 = sa.fetchCnSegmentById(5);
		CnSegment s6 = sa.fetchCnSegmentById(6);
		CnSegment s7 = sa.fetchCnSegmentById(7);
		CnSegment s8 = sa.fetchCnSegmentById(8);
		CnSegment s9 = sa.fetchCnSegmentById(9);
		CnSegment s10 = sa.fetchCnSegmentById(10);
		CnSegment s11 = sa.fetchCnSegmentById(11);
		CnSegment s12 = sa.fetchCnSegmentById(12);
		
		sa.deleteCnSegment(s1);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 11);
		sa.deleteCnSegment(s2);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 10);
		sa.deleteCnSegment(s3);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 9);
		sa.deleteCnSegment(s4);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 8);
		sa.deleteCnSegment(s5);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 7);
		sa.deleteCnSegment(s6);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 6);
		sa.deleteCnSegment(s7);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 5);
		sa.deleteCnSegment(s8);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 4);
		sa.deleteCnSegment(s9);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 3);
		sa.deleteCnSegment(s10);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 2);
		sa.deleteCnSegment(s11);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 1);
		sa.deleteCnSegment(s12);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 0);
	}
	
	protected void tearDown() {
		
		if(((BaseAdaptor) sa).fetchCount() == 0){
			td.emptyLocationTable();
			td.emptyCnSegmentTable();
		}
	}
}