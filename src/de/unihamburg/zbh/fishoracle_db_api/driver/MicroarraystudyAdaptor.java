package de.unihamburg.zbh.fishoracle_db_api.driver;

import de.unihamburg.zbh.fishoracle_db_api.data.Microarraystudy;

public interface MicroarraystudyAdaptor {

	public Microarraystudy[] fetchAllMicroarraystudies();
	public Microarraystudy[] fetchAllMicroarraystudies(boolean withChildren);
	
	public Microarraystudy fetchMicroarraystudyById(int id);
	public Microarraystudy fetchMicroarraystudyById(int id, boolean withChilden);
	
	public Microarraystudy[] fetchMicroarraystudiesForProject(int projectId);
	public Microarraystudy[] fetchMicroarraystudiesForProject(int projectId, boolean withChrildren);
	
	public int storeMicroarraystudy(Microarraystudy mstudy,int projectId);
	public void deleteMicroarraystudy(int mstudyId);
	public void deleteMicroarraystudy(Microarraystudy mstudy);
	
	final static String TYPE = "MicroarraystudyAdaptor";
	
}
