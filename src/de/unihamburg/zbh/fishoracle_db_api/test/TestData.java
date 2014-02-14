/*
  Copyright (c) 2011-2013 Malte Mader <mader@zbh.uni-hamburg.de>
  Copyright (c) 2011-2013 Center for Bioinformatics, University of Hamburg

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
import de.unihamburg.zbh.fishoracle_db_api.data.ConfigData;
import de.unihamburg.zbh.fishoracle_db_api.data.GenericFeature;
import de.unihamburg.zbh.fishoracle_db_api.data.Platform;
import de.unihamburg.zbh.fishoracle_db_api.data.Segment;
import de.unihamburg.zbh.fishoracle_db_api.data.EnsemblDBs;
import de.unihamburg.zbh.fishoracle_db_api.data.Group;
import de.unihamburg.zbh.fishoracle_db_api.data.Location;
import de.unihamburg.zbh.fishoracle_db_api.data.SNPMutation;
import de.unihamburg.zbh.fishoracle_db_api.data.Study;
import de.unihamburg.zbh.fishoracle_db_api.data.Organ;
import de.unihamburg.zbh.fishoracle_db_api.data.Project;
import de.unihamburg.zbh.fishoracle_db_api.data.ProjectAccess;
import de.unihamburg.zbh.fishoracle_db_api.data.Property;
import de.unihamburg.zbh.fishoracle_db_api.data.TissueSample;
import de.unihamburg.zbh.fishoracle_db_api.data.TrackData;
import de.unihamburg.zbh.fishoracle_db_api.data.Translocation;
import de.unihamburg.zbh.fishoracle_db_api.data.User;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.ConfigAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.ConfigAttributeAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.GenericAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.PlatformAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.SegmentAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.EnsemblDBsAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriver;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriverImpl;
import de.unihamburg.zbh.fishoracle_db_api.driver.GroupAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.SNPMutationAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.TrackConfigAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.TranslocationAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.StudyAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.OrganAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.ProjectAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.PropertyAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.TissueSampleAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.UserAdaptor;

/**
 * @author Malte Mader
 *
 */
public class TestData {

	private FODriver driver;
	private UserAdaptor ua;
	private GroupAdaptor ga;
	private ProjectAdaptor pa;
	private PlatformAdaptor pfa;
	private OrganAdaptor oa;
	private PropertyAdaptor pra;
	private TissueSampleAdaptor tsa;
	private SegmentAdaptor csa;
	private SNPMutationAdaptor ma;
	private TranslocationAdaptor ta;
	private GenericAdaptor gfa;
	private StudyAdaptor sa;
	private EnsemblDBsAdaptor ea;
	private ConfigAttributeAdaptor caa;
	private TrackConfigAdaptor tca;
	private ConfigAdaptor ca;
	
	public TestData() {
		driver = new FODriverImpl("localhost", "emptyoracle", "fouser", "fish4me", "3306");
		ua = (UserAdaptor) driver.getAdaptor("UserAdaptor");
		ga = (GroupAdaptor) driver.getAdaptor("GroupAdaptor");
		pa = (ProjectAdaptor) driver.getAdaptor("ProjectAdaptor");
		pfa = (PlatformAdaptor) driver.getAdaptor("PlatformAdaptor");
		oa = (OrganAdaptor) driver.getAdaptor("OrganAdaptor");
		tsa = (TissueSampleAdaptor) driver.getAdaptor("TissueSampleAdaptor");
		pra = (PropertyAdaptor) driver.getAdaptor("PropertyAdaptor");
		csa = (SegmentAdaptor) driver.getAdaptor("SegmentAdaptor");
		ma = (SNPMutationAdaptor) driver.getAdaptor("SNPMutationAdaptor");
		ta = (TranslocationAdaptor) driver.getAdaptor("TranslocationAdaptor");
		gfa = (GenericAdaptor) driver.getAdaptor("GenericAdaptor");
		sa = (StudyAdaptor) driver.getAdaptor("StudyAdaptor");
		ea = (EnsemblDBsAdaptor) driver.getAdaptor("EnsemblDBsAdaptor");
		caa = (ConfigAttributeAdaptor) driver.getAdaptor("ConfigAttributeAdaptor");
		tca = (TrackConfigAdaptor) driver.getAdaptor("TrackConfigAdaptor");
		ca = (ConfigAdaptor) driver.getAdaptor("ConfigAdaptor");
	}

	public User[] createAndStoreUserData() throws Exception{
		
		User[] users = createUserData();
		
		for(int i = 0; i < users.length; i++){
			ua.storeUser(users[i]);
		}
		return users;
	}
	
	public void addUsersToGroups(){
		ga.addUserToGroup(1, 1);
		ga.addUserToGroup(1, 2);
		ga.addUserToGroup(2, 3);
		ga.addUserToGroup(2, 4);
		ga.addUserToGroup(3, 5);
		ga.addUserToGroup(3, 6);
	}
	
	public User[] createUserData(){
				
		User user1, user2, user3, user4, user5, user6;
		
		user1 = new User("Bugs", "Bunny", "bugs", "bugs@loony.tunes", "123secret", false, false);
		user2 = new User("Daffy", "Duck", "daffy", "daffy@loony.tunes", "123secret", false, false);
		user3 = new User("Porky", "Pig", "porky", "porky@loony.tunes", "123secret", true, false);
		user4 = new User("Elmer", "Fudd", "elmer", "elmer@loony.tunes", "123secret", false, true);
		user5 = new User("Yosemite", "Sam", "yosemite", "yosemite@loony.tunes", "123secret", true, true);
		user6 = new User("Sylvester", "Cat", "sylvester", "sylvester@loony.tunes", "123secret", true, true);
		
		User[] users = new User[]{user1, user2, user3, user4, user5, user6};
		
		return users;
	}
	
	public Group[] createAndStoreGroupData(){
		
		Group[] groups = createGroupData();
		
		for(int i = 0; i < groups.length; i++){
			ga.storeGroup(groups[i]);
		}
		return groups;
	}
	
	public Group[] createGroupData(){
		
		Group group1, group2, group3;
		
		group1 = new Group(1, "Staff", true);
		group2 = new Group(2, "Students",  true);
		group3 = new Group(3, "Extern", false);
		
		Group[] groups = new Group[]{group1, group2, group3};
		
		return groups;
		
	}
	
	public void createAndStoreProjectAccessData(){
		
		ProjectAccess[] pas = createProjectAccessData();
		
		for(int i = 0; i < pas.length; i++){
			pa.addGroupAccessToProject(pas[i].getGroupId(), pas[i].getProjectId(), pas[i].getAccess());
		}
	}
	
	public ProjectAccess[] createProjectAccessData(){
		
		ProjectAccess pa1, pa2, pa3;
		
		pa1 = new ProjectAccess(1, 1, "rw");
		pa1.setProjectId(1);
		pa2 = new ProjectAccess(2, 2, "r");
		pa2.setProjectId(1);
		pa3 = new ProjectAccess(3, 3, "r");
		pa3.setProjectId(2);
		
		ProjectAccess[] pas = new ProjectAccess[]{pa1, pa2, pa3};
		
		return pas;
		
	}
	
	public void createAndStoreProjectData(){
		
		Project[] projects = createProjectData();
		
		for(int i = 0; i < projects.length; i++){
			pa.storeProject(projects[i]);
		}	
	}
	
	public Project[] createProjectData(){
		
		Project project1, project2;
		
		project1 = new Project(1, "Internal", "Internal data");
		project2 = new Project(2, "External", "External data");
		
		Project[] projects = new Project[]{project1, project2};
		
		return projects;
		
	}
	
	public void createAndStorePlatformData(){
		
		Platform[] platforms = createPlatformData();
		
		for(int i = 0; i < platforms.length; i++){
			pfa.storePlatform(platforms[i]);
		}
	}
	
	public Platform[] createPlatformData(){
		
		Platform platform1, platform2, platform3;
		
		platform1 = new Platform(1, "mapping250k_sty", "snp");
		platform2 = new Platform(2, "GenomeWideSNP_6", "snp");
		platform3 = new Platform(3, "hg-u133a_2", "expression");
		
		Platform[] platforms = new Platform[]{platform1, platform2, platform3};
		
		return platforms;
	}
	
	public Organ[] createAndStoreOrganData(){
		
		Organ[] organs = createOrganData();
		
		for(int i = 0; i < organs.length; i++){
			oa.storeOrgan(organs[i]);
		}
		return organs;
	}
	
	public Organ[] createOrganData(){
		
		Organ organ1, organ2, organ3, organ4, organ5, organ6;
		
		organ1 = new Organ(1, "Prostate", "Tumor tissue", "enabled");
		organ2 = new Organ(2, "Prostate", "Cell line", "enabled");
		organ3 = new Organ(3, "Kidney", "Tumor tissue", "enabled");
		organ4 = new Organ(4, "Kidney", "Cell line", "disabled");
		organ5 = new Organ(5, "Lung", "Tumor tissue", "disabled");
		organ6 = new Organ(6, "Lung", "Cell line", "disabled");
		
		Organ[] organs = new Organ[]{organ1, organ2, organ3,organ4, organ5, organ6};
		
		return organs;
	}
	
	public Property[] createAndStorePropertyData(){
		
		Property[] properties = createPropertyData();
		
		for(int i = 0; i < properties.length; i++){
			pra.storeProperty(properties[i]);
		}
		return properties;
	}
	
	public Property[] createPropertyData(){
		
		Property property1, property2, property3, property4, property5, property6;
		
		property1 = new Property(1, "G0", "grade", "enabled");
		property2 = new Property(2, "G1", "grade", "enabled");
		property3 = new Property(3, "G2", "grade", "enabled");
		property4 = new Property(4, "pT1", "stage", "disabled");
		property5 = new Property(5, "pT2", "stage", "disabled");
		property6 = new Property(6, "pT3", "stage", "disabled");
		
		Property[] properties = new Property[]{property1, property2, property3, property4, property5, property6};
		
		return properties;
		
	}
	
	public void createAndStoreTissueSampleData(){
		
		TissueSample[] tissues = createTissueSampleData();
		
		for(int i = 0; i < tissues.length; i++){
			tsa.storeTissueSample(tissues[i]);
		}
	}
	
	public TissueSample[] createTissueSampleData(){
		
		TissueSample tissue1, tissue2, tissue3;
		
		tissue1 = new TissueSample(1, oa.fetchOrganById(1), pra.fetchAllProperties());
		tissue2 = new TissueSample(2, oa.fetchOrganById(2), pra.fetchProperties(true));
		tissue3 = new TissueSample(3, oa.fetchOrganById(2), pra.fetchPropertiesByType("stage"));
		
		TissueSample[] tissues = new TissueSample[]{tissue1, tissue2, tissue3};
		
		return tissues;
	}
	
	public void createAndStoreCnSegmentData(){
		
		Segment[] segments = createSegmentData();
		
		csa.storeSegment(segments[0], 1);
		csa.storeSegment(segments[1], 1);
		csa.storeSegment(segments[2], 1);
		csa.storeSegment(segments[3], 1);
		
		csa.storeSegment(segments[4], 2);
		csa.storeSegment(segments[5], 2);
		csa.storeSegment(segments[6], 2);
		csa.storeSegment(segments[7], 2);
		
		csa.storeSegment(segments[8], 3);
		csa.storeSegment(segments[9], 3);
		csa.storeSegment(segments[10], 3);
		csa.storeSegment(segments[11], 3);
		
		csa.storeSegment(segments[12], 3);
		csa.storeSegment(segments[13], 3);
		
	}
	
	public Segment[] createSegmentData(){
		
		Segment segment1, segment2, segment3, segment4,
					segment5, segment6, segment7, segment8,
					segment9, segment10, segment11, segment12,
					segment13, segment14;
		
		Segment[] segments = null;
		
		try {
			segment1 = new Segment(1,
									new Location("1", 1, 3000),
									"cnv_intensity");
		
		
			segment1.setMean(0.5);
			segment1.setNumberOfMarkers(100);
			segment1.setPlatformId(1);
			segment1.setStudyId(1);
		
			segment2 = new Segment(2,
								new Location("1", 3001, 5000),
								"cnv_intensity");
		
			segment2.setMean(0.7);
			segment2.setNumberOfMarkers(300);
			segment2.setPlatformId(1);
			segment2.setStudyId(1);
		
			segment3 = new Segment(3,
								new Location("2", 5000, 7000),
								"cnv_intensity");
		
			segment3.setMean(0.3);
			segment3.setNumberOfMarkers(600);
			segment3.setPlatformId(1);
			segment3.setStudyId(1);
		
			segment4 = new Segment(4,
								new Location("3", 2000, 3000),
								"cnv_intensity");
		
			segment4.setMean(-1.2);
			segment4.setNumberOfMarkers(250);
			segment4.setPlatformId(1);
			segment4.setStudyId(1);
		
			segment5 = new Segment(5,
								new Location("1", 2000, 4000),
								"cnv_intensity");
		
			segment5.setMean(0.2);
			segment5.setNumberOfMarkers(2300);
			segment5.setPlatformId(1);
			segment5.setStudyId(2);
		
			segment6 = new Segment(6,
								new Location("2", 1000, 4000),
								"cnv_intensity");
		
			segment6.setMean(0.55);
			segment6.setNumberOfMarkers(400);
			segment6.setPlatformId(1);
			segment6.setStudyId(2);
		
			segment7 = new Segment(7,
								new Location("3", 2500, 3500),
								"cnv_intensity");
		
			segment7.setMean(-0.52);
			segment7.setNumberOfMarkers(630);
			segment7.setPlatformId(1);
			segment7.setStudyId(2);
		
			segment8 = new Segment(8,
								new Location("4", 1, 1111),
								"cnv_intensity");
		
			segment8.setMean(1.32);
			segment8.setNumberOfMarkers(280);
			segment8.setPlatformId(1);
			segment8.setStudyId(2);
		
			segment9 = new Segment(9,
								new Location("1", 1000, 2000),
								"cnv_intensity");
		
			segment9.setMean(0.3);
			segment9.setNumberOfMarkers(2300);
			segment9.setPlatformId(1);
			segment9.setStudyId(3);
		
			segment10 = new Segment(10,
								new Location("2", 5000, 10000),
								"cnv_intensity");
		
			segment10.setMean(0.55);
			segment10.setNumberOfMarkers(400);
			segment10.setPlatformId(1);
			segment10.setStudyId(3);
		
			segment11 = new Segment(11,
								new Location("3", 1, 2222),
								"cnv_intensity");
		
			segment11.setMean(-0.32);
			segment11.setNumberOfMarkers(630);
			segment11.setPlatformId(1);
			segment11.setStudyId(3);
		
			segment12 = new Segment(12,
								new Location("4", 2222, 3333),
								"cnv_intensity");
		
			segment12.setMean(1.32);
			segment12.setNumberOfMarkers(280);
			segment12.setPlatformId(1);
			segment12.setStudyId(3);
		
			segment13 = new Segment(13,
								new Location("5", 1000, 2000),
								"cnv_status");

			segment13.setStatus(0);
			segment13.setStatusScore(50.5);
			segment13.setPlatformId(1);
			segment13.setStudyId(3);
		
			segment14 = new Segment(14,
								new Location("5", 2000, 3000),
								"cnv_status");

			segment14.setStatus(1);
			segment14.setStatusScore(60.3);
			segment14.setPlatformId(1);
			segment14.setStudyId(3);
		
		
		
			segments = new Segment[]{segment1, segment2,
												segment3, segment4,
												segment5, segment6,
												segment7, segment8,
												segment9, segment10,
												segment11, segment12,
												segment13, segment14};
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return segments;
	}
	
	public SNPMutation[] createSNPMutationData(){
		
		SNPMutation mut1, mut2, mut3, mut4;
		SNPMutation[] muts = null;
		
		try {
			mut1 = new SNPMutation(1,
									new Location("1", 3000, 3000),
									"id1",
									"T",
									"G",
									60.0,
									"somatic",
									"high confidence",
									"gatk");
		
			mut1.setPlatformId(1);
			mut1.setStudyId(1);
		
			mut2 = new SNPMutation(2,
								new Location("1", 500, 500),
								"id2",
								"A",
								"C",
								30.0,
								"somatic",
								"medium confidence",
								"snv-mix");
			mut2.setPlatformId(1);
			mut2.setStudyId(2);
		
			mut3 = new SNPMutation(3,
								new Location("2", 1700, 1700),
								"id3",
								"G",
								"C",
								0.95,
								"germline",
								"low confidence",
								"varscan");
			mut3.setPlatformId(1);
			mut3.setStudyId(3);
		
			mut4 = new SNPMutation(4,
								new Location("3", 2000, 2000),
								"id4",
								"A",
								"T",
								75.5,
								"somatic",
								"high confidence",
								"varscan");
			mut4.setPlatformId(1);
			mut4.setStudyId(4);
		
			muts = new SNPMutation[]{mut1, mut2, mut3, mut4};
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return muts;
		
	}
	
	public Translocation[][] createTranslocationData() {
		
		Translocation t1[], t2[], t3[];
		
		t1 = new Translocation[2];
		t2 = new Translocation[2];
		t3 = new Translocation[2];
		
		Translocation[][] ts = null;
		
		try {
			t1[0] = new Translocation(1,
									new Location("1", 1000, 1000),
									0);
		
			t1[0].setPlatformId(1);
			t1[0].setStudyId(1);
			
			t1[1] = new Translocation(1,
								new Location("2", 5000, 5000),
								0);
			t1[1].setPlatformId(1);
			t1[1].setStudyId(1);
		
			t2[0] = new Translocation(1,
								new Location("1", 2000, 2000),
								0);
			t2[0].setPlatformId(1);
			t2[0].setStudyId(2);
		
			t2[1] = new Translocation(1,
								new Location("3", 5000, 5000),
								0);
			t2[1].setPlatformId(1);
			t2[1].setStudyId(2);
		
			t3[0] = new Translocation(1,
								new Location("1", 3000, 3000),
								0);
			t3[0].setPlatformId(1);
			t3[0].setStudyId(3);
		
			t3[1] = new Translocation(1,
								new Location("4", 5000, 5000),
								0);
			t3[1].setPlatformId(1);
			t3[1].setStudyId(3);
		
			ts = new Translocation[][]{t1, t2, t3};
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ts;
	}
	
	public GenericFeature[] createGenericFeatureData(){
		
		GenericFeature f1, f2, f3;
		GenericFeature[] fs = null;
		
		try {
			f1 = new GenericFeature(1,
									new Location("1", 3000, 3000),
									"Methylation");
		
			f1.setPlatformId(1);
			f1.setStudyId(1);
		
			f2 = new GenericFeature(2,
								new Location("1", 500, 500),
								"myAnnotation");
			f2.setPlatformId(1);
			f2.setStudyId(2);
		
			f3 = new GenericFeature(3,
								new Location("2", 1700, 1700),
								"whatever");
			f3.setPlatformId(1);
			f3.setStudyId(3);
		
			fs = new GenericFeature[]{f1, f2, f3};
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fs;
		
	}
	
	public Study[] createStudyData(){
		
		Study study1, study2, study3;
		
		Segment[] segments = createSegmentData();
		
		Segment[] segments1 = new Segment[4];
		Segment[] segments2 = new Segment[4];
		Segment[] segments3 = new Segment[4];
		
		for(int i=0; i < 4; i++){
			segments1[i] = segments[i];
		}
		
		for(int i=4; i < 8; i++){
			segments2[i - 4] = segments[i];
		}
		
		for(int i=8; i < 12; i++){
			segments3[i - 8] = segments[i];
		}
		
		Property[] properties =  createPropertyData();
		int[] pids = new int[properties.length];
		
		for(int i=0; i < properties.length; i++){
			
			pids[i] = properties[i].getId();
			
		}
		
		study1 = new Study("teststudy1", "ncbi36", "This is a test.", 1, pids, 1);
		study1.setId(1);
		study1.setSegments(segments1);
		
		study2 = new Study("teststudy2", "GRCh37", "This is a test.", 2, pids, 1);
		study2.setId(2);
		study2.setSegments(segments2);
		
		study3 = new Study("teststudy3", "GRCh37", "This is a test.", 3, pids, 1);
		study3.setId(3);
		study3.setSegments(segments3);
		
		Study[] studies = new Study[]{study1, study2, study3};
		
		return studies;
	}
	
	public EnsemblDBs[] createEnsemblDBsData(){
		
		EnsemblDBs e1, e2, e3;
		
		e1 = new EnsemblDBs(0, "ensembl65", "Ensembl 65", 65);
		e2 = new EnsemblDBs(0, "ensembl66", "Ensembl 66", 66);
		e3 = new EnsemblDBs(0, "ensembl67", "Ensembl 67", 67);
		
		EnsemblDBs[] edbss = new EnsemblDBs[]{e3,e2,e1};
		
		return edbss;
	}
	
	public Attribute[] createAttributeData(){
		
		Attribute a1, a2, a3;
		
		a1 = new Attribute("sorted", "1");
		a2 = new Attribute("projectId", "0");
		a3 = new Attribute("projectId", "1");
		
		Attribute[] as = new Attribute[]{a1,a2,a3};
		
		return as;
	}
	
	public TrackData[] createTrackData(){
		
		TrackData td1, td2, td3;
		
		td1 = new TrackData("test1", 1);
		td1.setConfigId(1);
		td1.addStrArray("sorted", new String[]{"1"});
		td1.addStrArray("projectId", new String[]{"1", "2", "3"});
		
		td2 = new TrackData("test2", 2);
		td2.setConfigId(2);
		td2.addStrArray("showCaption", new String[]{"1"});
		td2.addStrArray("tissueId", new String[]{"1", "2"});
		
		
		td3 = new TrackData("test3", 3);
		td3.setConfigId(2);
		td3.addStrArray("segmentMean", new String[]{"-0.25"});
		td3.addStrArray("showCaption", new String[]{"0"});
		
		TrackData[] td = new TrackData[]{td1,td2,td3};
		
		return td;
	}
	
	public ConfigData[] createConfigData(){
		
		ConfigData cd1, cd2;
	
		TrackData[] td = createTrackData();
		
		cd1 = new ConfigData();
		cd1.setName("Config1");
		cd1.setUserId(1);
		cd1.setTracks(new TrackData[]{td[0]});
		cd1.addStrArray("globalAttrib", new String[]{"1"});
		
		cd2 = new ConfigData();
		cd2.setName("Config2");
		cd2.setUserId(2);
		cd2.setTracks(new TrackData[]{td[1], td[2]});
		
		ConfigData[] cds = new ConfigData[]{cd1, cd2};
		
		return cds;
	}
	
	public void emptyPlatformTable(){
		((BaseAdaptor) pfa).truncateTable(((BaseAdaptor) pfa).getPrimaryTableName());
	}
	
	public void emptyOrganTable(){
		((BaseAdaptor) oa).truncateTable(((BaseAdaptor) oa).getPrimaryTableName());
	}
	
	public void emptyPropertyTable(){
		((BaseAdaptor) pra).truncateTable(((BaseAdaptor) pra).getPrimaryTableName());
	}
	
	public void emptyTissueSampleTable(){
		((BaseAdaptor) tsa).truncateTable(((BaseAdaptor) tsa).getPrimaryTableName());
	}
	
	public void emptyTissueSamplePropertyTable(){
		((BaseAdaptor) tsa).truncateTable("tissue_sample_property");
	}
	
	public void emptyUserTable(){
		((BaseAdaptor) ua).truncateTable(((BaseAdaptor) ua).getPrimaryTableName());
	}
	
	public void emptyUserInGroupTable(){
		((BaseAdaptor) ua).truncateTable("user_in_group");
	}
	
	public void emptyGroupTable(){
		((BaseAdaptor) ga).truncateTable(((BaseAdaptor) ga).getPrimaryTableName());
	}

	public void emptyProjectTable(){
		((BaseAdaptor) pa).truncateTable(((BaseAdaptor) pa).getPrimaryTableName());
	}
	
	public void emptyProjectAccessTable(){
		((BaseAdaptor) pa).truncateTable("group_project_access");
	}
	
	public void emptyCnSegmentTable(){
		((BaseAdaptor) csa).truncateTable(((BaseAdaptor) csa).getPrimaryTableName());
	}
	
	public void emptySNPMutationTable(){
		((BaseAdaptor) ma).truncateTable(((BaseAdaptor) ma).getPrimaryTableName());
	}
	
	public void emptyTranslocationTable(){
		((BaseAdaptor) ta).truncateTable(((BaseAdaptor) ta).getPrimaryTableName());
	}
	
	public void emptyFeatureTable(){
		((BaseAdaptor) gfa).truncateTable(((BaseAdaptor) gfa).getPrimaryTableName());
	}
	
	public void emptyStudyTable(){
		((BaseAdaptor) sa).truncateTable(((BaseAdaptor) sa).getPrimaryTableName());
	}
	
	public void emptyStudyInProjectTable(){
		((BaseAdaptor) sa).truncateTable("study_in_project");
	}
	
	public void emptyEnsmeblDBsTable(){
		((BaseAdaptor) ea).truncateTable("ensembl_dbs");
	}
	
	public void emptyConfigAttributeTable(){
		((BaseAdaptor) caa).truncateTable("config_attribute");
	}
	
	public void emptyAttributeInTrackConfigTable(){
		((BaseAdaptor) caa).truncateTable("attrib_in_track_config");
	}
	
	public void emptyAttributeInConfigTable(){
		((BaseAdaptor) caa).truncateTable("attrib_in_config");
	}
	
	public void emptyTrackConfigTable(){
		((BaseAdaptor) tca).truncateTable("track_config");
	}
	
	public void emptyConfigTable(){
		((BaseAdaptor) ca).truncateTable("config");
	}
	
	public FODriver getDriver() {
		return driver;
	}

	public void setDriver(FODriver driver) {
		this.driver = driver;
	}
	
	public UserAdaptor getUa() {
		return ua;
	}

	public void setUa(UserAdaptor ua) {
		this.ua = ua;
	}

	public GroupAdaptor getGa() {
		return ga;
	}
	
	public void setGa(GroupAdaptor ga) {
		this.ga = ga;
	}
	
	public ProjectAdaptor getPa() {
		return pa;
	}

	public void setPa(ProjectAdaptor pa) {
		this.pa = pa;
	}

	public PlatformAdaptor getPfa() {
		return pfa;
	}

	public void setCa(PlatformAdaptor ca) {
		this.pfa = ca;
	}

	public OrganAdaptor getOa() {
		return oa;
	}

	public void setOa(OrganAdaptor oa) {
		this.oa = oa;
	}

	public PropertyAdaptor getPra() {
		return pra;
	}

	public void setPra(PropertyAdaptor pra) {
		this.pra = pra;
	}

	public TissueSampleAdaptor getTsa() {
		return tsa;
	}

	public void setTsa(TissueSampleAdaptor tsa) {
		this.tsa = tsa;
	}

	public SegmentAdaptor getCsa() {
		return csa;
	}

	public void setCsa(SegmentAdaptor csa) {
		this.csa = csa;
	}
	
	public SNPMutationAdaptor getMa() {
		return ma;
	}

	public TranslocationAdaptor getTa() {
		return ta;
	}
	
	public GenericAdaptor getGfa() {
		return gfa;
	}
	
	public void setMa(SNPMutationAdaptor ma) {
		this.ma = ma;
	}

	public StudyAdaptor getSa() {
		return sa;
	}

	public void setMa(StudyAdaptor ma) {
		this.sa = ma;
	}

	public EnsemblDBsAdaptor getEa() {
		return ea;
	}

	public void setEa(EnsemblDBsAdaptor ea) {
		this.ea = ea;
	}
	
	public ConfigAttributeAdaptor getCaa() {
		return caa;
	}
	
	public void setCaa(ConfigAttributeAdaptor caa) {
		this.caa = caa;
	}

	public TrackConfigAdaptor getTca() {
		return tca;
	}

	public void setTca(TrackConfigAdaptor tca) {
		this.tca = tca;
	}

	public ConfigAdaptor getCa() {
		return ca;
	}

	public void setCa(ConfigAdaptor ca) {
		this.ca = ca;
	}
}