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

import de.unihamburg.zbh.fishoracle_db_api.data.SNPMutation;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.SNPMutationAdaptor;
import junit.framework.TestCase;

public class SNPMutationAdaptorTest extends TestCase {

	private TestData td;
	private SNPMutationAdaptor ma;
	private SNPMutation[] testMuts;
	
	protected void setUp() {
		
		td = new TestData();
		
		ma = td.getMa();
		
		testMuts = td.createSNPMutationData();
	}
	
	public void testStoreSNPMutation() {
		
		ma.storeSNPMutation(testMuts[0], testMuts[0].getStudyId());
		assertTrue(((BaseAdaptor) ma).fetchCount() == 1);
		ma.storeSNPMutation(testMuts[1], testMuts[1].getStudyId());
		assertTrue(((BaseAdaptor) ma).fetchCount() == 2);
		ma.storeSNPMutation(testMuts[2], testMuts[2].getStudyId());
		assertTrue(((BaseAdaptor) ma).fetchCount() == 3);
		ma.storeSNPMutation(testMuts[3], testMuts[3].getStudyId());
		assertTrue(((BaseAdaptor) ma).fetchCount() == 4);
	}
	
	public void testFetchSNPMutationById() {
		SNPMutation m1 = ma.fetchSNPMutationById(1);
		SNPMutation m2 = ma.fetchSNPMutationById(2);
		SNPMutation m3 = ma.fetchSNPMutationById(3);
		SNPMutation m4 = ma.fetchSNPMutationById(4);
		
		SNPMutation[] snpMuts = new SNPMutation[]{m1, m2, m3, m4};
		
		for(int i = 0; i < snpMuts.length; i++){
			
			assertTrue(snpMuts[i].getId() == i + 1);
			assertTrue(snpMuts[i].getLocation().getChromosome().equals(testMuts[i].getLocation().getChromosome()));
			assertTrue(snpMuts[i].getLocation().getStart() == testMuts[i].getLocation().getStart());
			assertTrue(snpMuts[i].getLocation().getEnd() == testMuts[i].getLocation().getEnd());
			assertTrue(snpMuts[i].getDbSnpId().equals(testMuts[i].getDbSnpId()));
			assertTrue(snpMuts[i].getRef().equals(testMuts[i].getRef()));
			assertTrue(snpMuts[i].getAlt().equals(testMuts[i].getAlt()));
			assertTrue(snpMuts[i].getQuality() == testMuts[i].getQuality());
			assertTrue(snpMuts[i].getSomatic().equals(testMuts[i].getSomatic()));
			assertTrue(snpMuts[i].getConfidence().equals(testMuts[i].getConfidence()));
			assertTrue(snpMuts[i].getSnpTool().equals(testMuts[i].getSnpTool()));
			assertTrue(snpMuts[i].getStudyId() == testMuts[i].getStudyId());
		}
	}
	
	public void testFetchCnSegmentsForStudyId(){
		SNPMutation[] m1 = ma.fetchSNPMutationsForStudyId(1);
		SNPMutation[] m2 = ma.fetchSNPMutationsForStudyId(2);
		SNPMutation[] m3 = ma.fetchSNPMutationsForStudyId(3);
		SNPMutation[] m4 = ma.fetchSNPMutationsForStudyId(4);
		
		SNPMutation[] snpMuts = new SNPMutation[]{m1[0], m2[0], m3[0], m4[0]};
		
		for(int i = 0; i < snpMuts.length; i++){
			
			assertTrue(snpMuts[i].getId() == i + 1);
			assertTrue(snpMuts[i].getLocation().getChromosome().equals(testMuts[i].getLocation().getChromosome()));
			assertTrue(snpMuts[i].getLocation().getStart() == testMuts[i].getLocation().getStart());
			assertTrue(snpMuts[i].getLocation().getEnd() == testMuts[i].getLocation().getEnd());
			assertTrue(snpMuts[i].getDbSnpId().equals(testMuts[i].getDbSnpId()));
			assertTrue(snpMuts[i].getRef().equals(testMuts[i].getRef()));
			assertTrue(snpMuts[i].getAlt().equals(testMuts[i].getAlt()));
			assertTrue(snpMuts[i].getQuality() == testMuts[i].getQuality());
			assertTrue(snpMuts[i].getSomatic().equals(testMuts[i].getSomatic()));
			assertTrue(snpMuts[i].getConfidence().equals(testMuts[i].getConfidence()));
			assertTrue(snpMuts[i].getSnpTool().equals(testMuts[i].getSnpTool()));
			assertTrue(snpMuts[i].getStudyId() == testMuts[i].getStudyId());
		}	
	}
	
	public void testFetchSNPMutation(){
		
		//TODO Test this in more detail...
		SNPMutation[] snpMuts;
		
		snpMuts = ma.fetchSNPMutations("1", 0, 4000, 20.0, null, null, null, new int[0], new int[0], new int[0]);
		
		for(int i = 0; i < snpMuts.length; i++){
			
			assertTrue(snpMuts[i].getId() == i + 1);
			assertTrue(snpMuts[i].getLocation().getChromosome().equals(testMuts[i].getLocation().getChromosome()));
			assertTrue(snpMuts[i].getLocation().getStart() == testMuts[i].getLocation().getStart());
			assertTrue(snpMuts[i].getLocation().getEnd() == testMuts[i].getLocation().getEnd());
			assertTrue(snpMuts[i].getDbSnpId().equals(testMuts[i].getDbSnpId()));
			assertTrue(snpMuts[i].getRef().equals(testMuts[i].getRef()));
			assertTrue(snpMuts[i].getAlt().equals(testMuts[i].getAlt()));
			assertTrue(snpMuts[i].getQuality() == testMuts[i].getQuality());
			assertTrue(snpMuts[i].getSomatic().equals(testMuts[i].getSomatic()));
			assertTrue(snpMuts[i].getConfidence().equals(testMuts[i].getConfidence()));
			assertTrue(snpMuts[i].getSnpTool().equals(testMuts[i].getSnpTool()));
			assertTrue(snpMuts[i].getStudyId() == testMuts[i].getStudyId());
		}	
		
	}
	
	public void testDeleteSNPMutation() {
		
		SNPMutation m1 = ma.fetchSNPMutationById(1);
		SNPMutation m2 = ma.fetchSNPMutationById(2);
		SNPMutation m3 = ma.fetchSNPMutationById(3);
		SNPMutation m4 = ma.fetchSNPMutationById(4);
		
		ma.deleteSNPMutation(m4);
		assertTrue(((BaseAdaptor) ma).fetchCount() == 3);
		ma.deleteSNPMutation(m3);
		assertTrue(((BaseAdaptor) ma).fetchCount() == 2);
		ma.deleteSNPMutation(m2);
		assertTrue(((BaseAdaptor) ma).fetchCount() == 1);
		ma.deleteSNPMutation(m1);
		assertTrue(((BaseAdaptor) ma).fetchCount() == 0);
	}
	
	protected void tearDown() {
		
		if(((BaseAdaptor) ma).fetchCount() == 0){
			td.emptyLocationTable();
			td.emptySNPMutationTable();
		}
	}
}