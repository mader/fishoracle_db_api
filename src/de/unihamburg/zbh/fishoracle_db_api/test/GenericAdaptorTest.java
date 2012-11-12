/*
  Copyright (c) 2012 Malte Mader <mader@zbh.uni-hamburg.de>
  Copyright (c) 2012 Center for Bioinformatics, University of Hamburg

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

import de.unihamburg.zbh.fishoracle_db_api.data.GenericFeature;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.GenericAdaptor;
import junit.framework.TestCase;

public class GenericAdaptorTest extends TestCase {

	private TestData td;
	private GenericAdaptor gfa;
	private GenericFeature[] testFs;
	
	protected void setUp() {
		
		td = new TestData();
		
		gfa = td.getGfa();
		
		testFs = td.createGenericFeatureData();
	}
	
	public void testStoreGenericFeature() {
		
		gfa.storeGenericFeature(testFs[0], testFs[0].getStudyId());
		assertTrue(((BaseAdaptor) gfa).fetchCount() == 1);
		gfa.storeGenericFeature(testFs[1], testFs[1].getStudyId());
		assertTrue(((BaseAdaptor) gfa).fetchCount() == 2);
		gfa.storeGenericFeature(testFs[2], testFs[2].getStudyId());
		assertTrue(((BaseAdaptor) gfa).fetchCount() == 3);
	}
	
	public void testFetchGenericFeatureById() {
		GenericFeature f1 = gfa.fetchGenericFeatureById(1);
		GenericFeature f2 = gfa.fetchGenericFeatureById(2);
		GenericFeature f3 = gfa.fetchGenericFeatureById(3);
		
		GenericFeature[] fs = new GenericFeature[]{f1, f2, f3};
		
		for(int i = 0; i < fs.length; i++){
			
			assertTrue(fs[i].getId() == i + 1);
			assertTrue(fs[i].getLocation().getChromosome().equals(testFs[i].getLocation().getChromosome()));
			assertTrue(fs[i].getLocation().getStart() == testFs[i].getLocation().getStart());
			assertTrue(fs[i].getLocation().getEnd() == testFs[i].getLocation().getEnd());
			assertTrue(fs[i].getType().equals(testFs[i].getType()));
			assertTrue(fs[i].getPlatformId() == 1);
			assertTrue(fs[i].getStudyId() == testFs[i].getStudyId());
		}
	}
	
	public void testFetchGenericFeatureForStudyId(){
		GenericFeature[] f1 = gfa.fetchGenericFeaturesForStudyId(1);
		GenericFeature[] f2 = gfa.fetchGenericFeaturesForStudyId(2);
		GenericFeature[] f3 = gfa.fetchGenericFeaturesForStudyId(3);
		
		GenericFeature[] fs = new GenericFeature[]{f1[0], f2[0], f3[0]};
		
		for(int i = 0; i < fs.length; i++){
			
			assertTrue(fs[i].getId() == i + 1);
			assertTrue(fs[i].getLocation().getChromosome().equals(testFs[i].getLocation().getChromosome()));
			assertTrue(fs[i].getLocation().getStart() == testFs[i].getLocation().getStart());
			assertTrue(fs[i].getLocation().getEnd() == testFs[i].getLocation().getEnd());
			assertTrue(fs[i].getType().equals(testFs[i].getType()));
			assertTrue(fs[i].getPlatformId() == 1);
			assertTrue(fs[i].getStudyId() == testFs[i].getStudyId());
		}	
	}
	
	public void testFetchGenericFeature(){
		
		//TODO Test this in more detail...
		GenericFeature[] fs;
		
		fs = gfa.fetchGenericFeatures("1", 0, 4000, new String[]{"Methylation"}, new int[0], new int[0], new int[0]);
			
		assertTrue(fs[0].getId() == 1);
		assertTrue(fs[0].getLocation().getChromosome().equals(testFs[0].getLocation().getChromosome()));
		assertTrue(fs[0].getLocation().getStart() == testFs[0].getLocation().getStart());
		assertTrue(fs[0].getLocation().getEnd() == testFs[0].getLocation().getEnd());
		assertTrue(fs[0].getType().equals(testFs[0].getType()));
		assertTrue(fs[0].getPlatformId() == 1);
		assertTrue(fs[0].getStudyId() == testFs[0].getStudyId());
		
	}
	
	public void testFetchAllTypes() {
		String[] types = gfa.fetchAllTypes();
		
		assertTrue(types[0].equals("Methylation"));
		assertTrue(types[1].equals("myAnnotation"));
		assertTrue(types[2].equals("whatever"));
		
	}
	
	public void testDeleteGenericFeature() {
		
		GenericFeature f1 = gfa.fetchGenericFeatureById(1);
		GenericFeature f2 = gfa.fetchGenericFeatureById(2);
		GenericFeature f3 = gfa.fetchGenericFeatureById(3);
		
		gfa.deleteGenericFeature(f3);
		assertTrue(((BaseAdaptor) gfa).fetchCount() == 2);
		gfa.deleteGenericFeature(f2);
		assertTrue(((BaseAdaptor) gfa).fetchCount() == 1);
		gfa.deleteGenericFeature(f1);
		assertTrue(((BaseAdaptor) gfa).fetchCount() == 0);
	}
	
	protected void tearDown() {
		
		if(((BaseAdaptor) gfa).fetchCount() == 0){
			td.emptyLocationTable();
			td.emptyFeatureTable();
		}
	}
	
}
