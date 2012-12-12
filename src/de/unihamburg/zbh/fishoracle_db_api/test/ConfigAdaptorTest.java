package de.unihamburg.zbh.fishoracle_db_api.test;

import de.unihamburg.zbh.fishoracle_db_api.data.ConfigData;
import de.unihamburg.zbh.fishoracle_db_api.data.TrackData;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.ConfigAdaptor;
import junit.framework.TestCase;

public class ConfigAdaptorTest extends TestCase {

	private TestData td;
	private ConfigAdaptor ca;
	private ConfigData[] testConfigs;
	
	protected void setUp() {
		
		td = new TestData();
		
		ca = td.getCa();
		
		testConfigs = td.createConfigData();
	}
	
	public void testStoreConfig(){
		
		ca.storeConfig(testConfigs[0]);
		assertTrue(((BaseAdaptor) ca).fetchCount() == 1);
		ca.storeConfig(testConfigs[1]);
		assertTrue(((BaseAdaptor) ca).fetchCount() == 2);
	}
	
	public void testFetchConfigById(){
		
		ConfigData cd1 = ca.fetchConfigById(1);
		ConfigData cd2 = ca.fetchConfigById(2);
		
		ConfigData[] cd = new ConfigData[]{cd1, cd2};
		
		for(int i = 0; i < cd.length; i++){
			assertTrue(cd[i].getId() == i + 1);
			assertTrue(cd[i].getUserId() == testConfigs[i].getUserId());
			assertTrue(cd[i].getName().equals(testConfigs[i].getName()));
		}
		
		assertTrue(cd[0].getTracks().length == 1);
		TrackData[] td1 = cd[0].getTracks();
		
		String[] s = cd[0].getStrArray("globalAttrib");
		assertTrue(s.length == 1);
		assertTrue(s[0].equals("1"));
		
		s = td1[0].getStrArray("sorted");
		assertTrue(s.length == 1);
		assertTrue(s[0].equals("1"));
		
		s = td1[0].getStrArray("projectId");
		assertTrue(s.length == 3);
		assertTrue(s[0].equals("1"));
		assertTrue(s[1].equals("2"));
		assertTrue(s[2].equals("3"));
		 
		assertTrue(cd[1].getTracks().length == 2);
		TrackData[] td2 = cd[1].getTracks();
		
		s = td2[0].getStrArray("showCaption");
		assertTrue(s.length == 1);
		assertTrue(s[0].equals("1"));
		 
		s = td2[0].getStrArray("tissueId");
		assertTrue(s.length == 2);
		assertTrue(s[0].equals("1"));
		assertTrue(s[1].equals("2"));
		 
		s = td2[1].getStrArray("segmentMean");
		assertTrue(s.length == 1);
		assertTrue(s[0].equals("-0.25"));
		 
		s = td2[1].getStrArray("showCaption");
		assertTrue(s.length == 1);
		assertTrue(s[0].equals("0"));
		
	}
	
	public void testFetchConfigForUserId(int userId){
		
		ConfigData[] cd1 = ca.fetchConfigForUserId(1);
		
		assertTrue(cd1.length == 1);
		
		for(int i = 0; i < cd1.length; i++){
			assertTrue(cd1[i].getId() == i + 1);
			assertTrue(cd1[i].getUserId() == testConfigs[i].getUserId());
			assertTrue(cd1[i].getName().equals(testConfigs[i].getName()));
		}
		
		assertTrue(cd1[0].getTracks().length == 1);
		TrackData[] td1 = cd1[0].getTracks();
		
		String[] s = cd1[0].getStrArray("globalAttrib");
		assertTrue(s.length == 1);
		assertTrue(s[0].equals("1"));
		
		s = td1[0].getStrArray("sorted");
		assertTrue(s.length == 1);
		assertTrue(s[0].equals("1"));
		
		s = td1[0].getStrArray("projectId");
		assertTrue(s.length == 3);
		assertTrue(s[0].equals("1"));
		assertTrue(s[1].equals("2"));
		assertTrue(s[2].equals("3"));
		
		ConfigData[] cd2 = ca.fetchConfigForUserId(2);
		
		for(int i = 0; i < cd1.length; i++){
			assertTrue(cd2[i].getId() == i + 2);
			assertTrue(cd2[i].getUserId() == testConfigs[i].getUserId());
			assertTrue(cd2[i].getName().equals(testConfigs[i].getName()));
		}
		
		assertTrue(cd1[0].getTracks().length == 2);
		TrackData[] td2 = cd1[0].getTracks();
		
		s = td2[0].getStrArray("showCaption");
		assertTrue(s.length == 1);
		assertTrue(s[0].equals("1"));
		 
		s = td2[0].getStrArray("tissueId");
		assertTrue(s.length == 2);
		assertTrue(s[0].equals("1"));
		assertTrue(s[1].equals("2"));
		 
		s = td2[1].getStrArray("segmentMean");
		assertTrue(s.length == 1);
		assertTrue(s[0].equals("-0.25"));
		 
		s = td2[1].getStrArray("showCaption");
		assertTrue(s.length == 1);
		assertTrue(s[0].equals("0"));	
	}
	
	public void testDeleteConfig(){
		
		ConfigData cd1 = ca.fetchConfigById(1);
		ConfigData cd2 = ca.fetchConfigById(2);
		
		ca.deleteConfig(cd1.getId());
		assertTrue(((BaseAdaptor) ca).fetchCount() == 1);
		ca.deleteConfig(cd2.getId());
		assertTrue(((BaseAdaptor) ca).fetchCount() == 0);
	}
	
	protected void tearDown() {
		if(((BaseAdaptor) ca).fetchCount() == 0){
			td.emptyConfigAttributeTable();
			td.emptyTrackConfigTable();
			td.emptyConfigTable();
			td.emptyAttributeInTrackConfigTable();
			td.emptyAttributeInConfigTable();
		}
	}
}
