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

import de.unihamburg.zbh.fishoracle_db_api.data.Microarraystudy;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.MicroarraystudyAdaptor;
import junit.framework.TestCase;

public class MicroarraystudyAdaptorTest extends TestCase{

	private TestData td;
	private MicroarraystudyAdaptor ma;
	private Microarraystudy[] teststudies;;
	
	protected void setUp() {
		
		td = new TestData();
		ma = td.getMa();
		
		td.createAndStoreChipData();
		
		td.createAndStoreOrganData();
		
		td.createAndStorePropertyData();
		
		teststudies = td.createMicroarraystudyData();
	}
	
	public void testStoreMicroarraystudy(){
		
		ma.storeMicroarraystudy(teststudies[0], 1);
		assertTrue(((BaseAdaptor) ma).fetchCount() == 1);
		ma.storeMicroarraystudy(teststudies[1], 2);
		assertTrue(((BaseAdaptor) ma).fetchCount() == 2);
		ma.storeMicroarraystudy(teststudies[2], 2);
		assertTrue(((BaseAdaptor) ma).fetchCount() == 3);
		
	}
	
	public void testFetchMicroarraystudyById() {
		Microarraystudy m1 = ma.fetchMicroarraystudyById(1, true);
		Microarraystudy m2 = ma.fetchMicroarraystudyById(2, true);
		Microarraystudy m3 = ma.fetchMicroarraystudyById(3, true);
		
		assertTrue(m1.getId() == teststudies[0].getId());
		assertTrue(m1.getName().equals(teststudies[0].getName()));
		assertTrue(m1.getDescription().equals(teststudies[0].getDescription()));
		assertTrue(m1.getChip().getId() == teststudies[0].getChipId());
		assertTrue(m1.getTissue().getOrgan().getId() == teststudies[0].getOrgan_id());
		assertTrue(m1.getTissue().getProperties().length == teststudies[0].getPropertyIds().length);
		assertTrue(m1.getSegments() == null);
		
		assertTrue(m2.getId() == teststudies[1].getId());
		assertTrue(m2.getName().equals(teststudies[1].getName()));
		assertTrue(m2.getDescription().equals(teststudies[1].getDescription()));
		assertTrue(m2.getChip().getId() == teststudies[1].getChipId());
		assertTrue(m2.getTissue().getOrgan().getId() == teststudies[1].getOrgan_id());
		assertTrue(m2.getTissue().getProperties().length == teststudies[1].getPropertyIds().length);
		assertTrue(m2.getSegments() == null);
		
		assertTrue(m3.getId() == teststudies[2].getId());
		assertTrue(m3.getName().equals(teststudies[2].getName()));
		assertTrue(m3.getDescription().equals(teststudies[2].getDescription()));
		assertTrue(m3.getChip().getId() == teststudies[2].getChipId());
		assertTrue(m3.getTissue().getOrgan().getId() == teststudies[2].getOrgan_id());
		assertTrue(m3.getTissue().getProperties().length == teststudies[2].getPropertyIds().length);
		assertTrue(m3.getSegments() == null);
	}
	
	public void testFetchAllMicroarraystudies(){
		Microarraystudy[] mstudies = ma.fetchAllMicroarraystudies(true);
		
		for(int i = 0; i < mstudies.length; i++) {
			assertTrue(mstudies[i].getId() == (i+1));
			assertTrue(mstudies[i].getName().equals(teststudies[i].getName()));
			assertTrue(mstudies[i].getDescription().equals(teststudies[i].getDescription()));
			assertTrue(mstudies[i].getChip().getId() == teststudies[i].getChipId());
			assertTrue(mstudies[i].getTissue().getOrgan().getId() == teststudies[i].getOrgan_id());
			assertTrue(mstudies[i].getTissue().getProperties().length == teststudies[i].getPropertyIds().length);
			assertTrue(mstudies[i].getSegments() == null);
		}
	}
	
	public void testFetchMicroarraystudisForProject() {
		
		Microarraystudy[] mstudies1 = ma.fetchMicroarraystudiesForProject(1, true);
		Microarraystudy[] mstudies2 = ma.fetchMicroarraystudiesForProject(2, true);
		
		assertTrue(mstudies1.length == 1);
		assertTrue(mstudies1[0].getId() == teststudies[0].getId());
		assertTrue(mstudies1[0].getName().equals(teststudies[0].getName()));
		assertTrue(mstudies1[0].getDescription().equals(teststudies[0].getDescription()));
		assertTrue(mstudies1[0].getChip().getId() == teststudies[0].getChipId());
		assertTrue(mstudies1[0].getTissue().getOrgan().getId() == teststudies[0].getOrgan_id());
		assertTrue(mstudies1[0].getTissue().getProperties().length == teststudies[0].getPropertyIds().length);
		assertTrue(mstudies1[0].getSegments() == null);
		
		assertTrue(mstudies2.length == 2);
		
		assertTrue(mstudies2[0].getId() == teststudies[1].getId());
		assertTrue(mstudies2[0].getName().equals(teststudies[1].getName()));
		assertTrue(mstudies2[0].getDescription().equals(teststudies[1].getDescription()));
		assertTrue(mstudies2[0].getChip().getId() == teststudies[1].getChipId());
		assertTrue(mstudies2[0].getTissue().getOrgan().getId() == teststudies[1].getOrgan_id());
		assertTrue(mstudies2[0].getTissue().getProperties().length == teststudies[1].getPropertyIds().length);
		assertTrue(mstudies2[0].getSegments() == null);
		
		assertTrue(mstudies2[1].getId() == teststudies[2].getId());
		assertTrue(mstudies2[1].getName().equals(teststudies[2].getName()));
		assertTrue(mstudies2[1].getDescription().equals(teststudies[2].getDescription()));
		assertTrue(mstudies2[1].getChip().getId() == teststudies[2].getChipId());
		assertTrue(mstudies2[1].getTissue().getOrgan().getId() == teststudies[2].getOrgan_id());
		assertTrue(mstudies2[1].getTissue().getProperties().length == teststudies[2].getPropertyIds().length);
		assertTrue(mstudies2[1].getSegments() == null);
	}
	
	public void testDeleteMicroarraystudy(){
		Microarraystudy m1 = ma.fetchMicroarraystudyById(1, true);
		Microarraystudy m2 = ma.fetchMicroarraystudyById(2, true);
		Microarraystudy m3 = ma.fetchMicroarraystudyById(3, true);
		
		ma.deleteMicroarraystudy(m1);
		assertTrue(((BaseAdaptor) ma).fetchCount() == 2);
		ma.deleteMicroarraystudy(m2);
		assertTrue(((BaseAdaptor) ma).fetchCount() == 1);
		ma.deleteMicroarraystudy(m3);
		assertTrue(((BaseAdaptor) ma).fetchCount() == 0);
	}
	
	protected void tearDown() {
		td.emptyChipTable();
		td.emptyOrganTable();
		td.emptyPropertyTable();
		
		if(((BaseAdaptor) ma).fetchCount() == 0){
			td.emptyMstudyTable();
			td.emptySampleOnChipTable();
			td.emptyTissueSampleTable();
			td.emptyTissueSamplePropertyTable();
			td.emptyCnSegmentTable();
			td.emptyMstudyInProjectTable();
		}
	}
}