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
import de.unihamburg.zbh.fishoracle_db_api.data.CnSegment;
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
import de.unihamburg.zbh.fishoracle_db_api.data.User;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.PlatformAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.CnSegmentAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.EnsemblDBsAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriver;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriverImpl;
import de.unihamburg.zbh.fishoracle_db_api.driver.GroupAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.SNPMutationAdaptor;
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
	private TissueSampleAdaptor ta;
	private CnSegmentAdaptor csa;
	private SNPMutationAdaptor ma;
	private StudyAdaptor sa;
	private EnsemblDBsAdaptor ea;
	
	public TestData() {
		driver = new FODriverImpl("localhost", "emptyoracle", "fouser", "fish4me", "3306");
		ua = (UserAdaptor) driver.getAdaptor("UserAdaptor");
		ga = (GroupAdaptor) driver.getAdaptor("GroupAdaptor");
		pa = (ProjectAdaptor) driver.getAdaptor("ProjectAdaptor");
		pfa = (PlatformAdaptor) driver.getAdaptor("PlatformAdaptor");
		oa = (OrganAdaptor) driver.getAdaptor("OrganAdaptor");
		ta = (TissueSampleAdaptor) driver.getAdaptor("TissueSampleAdaptor");
		pra = (PropertyAdaptor) driver.getAdaptor("PropertyAdaptor");
		csa = (CnSegmentAdaptor) driver.getAdaptor("CnSegmentAdaptor");
		ma = (SNPMutationAdaptor) driver.getAdaptor("SNPMutationAdaptor");
		sa = (StudyAdaptor) driver.getAdaptor("StudyAdaptor");
		ea = (EnsemblDBsAdaptor) driver.getAdaptor("EnsemblDBsAdaptor");
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
			ta.storeTissueSample(tissues[i]);
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
		
		CnSegment[] segments = createCnSegmentData();
		
		csa.storeCnSegment(segments[0], 1);
		csa.storeCnSegment(segments[1], 1);
		csa.storeCnSegment(segments[2], 1);
		csa.storeCnSegment(segments[3], 1);
		
		csa.storeCnSegment(segments[4], 2);
		csa.storeCnSegment(segments[5], 2);
		csa.storeCnSegment(segments[6], 2);
		csa.storeCnSegment(segments[7], 2);
		
		csa.storeCnSegment(segments[8], 3);
		csa.storeCnSegment(segments[9], 3);
		csa.storeCnSegment(segments[10], 3);
		csa.storeCnSegment(segments[11], 3);
		
	}
	
	public CnSegment[] createCnSegmentData(){
		
		CnSegment segment1, segment2, segment3, segment4,
					segment5, segment6, segment7, segment8,
					segment9, segment10, segment11, segment12;
		
		segment1 = new CnSegment(1, new Location(0, "1", 1, 3000), 0.5, 100, 1);
		segment2 = new CnSegment(2, new Location(0, "1", 3001, 5000), 0.7, 300, 1);
		segment3 = new CnSegment(3, new Location(0, "2", 5000, 7000), 0.3, 600, 1);
		segment4 = new CnSegment(4, new Location(0, "3", 2000, 3000), -1.2, 250, 1);
		
		segment5 = new CnSegment(5, new Location(0, "1", 2000, 4000), 0.2, 2300, 2);
		segment6 = new CnSegment(6, new Location(0, "2", 1000, 4000), 0.55, 400, 2);
		segment7 = new CnSegment(7, new Location(0, "3", 2500, 3500), -0.52, 630, 2);
		segment8 = new CnSegment(8, new Location(0, "4", 1, 1111), 1.32, 280, 2);
		
		segment9 = new CnSegment(9, new Location(0, "1", 1000, 2000), 0.3, 2300, 3);
		segment10 = new CnSegment(10, new Location(0, "2", 5000, 10000), 0.55, 400, 3);
		segment11 = new CnSegment(11, new Location(0, "3", 1, 2222), 0.-32, 630, 3);
		segment12 = new CnSegment(12, new Location(0, "4", 2222, 3333), 1.32, 280, 3);
		
		CnSegment[] segments = new CnSegment[]{segment1, segment2,
												segment3, segment4,
												segment5, segment6,
												segment7, segment8,
												segment9, segment10,
												segment11, segment12};
		return segments;
	}
	
	public SNPMutation[] createSNPMutationData(){
		
		SNPMutation mut1, mut2, mut3, mut4;
		
		mut1 = new SNPMutation(1,
								new Location(0, "1", 3000, 3000),
								"id1",
								"T",
								"G",
								60.0,
								"somatic",
								"high confidence",
								"gatk");
		mut1.setStudyId(1);
		
		mut2 = new SNPMutation(2,
								new Location(0, "1", 500, 500),
								"id2",
								"A",
								"C",
								30.0,
								"somatic",
								"medium confidence",
								"snv-mix");
		mut2.setStudyId(2);
		
		mut3 = new SNPMutation(3,
								new Location(0, "2", 1700, 1700),
								"id3",
								"G",
								"C",
								0.95,
								"germline",
								"low confidence",
								"varscan");
		mut3.setStudyId(3);
		
		mut4 = new SNPMutation(4,
								new Location(0, "3", 2000, 2000),
								"id4",
								"A",
								"T",
								75.5,
								"somatic",
								"high confidence",
								"varscan");
		mut4.setStudyId(4);
		
		SNPMutation[] muts = new SNPMutation[]{mut1, mut2, mut3, mut4};
		
		return muts;
		
	}
	
	public Study[] createStudyData(){
		
		Study study1, study2, study3;
		
		CnSegment[] segments = createCnSegmentData();
		
		CnSegment[] segments1 = new CnSegment[4];
		CnSegment[] segments2 = new CnSegment[4];
		CnSegment[] segments3 = new CnSegment[4];
		
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
		
		study1 = new Study("teststudy1", "microarray", "ncbi36", "This is a test.", 1, 1, pids, 1);
		study1.setId(1);
		study1.setSegments(segments1);
		
		study2 = new Study("teststudy2", "sequencing", "GRCh37", "This is a test.", 2, 2, pids, 1);
		study2.setId(2);
		study2.setSegments(segments2);
		
		study3 = new Study("teststudy3", "sequencing", "GRCh37", "This is a test.", 3, 3, pids, 1);
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
		((BaseAdaptor) ta).truncateTable(((BaseAdaptor) ta).getPrimaryTableName());
	}
	
	public void emptyTissueSamplePropertyTable(){
		((BaseAdaptor) ta).truncateTable("tissue_sample_property");
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
	
	public void emptyLocationTable(){
		((BaseAdaptor) csa).truncateTable("location");
	}
	
	public void emptySNPMutationTable(){
		((BaseAdaptor) ma).truncateTable(((BaseAdaptor) ma).getPrimaryTableName());
	}
	
	public void emptyStudyTable(){
		((BaseAdaptor) sa).truncateTable(((BaseAdaptor) sa).getPrimaryTableName());
	}
	
	public void emptyStudyInProjectTable(){
		((BaseAdaptor) sa).truncateTable("study_in_project");
	}
	
	public void emptySampleOnPlatformTable(){
		((BaseAdaptor) sa).truncateTable("sample_on_platform");
	}
	
	public void emptyEnsmeblDBsTable(){
		((BaseAdaptor) ea).truncateTable("ensembl_dbs");
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

	public TissueSampleAdaptor getTa() {
		return ta;
	}

	public void setTa(TissueSampleAdaptor ta) {
		this.ta = ta;
	}

	public CnSegmentAdaptor getCsa() {
		return csa;
	}

	public void setCsa(CnSegmentAdaptor csa) {
		this.csa = csa;
	}
	
	public SNPMutationAdaptor getMa() {
		return ma;
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
}