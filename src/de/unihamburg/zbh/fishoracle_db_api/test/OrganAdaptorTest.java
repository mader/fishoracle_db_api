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

import de.unihamburg.zbh.fishoracle_db_api.data.Organ;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.OrganAdaptor;
import junit.framework.TestCase;

public class OrganAdaptorTest extends TestCase {
	
	private TestData td;
	private OrganAdaptor oa;
	private Organ[] testorgans; 
	
	protected void setUp() {
		
		td = new TestData();
		
		oa = td.getOa();
		
		testorgans = td.createOrganData();
	}
	
	public void testStoreOrgan() {
		if(((BaseAdaptor) oa).fetchCount() == 0){
			oa.storeOrgan(testorgans[0]);
			assertTrue(((BaseAdaptor) oa).fetchCount() == 1);
			oa.storeOrgan(testorgans[1]);
			assertTrue(((BaseAdaptor) oa).fetchCount() == 2);
			oa.storeOrgan(testorgans[2]);
			assertTrue(((BaseAdaptor) oa).fetchCount() == 3);
			oa.storeOrgan(testorgans[3]);
			assertTrue(((BaseAdaptor) oa).fetchCount() == 4);
			oa.storeOrgan(testorgans[4]);
			assertTrue(((BaseAdaptor) oa).fetchCount() == 5);
			oa.storeOrgan(testorgans[5]);
			assertTrue(((BaseAdaptor) oa).fetchCount() == 6);
		}
	}
	
	public void testFetchAllOrgans() {
		Organ[] organs = oa.fetchAllOrgans();
		
		for (int i=0; i < organs.length; i++) {
			assertTrue(organs[i].getId() == (i+1));
			assertTrue(organs[i].getLabel().equals(testorgans[i].getLabel()));
			assertTrue(organs[i].getType().equals(testorgans[i].getType()));
			assertTrue(organs[i].getActivty().equals(testorgans[i].getActivty()));
		}
	}
	
	public void testFetchOrgans(){
		
		Organ[] organs = oa.fetchOrgans(true);
		
		for (int i=0; i < organs.length; i++) {
			assertTrue(organs[i].getId() == (i+1));
			assertTrue(organs[i].getLabel().equals(testorgans[i].getLabel()));
			assertTrue(organs[i].getType().equals(testorgans[i].getType()));
			assertTrue(organs[i].getActivty().equals(testorgans[i].getActivty()));
		}
		
		organs = oa.fetchOrgans(false);
		
		for (int i=0; i < organs.length; i++) {
			assertTrue(organs[i].getId() == (i+3+1));
			assertTrue(organs[i].getLabel().equals(testorgans[i + 3].getLabel()));
			assertTrue(organs[i].getType().equals(testorgans[i + 3].getType()));
			assertTrue(organs[i].getActivty().equals(testorgans[i + 3].getActivty()));
		}
	}
	
	public void testFetchOrganById() {
		Organ o1 = oa.fetchOrganById(1);
		Organ o2 = oa.fetchOrganById(2);
		Organ o3 = oa.fetchOrganById(3);
		Organ o4 = oa.fetchOrganById(4);
		Organ o5 = oa.fetchOrganById(5);
		Organ o6 = oa.fetchOrganById(6);
		
		assertTrue(o1.getId() == 1);
		assertTrue(o1.getLabel().equals(testorgans[0].getLabel()));
		assertTrue(o1.getType().equals(testorgans[0].getType()));
		assertTrue(o1.getActivty().equals(testorgans[0].getActivty()));
		
		assertTrue(o2.getId() == 2);
		assertTrue(o2.getLabel().equals(testorgans[1].getLabel()));
		assertTrue(o2.getType().equals(testorgans[1].getType()));
		assertTrue(o2.getActivty().equals(testorgans[1].getActivty()));
		
		assertTrue(o3.getId() == 3);
		assertTrue(o3.getLabel().equals(testorgans[2].getLabel()));
		assertTrue(o3.getType().equals(testorgans[2].getType()));
		assertTrue(o3.getActivty().equals(testorgans[2].getActivty()));
		
		assertTrue(o4.getId() == 4);
		assertTrue(o4.getLabel().equals(testorgans[3].getLabel()));
		assertTrue(o4.getType().equals(testorgans[3].getType()));
		assertTrue(o4.getActivty().equals(testorgans[3].getActivty()));
		
		assertTrue(o5.getId() == 5);
		assertTrue(o5.getLabel().equals(testorgans[4].getLabel()));
		assertTrue(o5.getType().equals(testorgans[4].getType()));
		assertTrue(o5.getActivty().equals(testorgans[4].getActivty()));
		
		assertTrue(o6.getId() == 6);
		assertTrue(o6.getLabel().equals(testorgans[5].getLabel()));
		assertTrue(o6.getType().equals(testorgans[5].getType()));
		assertTrue(o6.getActivty().equals(testorgans[5].getActivty()));
	}
	
	public void testFetchAllTypes() {
		String[] types = oa.fetchAllTypes();
		
		types[0].equals("Tumor tissue");
		types[1].equals("Cell line");
		
	}
	
	public void testFetchOrganByType(){
		Organ[] o1 = oa.fetchOrgansByType("Tumor tissue");
		Organ[] o2 = oa.fetchOrgansByType("Cell line");
		
		assertTrue(o1[0].getId() == 1);
		assertTrue(o1[1].getId() == 3);
		assertTrue(o1[2].getId() == 5);
		
		assertTrue(o2[0].getId() == 2);
		assertTrue(o2[1].getId() == 4);
		assertTrue(o2[2].getId() == 6);
		
	}
	
	public void testDeleteOrgan() {
		Organ o1 = oa.fetchOrganById(1);
		Organ o2 = oa.fetchOrganById(2);
		Organ o3 = oa.fetchOrganById(3);
		Organ o4 = oa.fetchOrganById(4);
		Organ o5 = oa.fetchOrganById(5);
		Organ o6 = oa.fetchOrganById(6);
		
		oa.deleteOrgan(o1);
		assertTrue(((BaseAdaptor) oa).fetchCount() == 5);
		oa.deleteOrgan(o2);
		assertTrue(((BaseAdaptor) oa).fetchCount() == 4);
		oa.deleteOrgan(o3);
		assertTrue(((BaseAdaptor) oa).fetchCount() == 3);
		oa.deleteOrgan(o4);
		assertTrue(((BaseAdaptor) oa).fetchCount() == 2);
		oa.deleteOrgan(o5);
		assertTrue(((BaseAdaptor) oa).fetchCount() == 1);
		oa.deleteOrgan(o6);
		assertTrue(((BaseAdaptor) oa).fetchCount() == 0);
		
	}
	
	protected void tearDown() {
		if(((BaseAdaptor) oa).fetchCount() == 0){
			td.emptyOrganTable();
		}
	}
}