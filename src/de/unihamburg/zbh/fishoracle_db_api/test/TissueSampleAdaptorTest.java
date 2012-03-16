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

import de.unihamburg.zbh.fishoracle_db_api.data.TissueSample;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.TissueSampleAdaptor;
import junit.framework.TestCase;

/**
 * @author Malte Mader
 *
 */
public class TissueSampleAdaptorTest extends TestCase{

	private TestData td;
	private TissueSampleAdaptor ta;
	private TissueSample[] testtissues;
	
	protected void setUp() {
		
		td = new TestData();
		
		td.createAndStoreOrganData();
		
		td.createAndStorePropertyData();
		
		testtissues = td.createTissueSampleData();
		
		ta = td.getTa();
	}
	
	public void testStoreTissueSample() {
		ta.storeTissueSample(testtissues[0]);
		assertTrue(((BaseAdaptor) ta).fetchCount() == 1);
		ta.storeTissueSample(testtissues[1]);
		assertTrue(((BaseAdaptor) ta).fetchCount() == 2);
		ta.storeTissueSample(testtissues[2]);
		assertTrue(((BaseAdaptor) ta).fetchCount() == 3);
	}
	
	
	public void testFetchTissueById() {
		TissueSample t1 = ta.fetchTissueSampleById(1);
		TissueSample t2 = ta.fetchTissueSampleById(2);
		TissueSample t3 = ta.fetchTissueSampleById(3, false);
		
		assertTrue(t1.getId() == testtissues[0].getId());
		assertTrue(t1.getOrgan().getId() == testtissues[0].getOrgan().getId());
		for(int i = 0; i < t1.getProperties().length; i++) {
			assertTrue(t1.getProperties()[i].getId() == testtissues[0].getProperties()[i].getId());
		}
		
		assertTrue(t2.getId() == testtissues[1].getId());
		assertTrue(t2.getOrgan().getId() == testtissues[1].getOrgan().getId());
		for(int i = 0; i < t2.getProperties().length; i++) {
			assertTrue(t2.getProperties()[i].getId() == testtissues[1].getProperties()[i].getId());
		}
		
		assertTrue(t3.getId() == testtissues[2].getId());
		assertTrue(t3.getOrgan() == null);
		assertTrue(t3.getProperties().length == 0);
		
	}
	
	public void testDeleteOrgan() {
		TissueSample t1 = ta.fetchTissueSampleById(1);
		TissueSample t2 = ta.fetchTissueSampleById(2);
		TissueSample t3 = ta.fetchTissueSampleById(3);
		
		ta.deleteTissueSample(t1);
		assertTrue(((BaseAdaptor) ta).fetchCount() == 2);
		ta.deleteTissueSample(t2);
		assertTrue(((BaseAdaptor) ta).fetchCount() == 1);
		ta.deleteTissueSample(t3);
		assertTrue(((BaseAdaptor) ta).fetchCount() == 0);
	}
	
	protected void tearDown() {
		td.emptyOrganTable();
		td.emptyPropertyTable();
		if(((BaseAdaptor) ta).fetchCount() == 0){
			td.emptyTissueSampleTable();
			td.emptyTissueSamplePropertyTable();
		}
	}
}