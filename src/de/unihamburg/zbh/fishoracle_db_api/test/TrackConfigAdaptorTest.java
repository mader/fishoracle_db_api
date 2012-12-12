package de.unihamburg.zbh.fishoracle_db_api.test;

import de.unihamburg.zbh.fishoracle_db_api.data.TrackData;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.TrackConfigAdaptor;
import junit.framework.TestCase;

public class TrackConfigAdaptorTest extends TestCase {

	private TestData td;
	private TrackConfigAdaptor tca;
	private TrackData[] testTracks;
	
	protected void setUp() {
		
		td = new TestData();
		
		tca = td.getTca();
		
		testTracks = td.createTrackData();
	}
	
	public void testStoreTrackConfig(){
		
		tca.storeTrackConfig(testTracks[0]);
		assertTrue(((BaseAdaptor) tca).fetchCount() == 1);
		tca.storeTrackConfig(testTracks[1]);
		assertTrue(((BaseAdaptor) tca).fetchCount() == 2);
		tca.storeTrackConfig(testTracks[2]);
		assertTrue(((BaseAdaptor) tca).fetchCount() == 3);
	}
	
	public void testFetchTrackConfigById(){
		
		TrackData td1 = tca.fetchTrackConfigById(1);
		TrackData td2 = tca.fetchTrackConfigById(2);
		TrackData td3 = tca.fetchTrackConfigById(3);
		
		TrackData[] td = new TrackData[]{td1, td2, td3};
		
		for(int i = 0; i < td.length; i++){
			assertTrue(td[i].getId() == i + 1);
			assertTrue(td[i].getTrackNumber() == testTracks[i].getTrackNumber());
			assertTrue(td[i].getTrackName().equals(testTracks[i].getTrackName()));
		}
		
		String[] s = td1.getStrArray("sorted");
		assertTrue(s.length == 1);
		assertTrue(s[0].equals("1"));
		
		s = td1.getStrArray("projectId");
		assertTrue(s.length == 3);
		assertTrue(s[0].equals("1"));
		assertTrue(s[1].equals("2"));
		assertTrue(s[2].equals("3"));
		 
		s = td2.getStrArray("showCaption");
		assertTrue(s.length == 1);
		assertTrue(s[0].equals("1"));
		 
		s = td2.getStrArray("tissueId");
		assertTrue(s.length == 2);
		assertTrue(s[0].equals("1"));
		assertTrue(s[1].equals("2"));
		 
		s = td3.getStrArray("segmentMean");
		assertTrue(s.length == 1);
		assertTrue(s[0].equals("-0.25"));
		 
		s = td3.getStrArray("showCaption");
		assertTrue(s.length == 1);
		assertTrue(s[0].equals("0"));
	}
	
	public void testFetchTrackConfigForConfigId(){
		
		TrackData[] td1 = tca.fetchTrackConfigForConfigId(1);
		TrackData[] td2 = tca.fetchTrackConfigForConfigId(2);
		
		assertTrue(td1[0].getId() == 1);
		assertTrue(td1[0].getTrackNumber() == testTracks[0].getTrackNumber());
		assertTrue(td1[0].getTrackName().equals(testTracks[0].getTrackName()));
		
		String[] s = td1[0].getStrArray("sorted");
		assertTrue(s.length == 1);
		assertTrue(s[0].equals("1"));
		
		s = td1[0].getStrArray("projectId");
		assertTrue(s.length == 3);
		assertTrue(s[0].equals("1"));
		assertTrue(s[1].equals("2"));
		assertTrue(s[2].equals("3"));
		
		assertTrue(td2[0].getId() == 2);
		assertTrue(td2[0].getTrackNumber() == testTracks[1].getTrackNumber());
		assertTrue(td2[0].getTrackName().equals(testTracks[1].getTrackName()));
		
		s = td2[0].getStrArray("showCaption");
		assertTrue(s.length == 1);
		assertTrue(s[0].equals("1"));
		 
		s = td2[0].getStrArray("tissueId");
		assertTrue(s.length == 2);
		assertTrue(s[0].equals("1"));
		assertTrue(s[1].equals("2"));
		
		assertTrue(td2[1].getId() == 3);
		assertTrue(td2[1].getTrackNumber() == testTracks[2].getTrackNumber());
		assertTrue(td2[1].getTrackName().equals(testTracks[2].getTrackName()));
		
		s = td2[1].getStrArray("segmentMean");
		assertTrue(s.length == 1);
		assertTrue(s[0].equals("-0.25"));
		 
		s = td2[1].getStrArray("showCaption");
		assertTrue(s.length == 1);
		assertTrue(s[0].equals("0"));
	}
	
	public void testDeleteTrackConfig(){
		
		TrackData td1 = tca.fetchTrackConfigById(1);
		TrackData td2 = tca.fetchTrackConfigById(2);
		TrackData td3 = tca.fetchTrackConfigById(3);
		
		tca.deleteTrackConfig(td1.getId());
		assertTrue(((BaseAdaptor) tca).fetchCount() == 2);
		tca.deleteTrackConfig(td2.getId());
		assertTrue(((BaseAdaptor) tca).fetchCount() == 1);
		tca.deleteTrackConfig(td3.getId());
		assertTrue(((BaseAdaptor) tca).fetchCount() == 0);
	}
	
	protected void tearDown() {
		if(((BaseAdaptor) tca).fetchCount() == 0){
			td.emptyConfigAttributeTable();
			td.emptyTrackConfigTable();
			td.emptyAttributeInTrackConfigTable();
			td.emptyAttributeInConfigTable();
		}
	}
}
