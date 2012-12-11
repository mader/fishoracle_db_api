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
	
	public void tesFetchTrackConfigById(int trackConfigId){
		
		
		
	}
	
	public void testFetchTrackConfigForConfigId(){}
	
	public void testDeleteTrackConfig(int trackConfigId){
		
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
			td.emptyAttributeInConfigTable();
			td.emptyTrackConfigTable();
			td.emptyAttributeInTrackConfigTable();
			td.emptyAttributeInConfigTable();
		}
	}
}
