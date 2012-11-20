package de.unihamburg.zbh.fishoracle_db_api.driver;

import java.sql.ResultSet;

import de.unihamburg.zbh.fishoracle_db_api.data.TrackData;

public class TrackConfigAdaptorImpl extends BaseAdaptor implements TrackConfigAdaptor {

	protected TrackConfigAdaptorImpl(FODriverImpl driver, String type) {
		super(driver, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int storeTrackConfig(TrackData td) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TrackData fetchTrackConfigById(int trackConfigId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrackData[] fetchTrackConfigForConfigId(int ConfigId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteTrackConfig(int trackConfigId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected String[] tables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] columns() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[][] leftJoins() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createObject(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}

}
