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
import de.unihamburg.zbh.fishoracle_db_api.data.CnSegment;
import de.unihamburg.zbh.fishoracle_db_api.data.Group;
import de.unihamburg.zbh.fishoracle_db_api.data.Microarraystudy;
import de.unihamburg.zbh.fishoracle_db_api.data.Organ;
import de.unihamburg.zbh.fishoracle_db_api.data.Project;
import de.unihamburg.zbh.fishoracle_db_api.data.Property;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.ChipAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriver;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriverImpl;
import de.unihamburg.zbh.fishoracle_db_api.driver.GroupAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.MicroarraystudyAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.OrganAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.ProjectAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.PropertyAdaptor;
import junit.framework.TestCase;

/**
 * @author Malte Mader
 *
 */
public class ProjectAdaptorTest extends TestCase{
	
	private TestData td;
	private FODriver driver;
	private MicroarraystudyAdaptor ma;
	
	private GroupAdaptor ga;
	private Group group1, group2;
	
	private CnSegment segment1, segment2, segment3, segment4;
	private CnSegment[] testsegments1 = new CnSegment[2];
	private CnSegment[] testsegments2 = new CnSegment[2];
	
	private Microarraystudy mstudy1;
	private Microarraystudy mstudy2;
	private Microarraystudy[] teststudies = new Microarraystudy[2];
	
	private ChipAdaptor ca;
	private Chip chip1, chip2, chip3;
	
	private OrganAdaptor oa;
	private Organ organ1, organ2;
	private Organ[] testorgans = new Organ[2];
	
	private PropertyAdaptor pa;
	private Property property1, property2;
	private Property[] testproperties = new Property[2]; 
	
	private ProjectAdaptor pra;
	private Project project1, project2;
	
	protected void setUp() {
		
		td = new TestData();
		
		/*
		driver = new FODriverImpl("localhost", "emptyoracle", "fouser", "fish4me", "3306");
		
		ga = (GroupAdaptor) driver.getAdaptor("GroupAdaptor");
		
		group1 = new Group(1, "Staff", true);
		group2 = new Group(2, "Students", true);

		ga.storeGroup(group1);
		ga.storeGroup(group2);
		
		ma = (MicroarraystudyAdaptor) driver.getAdaptor("MicroarraystudyAdaptor");

		segment1 = new CnSegment(1, "1", 1, 5000, 0.5, 100);
		segment2 = new CnSegment(2, "1", 5001, 8000, 0.7, 300);
		segment3 = new CnSegment(3, "2", 1, 2000, 0.3, 600);
		segment4 = new CnSegment(4, "3", 1, 1000, 1.2, 250);
		
		testsegments1[0] = segment1;
		testsegments1[1] = segment2;
		testsegments2[0] = segment3;
		testsegments2[1] = segment4;
		
		ca = (ChipAdaptor) driver.getAdaptor("ChipAdaptor");
		
		chip1 = new Chip(1, "mapping250k_sty", "snp");
		chip2 = new Chip(2, "GenomeWideSNP_6", "snp");
		chip3 = new Chip(3, "hg-u133a_2", "expression");
		
		ca.storeChip(chip1);
		ca.storeChip(chip2);
		ca.storeChip(chip3);
		
		oa = (OrganAdaptor) driver.getAdaptor("OrganAdaptor");
		
		organ1 = new Organ(1, "Prostate", "Tumor tissue", "enabled");
		organ2 = new Organ(2, "Prostate", "Cell line", "enabled");
		
		testorgans[0] = organ1;
		testorgans[1] = organ2;

		oa.storeOrgan(organ1);
		oa.storeOrgan(organ2);
		
		pa = (PropertyAdaptor) driver.getAdaptor("PropertyAdaptor");
		
		property1 = new Property(1, "G0", "grade", "enabled");
		property2 = new Property(2, "G1", "grade", "enabled");
		
		testproperties[0] = property1;
		testproperties[1] = property2;
		
		pa.storeProperty(property1);
		pa.storeProperty(property2);
		
		int[] propertyIds = new int[testproperties.length];
		
		for(int i = 0; i < testproperties.length; i++){
			propertyIds[i] = testproperties[i].getId();
		}
		
		String description1 = "This is a description.";
		
		mstudy1 = new Microarraystudy(testsegments1, "teststudy1", description1, 1, 1, propertyIds, 1);
		mstudy2 = new Microarraystudy(testsegments2, "teststudy2", description1, 2, 2, propertyIds, 1);
		
		teststudies[0] = mstudy1;
		teststudies[1] = mstudy2;
		
		pra = (ProjectAdaptor) driver.getAdaptor("ProjectAdaptor");
		
		project1 = new Project(1, "Project1", "This is the description.");
		project2 = new Project(2, "Project2", "This is the description.");
		*/
	}
	
	public void testStoreProject(){
		pra.storeProject(project1);
		assertTrue(((BaseAdaptor) pra).fetchCount() == 1);
		pra.storeProject(project2);
		assertTrue(((BaseAdaptor) pra).fetchCount() == 2);
		
		ma.storeMicroarraystudy(mstudy1, 1);
		ma.storeMicroarraystudy(mstudy2, 1);
		
		pra.addMicroarraystudyToProject(1, 2);
		
	}
	
	public void testFetchAllProjects(){
		Project[] projects;
		try {
			projects = pra.fetchAllProjects();
			
			assertTrue(projects[0].getId() == 1);
			assertTrue(projects[0].getName().equals(project1.getName()));
			assertTrue(projects[0].getDescription().equals(project1.getDescription()));
			
			assertTrue(projects[1].getId() == 2);
			assertTrue(projects[1].getName().equals(project2.getName()));
			assertTrue(projects[1].getDescription().equals(project2.getDescription()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testFetchAccessRightforGroup(){
		pra.addGroupAccessToProject(1, 1, "r");
		pra.addGroupAccessToProject(1, 2, "rw");
		
		String r1 = pra.fetchAccessRightForGroup(1, 1);
		
		assertTrue(r1.equals("r"));
		
		String r2 = pra.fetchAccessRightForGroup(2, 1);
		assertTrue(r2.equals("rw"));
	}
	
	public void testDeleteProject(){
		Project p1 = pra.fetchProjectById(1);
		Project p2 = pra.fetchProjectById(2);
		
		pra.deleteProject(p1);
		assertTrue(((BaseAdaptor) pra).fetchCount() == 1);
		pra.deleteProject(p2);
		assertTrue(((BaseAdaptor) pra).fetchCount() == 0);
	}
	
	protected void tearDown() {
		((BaseAdaptor) ca).truncateTable(((BaseAdaptor) ca).getPrimaryTableName());
		((BaseAdaptor) oa).truncateTable(((BaseAdaptor) oa).getPrimaryTableName());
		((BaseAdaptor) pa).truncateTable(((BaseAdaptor) pa).getPrimaryTableName());
		((BaseAdaptor) ga).truncateTable(((BaseAdaptor) ga).getPrimaryTableName());
		if(((BaseAdaptor) pra).fetchCount() == 0){
			((BaseAdaptor) pra).truncateTable(((BaseAdaptor) pra).getPrimaryTableName());
			((BaseAdaptor) pra).truncateTable("microarraystudy");
			((BaseAdaptor) pra).truncateTable("microarraystudy_in_project");
			((BaseAdaptor) pra).truncateTable("sample_on_chip");
			((BaseAdaptor) pra).truncateTable("tissue_sample");
			((BaseAdaptor) pra).truncateTable("tissue_sample_property");
			((BaseAdaptor) pra).truncateTable("cn_segment");
		}
	}
	
}
