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

import de.unihamburg.zbh.fishoracle_db_api.data.Segment;
import de.unihamburg.zbh.fishoracle_db_api.data.Location;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.SegmentAdaptor;
import junit.framework.TestCase;

/**
 * @author Malte Mader
 *
 */
public class SegmentTest extends TestCase{

	private TestData td;
	private SegmentAdaptor sa;
	private Segment[] testsegments;
	
	protected void setUp() {
		
		td = new TestData();
		
		sa = td.getCsa();
		
		testsegments = td.createSegmentData();
	}
	
	public void testStoreSegment(){
		
		sa.storeSegment(testsegments[0], 1);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 1);
		sa.storeSegment(testsegments[1], 1);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 2);
		sa.storeSegment(testsegments[2], 1);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 3);
		sa.storeSegment(testsegments[3], 1);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 4);
		sa.storeSegment(testsegments[4], 2);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 5);
		sa.storeSegment(testsegments[5], 2);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 6);
		sa.storeSegment(testsegments[6], 2);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 7);
		sa.storeSegment(testsegments[7], 2);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 8);
		sa.storeSegment(testsegments[8], 3);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 9);
		sa.storeSegment(testsegments[9], 3);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 10);
		sa.storeSegment(testsegments[10], 3);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 11);
		sa.storeSegment(testsegments[11], 3);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 12);
		sa.storeSegment(testsegments[12], 3);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 13);
		sa.storeSegment(testsegments[13], 3);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 14);
		
	}
	
	public void testFetchSegmentById(){
		Segment s1 = sa.fetchSegmentById(1);
		Segment s2 = sa.fetchSegmentById(2);
		Segment s3 = sa.fetchSegmentById(3);
		
		assertTrue(s1.getId() == 1);
		assertTrue(s1.getLocation().getChromosome().equals(testsegments[0].getLocation().getChromosome()));
		assertTrue(s1.getLocation().getStart() == testsegments[0].getLocation().getStart());
		assertTrue(s1.getLocation().getEnd() == testsegments[0].getLocation().getEnd());
		assertTrue(s1.getMean() == testsegments[0].getMean());
		assertTrue(s1.getNumberOfMarkers() == testsegments[0].getNumberOfMarkers());
		assertTrue(s1.getType().equals(testsegments[0].getType()));
		assertTrue(s1.getPlatformId() == testsegments[0].getPlatformId());
		assertTrue(s1.getStudyId() == 1);
		
		assertTrue(s2.getId() == 2);
		assertTrue(s2.getLocation().getChromosome().equals(testsegments[1].getLocation().getChromosome()));
		assertTrue(s2.getLocation().getStart() == testsegments[1].getLocation().getStart());
		assertTrue(s2.getLocation().getEnd() == testsegments[1].getLocation().getEnd());
		assertTrue(s2.getMean() == testsegments[1].getMean());
		assertTrue(s2.getNumberOfMarkers() == testsegments[1].getNumberOfMarkers());
		assertTrue(s2.getType().equals(testsegments[1].getType()));
		assertTrue(s2.getPlatformId() == testsegments[1].getPlatformId());
		assertTrue(s2.getStudyId() == 1);
		
		assertTrue(s3.getId() == 3);
		assertTrue(s3.getLocation().getChromosome().equals(testsegments[2].getLocation().getChromosome()));
		assertTrue(s3.getLocation().getStart() == testsegments[2].getLocation().getStart());
		assertTrue(s3.getLocation().getEnd() == testsegments[2].getLocation().getEnd());
		assertTrue(s3.getMean() == testsegments[2].getMean());
		assertTrue(s3.getNumberOfMarkers() == testsegments[2].getNumberOfMarkers());
		assertTrue(s3.getType().equals(testsegments[2].getType()));
		assertTrue(s3.getPlatformId() == testsegments[2].getPlatformId());
		assertTrue(s3.getStudyId() == 1);
		
		Segment s14 = sa.fetchSegmentById(14);
		
		assertTrue(s14.getLocation().getChromosome().equals(testsegments[13].getLocation().getChromosome()));
		assertTrue(s14.getLocation().getStart() == testsegments[13].getLocation().getStart());
		assertTrue(s14.getLocation().getEnd() == testsegments[13].getLocation().getEnd());
		assertTrue(s14.getStatus() == testsegments[13].getStatus());
		assertTrue(s14.getStatusScore() == testsegments[13].getStatusScore());
		assertTrue(s14.getType().equals(testsegments[13].getType()));
		assertTrue(s14.getPlatformId() == testsegments[13].getPlatformId());
		assertTrue(s14.getStudyId() == 3);
		
	}
	
	 public void testFetchLocationForSegmentId(){
		 Location l1 = sa.fetchLocationForSegmentId(1);
		 Location l2 = sa.fetchLocationForSegmentId(2);
		 
		 assertTrue(l1.getChromosome().equals(testsegments[0].getLocation().getChromosome()));
		 assertTrue(l1.getStart() == testsegments[0].getLocation().getStart());
		 assertTrue(l1.getEnd() == testsegments[0].getLocation().getEnd());
		 
		 assertTrue(l2.getChromosome().equals(testsegments[1].getLocation().getChromosome()));
		 assertTrue(l2.getStart() == testsegments[1].getLocation().getStart());
		 assertTrue(l2.getEnd() == testsegments[1].getLocation().getEnd());
	 }
	
	public void testFetchSegmentsForStudyId(){
		Segment[] segments = sa.fetchSegmentsForStudyId(1);
		
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
	
	public void testFetchMaximalOverlappingSegmentRange(){
		
		//TODO Test this in more detail...
		Location loc = sa.fetchMaximalOverlappingSegmentRange("1", 2000, 3000, null, 0.0, null, null, null);
		
		 assertTrue(loc.getChromosome().equals("1"));
		 assertTrue(loc.getStart() == 1);
		 assertTrue(loc.getEnd() == 4000);
		
	}
	
	public void testDeleteSegment(){
		
		Segment s1 = sa.fetchSegmentById(1);
		Segment s2 = sa.fetchSegmentById(2);
		Segment s3 = sa.fetchSegmentById(3);
		Segment s4 = sa.fetchSegmentById(4);
		Segment s5 = sa.fetchSegmentById(5);
		Segment s6 = sa.fetchSegmentById(6);
		Segment s7 = sa.fetchSegmentById(7);
		Segment s8 = sa.fetchSegmentById(8);
		Segment s9 = sa.fetchSegmentById(9);
		Segment s10 = sa.fetchSegmentById(10);
		Segment s11 = sa.fetchSegmentById(11);
		Segment s12 = sa.fetchSegmentById(12);
		Segment s13 = sa.fetchSegmentById(13);
		Segment s14 = sa.fetchSegmentById(14);
		
		sa.deleteSegment(s1);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 13);
		sa.deleteSegment(s2);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 12);
		sa.deleteSegment(s3);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 11);
		sa.deleteSegment(s4);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 10);
		sa.deleteSegment(s5);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 9);
		sa.deleteSegment(s6);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 8);
		sa.deleteSegment(s7);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 7);
		sa.deleteSegment(s8);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 6);
		sa.deleteSegment(s9);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 5);
		sa.deleteSegment(s10);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 4);
		sa.deleteSegment(s11);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 3);
		sa.deleteSegment(s12);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 2);
		sa.deleteSegment(s13);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 1);
		sa.deleteSegment(s14);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 0);
	}
	
	protected void tearDown() {
		
		if(((BaseAdaptor) sa).fetchCount() == 0){
			td.emptyLocationTable();
			td.emptyCnSegmentTable();
		}
	}
}