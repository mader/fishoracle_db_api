package de.unihamburg.zbh.fishoracle_db_api.driver;

import de.unihamburg.zbh.fishoracle_db_api.data.TissueSample;

public interface TissueSampleAdaptor {

	public int storeTissueSample(TissueSample tissue);
	public TissueSample fetchTissueSampleById(int id);
	public int deleteTissueSample(TissueSample tissue);
	
	final static String TYPE = "TissueSampleAdaptor";
	
}
