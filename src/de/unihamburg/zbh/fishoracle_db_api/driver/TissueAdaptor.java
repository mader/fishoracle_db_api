package de.unihamburg.zbh.fishoracle_db_api.driver;

import de.unihamburg.zbh.fishoracle_db_api.data.TissueSample;

public interface TissueAdaptor {

	public int storeTissueSample();
	public TissueSample getTissueSampleById();
	public int deleteTissueSample();
	
}
