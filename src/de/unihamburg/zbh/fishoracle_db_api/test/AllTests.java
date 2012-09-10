package de.unihamburg.zbh.fishoracle_db_api.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTestSuite(CnSegmentTest.class);
		suite.addTestSuite(EnsemblDBsAdaptorTest.class);
		suite.addTestSuite(GroupAdaptorTest.class);
		suite.addTestSuite(OrganAdaptorTest.class);
		suite.addTestSuite(PlatformAdaptorTest.class);
		suite.addTestSuite(ProjectAdaptorTest.class);
		suite.addTestSuite(PropertyAdaptorTest.class);
		suite.addTestSuite(SNPMutationAdaptorTest.class);
		suite.addTestSuite(TranslocationAdaptorTest.class);
		suite.addTestSuite(StudyAdaptorTest.class);
		suite.addTestSuite(TissueSampleAdaptorTest.class);
		suite.addTestSuite(UserAdaptorTest.class);
		
		return suite;
	}
}
