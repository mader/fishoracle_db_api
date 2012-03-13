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

import de.unihamburg.zbh.fishoracle_db_api.data.Chip;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.ChipAdaptor;
import junit.framework.TestCase;

/**
 * @author Malte Mader
 *
 */
public class ChipAdaptorTest extends TestCase {
	private TestData td;
	private ChipAdaptor ca;
	private Chip[] testChips = new Chip[3];
	
	protected void setUp() {
		
		td = new TestData();
		
		ca = td.getCa();
		
		testChips = td.createChipData();
	}
	
	public void testStoreChip(){
		
		ca.storeChip(testChips[0]);
		assertTrue(((BaseAdaptor) ca).fetchCount() == 1);
		ca.storeChip(testChips[1]);
		assertTrue(((BaseAdaptor) ca).fetchCount() == 2);
		ca.storeChip(testChips[2]);
		assertTrue(((BaseAdaptor) ca).fetchCount() == 3);
	}
	
	public void testFetchAllChips(){
		
		Chip[] chips = ca.fetchAllChips();
		
		for(int i = 0; i < chips.length; i++){
			assertTrue(chips[i].getId() == i + 1);
			assertTrue(chips[i].getName().equals(testChips[i].getName()));
			assertTrue(chips[i].getType().equals(testChips[i].getType()));
		}
	}
	
	public void testfetchAllTypes(){
		
		String[] types = ca.fetchAllTypes();
		
		assertTrue(types[0].equals("expression"));
		assertTrue(types[1].equals("snp"));
	}
	
	public void testFetchChipById(){
		Chip c1 = ca.fetchChipById(1);
		Chip c2 = ca.fetchChipById(2);
		Chip c3 = ca.fetchChipById(3);
	
		Chip[] chips = new Chip[]{c1, c2, c3};
		
		for(int i = 0; i < chips.length; i++){
			assertTrue(chips[i].getId() == i + 1);
			assertTrue(chips[i].getName().equals(testChips[i].getName()));
			assertTrue(chips[i].getType().equals(testChips[i].getType()));
		}
	}
	
	public void testDeleteChip(){
		Chip c1 = ca.fetchChipById(1);
		Chip c2 = ca.fetchChipById(2);
		Chip c3 = ca.fetchChipById(3);
		
		ca.deleteChip(c1);
		assertTrue(((BaseAdaptor) ca).fetchCount() == 2);
		ca.deleteChip(c2);
		assertTrue(((BaseAdaptor) ca).fetchCount() == 1);
		ca.deleteChip(c3.getId());
		assertTrue(((BaseAdaptor) ca).fetchCount() == 0);
	}
	
	protected void tearDown() {
		if(((BaseAdaptor) ca).fetchCount() == 0){
			td.emptyChipTable();
		}
	}
}
