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

import de.unihamburg.zbh.fishoracle_db_api.data.Study;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.StudyAdaptor;
import junit.framework.TestCase;

public class StudyAdaptorTest extends TestCase{

	private TestData td;
	private StudyAdaptor sa;
	private Study[] teststudies;;
	
	protected void setUp() {
		
		td = new TestData();
		sa = td.getSa();
		
		td.createAndStorePlatformData();
		
		td.createAndStoreOrganData();
		
		td.createAndStorePropertyData();
		
		teststudies = td.createStudyData();
	}
	
	public void testStoreStudy(){
		
		sa.storeStudy(teststudies[0], 1);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 1);
		sa.storeStudy(teststudies[1], 2);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 2);
		sa.storeStudy(teststudies[2], 2);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 3);
		
	}
	
	public void testFetchStudyById() {
		Study s1 = sa.fetchStudyById(1, true);
		Study s2 = sa.fetchStudyById(2, true);
		Study s3 = sa.fetchStudyById(3, true);
		
		assertTrue(s1.getId() == teststudies[0].getId());
		assertTrue(s1.getName().equals(teststudies[0].getName()));
		assertTrue(s1.getAssembly().equals(teststudies[0].getAssembly()));
		assertTrue(s1.getDescription().equals(teststudies[0].getDescription()));
		assertTrue(s1.getTissue().getOrgan().getId() == teststudies[0].getOrganId());
		assertTrue(s1.getTissue().getProperties().length == teststudies[0].getPropertyIds().length);
		assertTrue(s1.getSegments() == null);
		
		assertTrue(s2.getId() == teststudies[1].getId());
		assertTrue(s2.getName().equals(teststudies[1].getName()));
		assertTrue(s2.getAssembly().equals(teststudies[1].getAssembly()));
		assertTrue(s2.getDescription().equals(teststudies[1].getDescription()));
		assertTrue(s2.getTissue().getOrgan().getId() == teststudies[1].getOrganId());
		assertTrue(s2.getTissue().getProperties().length == teststudies[1].getPropertyIds().length);
		assertTrue(s2.getSegments() == null);
		
		assertTrue(s3.getId() == teststudies[2].getId());
		assertTrue(s3.getName().equals(teststudies[2].getName()));
		assertTrue(s3.getAssembly().equals(teststudies[2].getAssembly()));
		assertTrue(s3.getDescription().equals(teststudies[2].getDescription()));
		assertTrue(s3.getTissue().getOrgan().getId() == teststudies[2].getOrganId());
		assertTrue(s3.getTissue().getProperties().length == teststudies[2].getPropertyIds().length);
		assertTrue(s3.getSegments() == null);
	}
	
	public void testFetchStudyForName() {
		Study s1 = sa.fetchStudyForName("teststudy1", false);
		Study s2 = sa.fetchStudyForName("teststudy2", false);
		Study s3 = sa.fetchStudyForName("teststudy3", false);
		
		Study[] studies = new Study[]{s1, s2, s3};
		
		for(int i = 0; i < studies.length; i++) {
			assertTrue(studies[i].getId() == (i+1));
			assertTrue(studies[i].getName().equals(teststudies[i].getName()));
			assertTrue(studies[i].getAssembly().equals(teststudies[i].getAssembly()));
			assertTrue(studies[i].getDescription().equals(teststudies[i].getDescription()));
			assertTrue(studies[i].getSegments() == null);
		}	
	}
	
	public void testFetchAllStudies(){
		Study[] studies = sa.fetchAllStudies(true);
		
		for(int i = 0; i < studies.length; i++) {
			assertTrue(studies[i].getId() == (i+1));
			assertTrue(studies[i].getName().equals(teststudies[i].getName()));
			assertTrue(studies[i].getAssembly().equals(teststudies[i].getAssembly()));
			assertTrue(studies[i].getDescription().equals(teststudies[i].getDescription()));
			assertTrue(studies[i].getTissue().getOrgan().getId() == teststudies[i].getOrganId());
			assertTrue(studies[i].getTissue().getProperties().length == teststudies[i].getPropertyIds().length);
			assertTrue(studies[i].getSegments() == null);
		}
	}
	
	public void testFetchStudiesForProject() {
		
		Study[] studies1 = sa.fetchStudiesForProject(1, true);
		Study[] studies2 = sa.fetchStudiesForProject(2, true);
		
		assertTrue(studies1.length == 1);
		assertTrue(studies1[0].getId() == teststudies[0].getId());
		assertTrue(studies1[0].getName().equals(teststudies[0].getName()));
		assertTrue(studies1[0].getAssembly().equals(teststudies[0].getAssembly()));
		assertTrue(studies1[0].getDescription().equals(teststudies[0].getDescription()));
		assertTrue(studies1[0].getTissue().getOrgan().getId() == teststudies[0].getOrganId());
		assertTrue(studies1[0].getTissue().getProperties().length == teststudies[0].getPropertyIds().length);
		assertTrue(studies1[0].getSegments() == null);
		
		assertTrue(studies2.length == 2);
		
		assertTrue(studies2[0].getId() == teststudies[1].getId());
		assertTrue(studies2[0].getName().equals(teststudies[1].getName()));
		assertTrue(studies2[0].getAssembly().equals(teststudies[1].getAssembly()));
		assertTrue(studies2[0].getDescription().equals(teststudies[1].getDescription()));
		assertTrue(studies2[0].getTissue().getOrgan().getId() == teststudies[1].getOrganId());
		assertTrue(studies2[0].getTissue().getProperties().length == teststudies[1].getPropertyIds().length);
		assertTrue(studies2[0].getSegments() == null);
		
		assertTrue(studies2[1].getId() == teststudies[2].getId());
		assertTrue(studies2[1].getName().equals(teststudies[2].getName()));
		assertTrue(studies2[1].getAssembly().equals(teststudies[2].getAssembly()));
		assertTrue(studies2[1].getDescription().equals(teststudies[2].getDescription()));
		assertTrue(studies2[1].getTissue().getOrgan().getId() == teststudies[2].getOrganId());
		assertTrue(studies2[1].getTissue().getProperties().length == teststudies[2].getPropertyIds().length);
		assertTrue(studies2[1].getSegments() == null);
	}
	
	public void testDeleteStudy(){
		Study s1 = sa.fetchStudyById(1, true);
		Study s2 = sa.fetchStudyById(2, true);
		Study s3 = sa.fetchStudyById(3, true);
		
		sa.deleteStudy(s1);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 2);
		sa.deleteStudy(s2);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 1);
		sa.deleteStudy(s3);
		assertTrue(((BaseAdaptor) sa).fetchCount() == 0);
	}
	
	protected void tearDown() {
		td.emptyPlatformTable();
		td.emptyOrganTable();
		td.emptyPropertyTable();
		
		if(((BaseAdaptor) sa).fetchCount() == 0){
			td.emptyStudyTable();
			td.emptyTissueSampleTable();
			td.emptyTissueSamplePropertyTable();
			td.emptyCnSegmentTable();
			td.emptyLocationTable();
			td.emptyStudyInProjectTable();
		}
	}
}