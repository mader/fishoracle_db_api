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

import de.unihamburg.zbh.fishoracle_db_api.data.Platform;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.PlatformAdaptor;
import junit.framework.TestCase;

/**
 * @author Malte Mader
 *
 */
public class PlatformAdaptorTest extends TestCase {
	private TestData td;
	private PlatformAdaptor pfa;
	private Platform[] testPlatforms;
	
	protected void setUp() {
		
		td = new TestData();
		
		pfa = td.getPfa();
		
		testPlatforms = td.createPlatformData();
	}
	
	public void testStorePlatform(){
		
		pfa.storePlatform(testPlatforms[0]);
		assertTrue(((BaseAdaptor) pfa).fetchCount() == 1);
		pfa.storePlatform(testPlatforms[1]);
		assertTrue(((BaseAdaptor) pfa).fetchCount() == 2);
		pfa.storePlatform(testPlatforms[2]);
		assertTrue(((BaseAdaptor) pfa).fetchCount() == 3);
	}
	
	public void testFetchAllPlatforms(){
		
		Platform[] platforms = pfa.fetchAllPlatforms();
		
		for(int i = 0; i < platforms.length; i++){
			assertTrue(platforms[i].getId() == i + 1);
			assertTrue(platforms[i].getName().equals(testPlatforms[i].getName()));
			assertTrue(platforms[i].getType().equals(testPlatforms[i].getType()));
		}
	}
	
	public void testfetchAllTypes(){
		
		String[] types = pfa.fetchAllTypes();
		
		assertTrue(types[0].equals("expression"));
		assertTrue(types[1].equals("snp"));
	}
	
	public void testFetchPlatformById(){
		Platform p1 = pfa.fetchPlatformById(1);
		Platform p2 = pfa.fetchPlatformById(2);
		Platform p3 = pfa.fetchPlatformById(3);
	
		Platform[] platforms = new Platform[]{p1, p2, p3};
		
		for(int i = 0; i < platforms.length; i++){
			assertTrue(platforms[i].getId() == i + 1);
			assertTrue(platforms[i].getName().equals(testPlatforms[i].getName()));
			assertTrue(platforms[i].getType().equals(testPlatforms[i].getType()));
		}
	}
	
	public void testDeletePlatform(){
		Platform p1 = pfa.fetchPlatformById(1);
		Platform p2 = pfa.fetchPlatformById(2);
		Platform p3 = pfa.fetchPlatformById(3);
		
		pfa.deletePlatform(p1);
		assertTrue(((BaseAdaptor) pfa).fetchCount() == 2);
		pfa.deletePlatform(p2);
		assertTrue(((BaseAdaptor) pfa).fetchCount() == 1);
		pfa.deletePlatform(p3.getId());
		assertTrue(((BaseAdaptor) pfa).fetchCount() == 0);
	}
	
	protected void tearDown() {
		if(((BaseAdaptor) pfa).fetchCount() == 0){
			td.emptyPlatformTable();
		}
	}
}
