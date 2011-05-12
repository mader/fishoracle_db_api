package de.unihamburg.zbh.fishoracle_db_api.driver;

import de.unihamburg.zbh.fishoracle_db_api.data.Microarraystudy;

public interface MicroarraystudyAdaptor {

	public Microarraystudy[] fetchAllMicroarraystudies();
	
	public Microarraystudy fetchMicroarraystudyById(int id);
	
	public int storeMicroarraystudy(Microarraystudy mstudy);
	
	public void deleteMicroarraystudy(Microarraystudy mstudy);
	
	final static String TYPE = "MicroarraystudyAdaptor";
	
}
