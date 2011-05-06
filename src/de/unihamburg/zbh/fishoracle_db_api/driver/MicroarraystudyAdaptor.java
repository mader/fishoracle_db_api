package de.unihamburg.zbh.fishoracle_db_api.driver;

import de.unihamburg.zbh.fishoracle_db_api.data.Microarraystudy;

public interface MicroarraystudyAdaptor {

	public Microarraystudy[] fetchAllMicroarraystudies(boolean childeren);
	
	public Microarraystudy[] fetchAllMicroarraystudyById(boolean childeren);
	
	public int storeMicroarraystudy(/*parameter*/);
	
	public int deleteMicroarraystudy();
	
	final static String TYPE = "MicroarraystudyAdaptor";
	
}
