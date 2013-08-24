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

import de.unihamburg.zbh.fishoracle_db_api.data.Attribute;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.ConfigAttributeAdaptor;
import junit.framework.TestCase;

public class ConfigAttributeAdaptorTest extends TestCase {

	private TestData td;
	private ConfigAttributeAdaptor caa;
	private Attribute[] testAttributes;

	protected void setUp() {
		
		td = new TestData();
		
		caa = td.getCaa();
		
		testAttributes = td.createAttributeData();
	}
	
	public void testStoreAttribute(){
		
		int aId1, aId2, aId3;
		
		aId1 = caa.storeAttribute(testAttributes[0].getKey(), testAttributes[0].getValue());
		assertTrue(((BaseAdaptor) caa).fetchCount() == 1);
		aId2 = caa.storeAttribute(testAttributes[1].getKey(), testAttributes[1].getValue());
		assertTrue(((BaseAdaptor) caa).fetchCount() == 2);
		aId3 = caa.storeAttribute(testAttributes[2].getKey(), testAttributes[2].getValue());
		assertTrue(((BaseAdaptor) caa).fetchCount() == 3);
		
		caa.addAttributeToConfig(aId1, 1, true);
		caa.addAttributeToConfig(aId2, 1, false);
		caa.addAttributeToConfig(aId3, 1, false);
	}
	
	public void testFetchAllKeys(){
		
		String[] s = caa.fetchAllKeys();
		
		for(int i = 2; i < s.length; i--){
			assertTrue(s[i].equals(testAttributes[i].getKey()));
		}
	}
	
	public void testFetchKeysForConfigId(){
		
		String[] s = caa.fetchKeysForConfigId(1, true);
		assertTrue(s[0].equals(testAttributes[0].getKey()));
		
		s = caa.fetchKeysForConfigId(1, false);
		assertTrue(s[0].equals(testAttributes[1].getKey()));
	}
	
	public void testFetchAttributeById(){
		
		Attribute a1 = caa.fetchAttributeById(1);
		Attribute a2 = caa.fetchAttributeById(2);
		Attribute a3 = caa.fetchAttributeById(3);
		
		Attribute[] as = new Attribute[]{a1, a2, a3};
		
		for(int i = 0; i < as.length; i++){
			
			assertTrue(as[i].getId() == i + 1);
			assertTrue(as[i].getKey().equals(testAttributes[i].getKey()));
			assertTrue(as[i].getValue().equals(testAttributes[i].getValue()));
		}
	}
	
	public void testFetchAttribute1(){
		
		Attribute a1 = caa.fetchAttribute("sorted", "1");
		Attribute a2 = caa.fetchAttribute("projectId", "0");
		Attribute a3 = caa.fetchAttribute("projectId", "1");
		
		Attribute[] as = new Attribute[]{a1, a2, a3};
		
		for(int i = 0; i < as.length; i++){
			
			assertTrue(as[i].getId() == i + 1);
			assertTrue(as[i].getKey().equals(testAttributes[i].getKey()));
			assertTrue(as[i].getValue().equals(testAttributes[i].getValue()));
		}
	}
	
	public void testFetchAttribute(String key, int configId, boolean global){
		
		Attribute[] as = caa.fetchAttribute("sorted", 1, true);
		assertTrue(as[0].getId() == 1);
		assertTrue(as[0].getKey().equals(testAttributes[0].getKey()));
		assertTrue(as[0].getValue().equals(testAttributes[0].getValue()));
		
		as = caa.fetchAttribute("projectId", 1, false);
		assertTrue(as[0].getId() == 2);
		assertTrue(as[0].getKey().equals(testAttributes[1].getKey()));
		assertTrue(as[0].getValue().equals(testAttributes[1].getValue()));
		
		assertTrue(as[1].getId() == 3);
		assertTrue(as[1].getKey().equals(testAttributes[2].getKey()));
		assertTrue(as[1].getValue().equals(testAttributes[2].getValue()));
		
	}

	public void testDeleteAttribute(){
		
		Attribute a1 = caa.fetchAttributeById(1);
		Attribute a2 = caa.fetchAttributeById(2);
		Attribute a3 = caa.fetchAttributeById(3);
		
		caa.deleteAttribute(a1.getId());
		assertTrue(((BaseAdaptor) caa).fetchCount() == 2);
		caa.deleteAttribute(a2.getId());
		assertTrue(((BaseAdaptor) caa).fetchCount() == 1);
		caa.deleteAttribute(a3.getId());
		assertTrue(((BaseAdaptor) caa).fetchCount() == 0);
	}
	
	protected void tearDown() {
		if(((BaseAdaptor) caa).fetchCount() == 0){
			td.emptyConfigAttributeTable();
			td.emptyAttributeInTrackConfigTable();
			td.emptyAttributeInConfigTable();
		}
	}
}
