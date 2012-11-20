package de.unihamburg.zbh.fishoracle_db_api.driver;

import de.unihamburg.zbh.fishoracle_db_api.data.TrackData;

public interface TrackConfigAdaptor {

	public int storeTrackConfig(TrackData td);
	
	public TrackData fetchTrackConfigById(int trackConfigId);
	
	public TrackData[] fetchTrackConfigForConfigId(int ConfigId);
	
	public int deleteTrackConfig(int trackConfigId);
}
