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

import junit.framework.TestCase;
import de.unihamburg.zbh.fishoracle_db_api.data.Property;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.PropertyAdaptor;

public class PropertyAdaptorTest  extends TestCase {
	
	private TestData td;
	private PropertyAdaptor pra;
	private Property[] testproperties;
	
	protected void setUp() {
		
		td = new TestData();
		
		testproperties = td.createPropertyData();
		
		pra = td.getPra();
		
		td.createAndStoreOrganData();
		
		td.createAndStoreTissueSampleData();
	}
	
	public void testStoreProperty() {
			pra.storeProperty(testproperties[0]);
			assertTrue(((BaseAdaptor) pra).fetchCount() == 1);
			pra.storeProperty(testproperties[1]);
			assertTrue(((BaseAdaptor) pra).fetchCount() == 2);
			pra.storeProperty(testproperties[2]);
			assertTrue(((BaseAdaptor) pra).fetchCount() == 3);
			pra.storeProperty(testproperties[3]);
			assertTrue(((BaseAdaptor) pra).fetchCount() == 4);
			pra.storeProperty(testproperties[4]);
			assertTrue(((BaseAdaptor) pra).fetchCount() == 5);
			pra.storeProperty(testproperties[5]);
			assertTrue(((BaseAdaptor) pra).fetchCount() == 6);
	}
	
	public void testFetchAllProperties() {
		
		Property[] properties = pra.fetchAllProperties();
		
		for (int i=0; i < properties.length; i++) {
			assertTrue(properties[i].getId() == (i+1));
			assertTrue(properties[i].getLabel().equals(testproperties[i].getLabel()));
			assertTrue(properties[i].getType().equals(testproperties[i].getType()));
			assertTrue(properties[i].getActivty().equals(testproperties[i].getActivty()));
		}
	}
	
	public void testFetchPropertyById() {
		Property p1 = pra.fetchPropertyById(1);
		Property p2 = pra.fetchPropertyById(2);
		Property p3 = pra.fetchPropertyById(3);
		Property p4 = pra.fetchPropertyById(4);
		Property p5 = pra.fetchPropertyById(5);
		Property p6 = pra.fetchPropertyById(6);
		
		assertTrue(p1.getId() == 1);
		assertTrue(p1.getLabel().equals(testproperties[0].getLabel()));
		assertTrue(p1.getType().equals(testproperties[0].getType()));
		assertTrue(p1.getActivty().equals(testproperties[0].getActivty()));
		
		assertTrue(p2.getId() == 2);
		assertTrue(p2.getLabel().equals(testproperties[1].getLabel()));
		assertTrue(p2.getType().equals(testproperties[1].getType()));
		assertTrue(p2.getActivty().equals(testproperties[1].getActivty()));
		
		assertTrue(p3.getId() == 3);
		assertTrue(p3.getLabel().equals(testproperties[2].getLabel()));
		assertTrue(p3.getType().equals(testproperties[2].getType()));
		assertTrue(p3.getActivty().equals(testproperties[2].getActivty()));
		
		assertTrue(p4.getId() == 4);
		assertTrue(p4.getLabel().equals(testproperties[3].getLabel()));
		assertTrue(p4.getType().equals(testproperties[3].getType()));
		assertTrue(p4.getActivty().equals(testproperties[3].getActivty()));
		
		assertTrue(p5.getId() == 5);
		assertTrue(p5.getLabel().equals(testproperties[4].getLabel()));
		assertTrue(p5.getType().equals(testproperties[4].getType()));
		assertTrue(p5.getActivty().equals(testproperties[4].getActivty()));
		
		assertTrue(p6.getId() == 6);
		assertTrue(p6.getLabel().equals(testproperties[5].getLabel()));
		assertTrue(p6.getType().equals(testproperties[5].getType()));
		assertTrue(p6.getActivty().equals(testproperties[5].getActivty()));
	}
	
	public void testFetchAllTypes() {
		String[] types = pra.fetchAllTypes();
		
		types[0].equals("grade");
		types[1].equals("stage");
		
	}
	
	public void testFetchPropertyByType(){
		Property[] p1 = pra.fetchPropertiesByType("grade");
		Property[] p2 = pra.fetchPropertiesByType("stage");
		
		assertTrue(p1[0].getId() == 1);
		assertTrue(p1[1].getId() == 2);
		assertTrue(p1[2].getId() == 3);
		
		assertTrue(p2[0].getId() == 4);
		assertTrue(p2[1].getId() == 5);
		assertTrue(p2[2].getId() == 6);
		
	}
	
	public void testFetchProperties() {
		Property[] p1 = pra.fetchProperties(true);
		Property[] p2 = pra.fetchProperties(false);
		
		assertTrue(p1[0].getId() == 1);
		assertTrue(p1[1].getId() == 2);
		assertTrue(p1[2].getId() == 3);
	
		assertTrue(p2[0].getId() == 4);
		assertTrue(p2[1].getId() == 5);
		assertTrue(p2[2].getId() == 6);
	}
	
	public void fetchPropertiesForTissueSampleId(){
		
		Property[] properties1 = pra.fetchPropertiesForTissueSampleId(1);
		Property[] properties2 = pra.fetchPropertiesForTissueSampleId(2);
		Property[] properties3 = pra.fetchPropertiesForTissueSampleId(3);
		
		for(int i=0; i < properties1.length; i++){
			assertTrue(properties1[i].getLabel().equals(testproperties[i].getLabel()));
			assertTrue(properties1[i].getType().equals(testproperties[i].getType()));
			assertTrue(properties1[i].getActivty().equals(testproperties[i].getActivty()));
		}
		
		for(int i=0; i < properties2.length; i++){
			assertTrue(properties2[i].getLabel().equals(testproperties[i].getLabel()));
			assertTrue(properties2[i].getType().equals(testproperties[i].getType()));
			assertTrue(properties2[i].getActivty().equals(testproperties[i].getActivty()));
		}
		
		for(int i=0; i < properties3.length; i++){
			assertTrue(properties2[i].getLabel().equals(testproperties[i + 3].getLabel()));
			assertTrue(properties2[i].getType().equals(testproperties[i + 3].getType()));
			assertTrue(properties2[i].getActivty().equals(testproperties[i + 3].getActivty()));
		}
	}
	
	public void testDeleteProperty() {
		Property p1 = pra.fetchPropertyById(1);
		Property p2 = pra.fetchPropertyById(2);
		Property p3 = pra.fetchPropertyById(3);
		Property p4 = pra.fetchPropertyById(4);
		Property p5 = pra.fetchPropertyById(5);
		Property p6 = pra.fetchPropertyById(6);
		
		pra.deleteProperty(p1);
		assertTrue(((BaseAdaptor) pra).fetchCount() == 5);
		pra.deleteProperty(p2);
		assertTrue(((BaseAdaptor) pra).fetchCount() == 4);
		pra.deleteProperty(p3);
		assertTrue(((BaseAdaptor) pra).fetchCount() == 3);
		pra.deleteProperty(p4);
		assertTrue(((BaseAdaptor) pra).fetchCount() == 2);
		pra.deleteProperty(p5);
		assertTrue(((BaseAdaptor) pra).fetchCount() == 1);
		pra.deleteProperty(p6);
		assertTrue(((BaseAdaptor) pra).fetchCount() == 0);
		
	}
	
	protected void tearDown() {
		td.emptyTissueSampleTable();
		td.emptyTissueSamplePropertyTable();
		td.emptyOrganTable();
		if(((BaseAdaptor) pra).fetchCount() == 0){
			td.emptyPropertyTable();
		}
	}	
}