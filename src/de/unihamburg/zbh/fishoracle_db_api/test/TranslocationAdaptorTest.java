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

import de.unihamburg.zbh.fishoracle_db_api.data.Translocation;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.TranslocationAdaptor;
import junit.framework.TestCase;

public class TranslocationAdaptorTest extends TestCase {

	private TestData td;
	private TranslocationAdaptor ta;
	private Translocation[][] testTranslocs;
	
	protected void setUp() {
		
		td = new TestData();
		
		ta = td.getTa();
		
		testTranslocs = td.createTranslocationData();
	}
	
	public void testStoreTranslocation() {
		
		ta.storeTranslocation(testTranslocs[0], 1);
		assertTrue(((BaseAdaptor) ta).fetchCount() == 2);
		ta.storeTranslocation(testTranslocs[1], 2);
		assertTrue(((BaseAdaptor) ta).fetchCount() == 4);
		ta.storeTranslocation(testTranslocs[2], 3);
		assertTrue(((BaseAdaptor) ta).fetchCount() == 6);
	}
	
	public void testFetchTranslocationById() {
		Translocation[] t1 = ta.fetchTranslocationById(1);
		Translocation[] t2 = ta.fetchTranslocationById(3);
		Translocation[] t3 = ta.fetchTranslocationById(5);
		
		Translocation[][] translocs = new Translocation[][]{t1, t2, t3};
		
		for(int i = 0; i < translocs.length; i++){
			for(int j = 0; j < translocs[i].length; j++){
				assertTrue(translocs[i][j].getLocation().getChromosome().equals(testTranslocs[i][j].getLocation().getChromosome()));
				assertTrue(translocs[i][j].getLocation().getStart() == testTranslocs[i][j].getLocation().getStart());
				assertTrue(translocs[i][j].getLocation().getEnd() == testTranslocs[i][j].getLocation().getEnd());
				assertTrue(translocs[i][j].getPlatformId() == 1);
				assertTrue(translocs[i][j].getStudyId() == testTranslocs[i][j].getStudyId());
			}
		}
	}
	
	public void testFetchTranslocationsForStudyId(){
		Translocation[][] t1 = ta.fetchTranslocationsForStudyId(1);
		Translocation[][] t2 = ta.fetchTranslocationsForStudyId(2);
		Translocation[][] t3 = ta.fetchTranslocationsForStudyId(3);
		
		Translocation[][][] translocs = new Translocation[][][]{t1, t2, t3};
		
		for(int i = 0; i < translocs.length; i++){
			for(int j = 0; j < translocs[i].length; j++){
				for(int k = 0; k < translocs[j].length; k++){
				assertTrue(translocs[i][j][k].getLocation().getChromosome().equals(testTranslocs[i][j].getLocation().getChromosome()));
				assertTrue(translocs[i][j][k].getLocation().getStart() == testTranslocs[i][j].getLocation().getStart());
				assertTrue(translocs[i][j][k].getLocation().getEnd() == testTranslocs[i][j].getLocation().getEnd());
				assertTrue(translocs[i][j][k].getPlatformId() == 1);
				assertTrue(translocs[i][j][k].getStudyId() == testTranslocs[i][j].getStudyId());
				}
			}
		}
	}
	
	public void testFetchTranslocation(){
		Translocation[][] translocs;
		
		translocs = ta.fetchTranslocations("1", 0, 4000, new int[0], new int[0], new int[0]);
		
		for(int i = 0; i < translocs.length; i++){
			for(int j = 0; j < translocs[i].length; j++){
				assertTrue(translocs[i][j].getLocation().getChromosome().equals(testTranslocs[i][j].getLocation().getChromosome()));
				assertTrue(translocs[i][j].getLocation().getStart() == testTranslocs[i][j].getLocation().getStart());
				assertTrue(translocs[i][j].getLocation().getEnd() == testTranslocs[i][j].getLocation().getEnd());
				assertTrue(translocs[i][j].getPlatformId() == 1);
				assertTrue(translocs[i][j].getStudyId() == testTranslocs[i][j].getStudyId());
			}
		}
		
	}
	
	public void testDeleteTranslocation() {
		
		Translocation[] t1 = ta.fetchTranslocationById(1);
		Translocation[] t2 = ta.fetchTranslocationById(3);
		Translocation[] t3 = ta.fetchTranslocationById(5);
		
		ta.deleteTranslocation(t3);
		assertTrue(((BaseAdaptor) ta).fetchCount() == 4);
		ta.deleteTranslocation(t2);
		assertTrue(((BaseAdaptor) ta).fetchCount() == 2);
		ta.deleteTranslocation(t1);
		assertTrue(((BaseAdaptor) ta).fetchCount() == 0);
	}
	
	protected void tearDown() {
		
		if(((BaseAdaptor) ta).fetchCount() == 0){
			td.emptyTranslocationTable();
		}
	}	
}
