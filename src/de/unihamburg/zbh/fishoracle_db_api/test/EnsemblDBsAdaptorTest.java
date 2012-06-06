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

import org.junit.Test;

import de.unihamburg.zbh.fishoracle_db_api.data.EnsemblDBs;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.EnsemblDBsAdaptor;

import junit.framework.TestCase;

public class EnsemblDBsAdaptorTest extends TestCase {

	private TestData td;
	private EnsemblDBsAdaptor ea;
	private EnsemblDBs[] testEdbss;
	
	protected void setUp() {
		
		td = new TestData();
		
		ea = td.getEa();
		
		testEdbss = td.createEnsemblDBsData();
	}
	
	@Test
	public void testStoreEnsemblDBs(){
		ea.storeDB(testEdbss[0]);
		assertTrue(((BaseAdaptor) ea).fetchCount() == 1);
		ea.storeDB(testEdbss[1]);
		assertTrue(((BaseAdaptor) ea).fetchCount() == 2);
		ea.storeDB(testEdbss[2]);
		assertTrue(((BaseAdaptor) ea).fetchCount() == 3);
	}
	
	@Test
	public void testFetchAllEnsemblDBs(){
		EnsemblDBs[] edbss = ea.fetchAllDBs();
		
		for(int i = 0; i < edbss.length; i++){
			assertTrue(edbss[i].getId() == i + 1);
			assertTrue(edbss[i].getDBName().equals(testEdbss[i].getDBName()));
			assertTrue(edbss[i].getLabel().equals(testEdbss[i].getLabel()));
			assertTrue(edbss[i].getVersion() == testEdbss[i].getVersion());
		}
	}
	
	public void testFetchEnsemblDBsById(){
		EnsemblDBs e1 = ea.fetchDBById(1);
		EnsemblDBs e2 = ea.fetchDBById(2);
		EnsemblDBs e3 = ea.fetchDBById(3);
	
		EnsemblDBs[] edbs = new EnsemblDBs[]{e1, e2, e3};
		
		for(int i = 0; i < edbs.length; i++){
			assertTrue(edbs[i].getId() == i + 1);
			assertTrue(edbs[i].getDBName().equals(testEdbss[i].getDBName()));
			assertTrue(edbs[i].getLabel().equals(testEdbss[i].getLabel()));
			assertTrue(edbs[i].getVersion() == testEdbss[i].getVersion());
		}
	}
	
	@Test
	public void testDeleteEnsemblDBs(){
		
		ea.deleteDB(1);
		assertTrue(((BaseAdaptor) ea).fetchCount() == 2);
		ea.deleteDB(2);
		assertTrue(((BaseAdaptor) ea).fetchCount() == 1);
		ea.deleteDB(3);
		assertTrue(((BaseAdaptor) ea).fetchCount() == 0);
		
	}
	
	protected void tearDown() {
		if(((BaseAdaptor) ea).fetchCount() == 0){
			td.emptyEnsmeblDBsTable();
		}
	}	
}