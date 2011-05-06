package de.unihamburg.zbh.fishoracle_db_api.driver;

import de.unihamburg.zbh.fishoracle_db_api.data.Organ;

public interface OrganAdaptor {

	public Organ[] fetchAllOrgans();
	public Organ[] fetchOrgans(boolean enabled);
	public Organ fetchOrganById(int id);
	public Organ[] fetchOrgansByType(String type);
	public String[] fetchAllTypes();
	public int storeOrgan(Organ organ);
	public void deleteOrgan(Organ organ);
	
	final static String TYPE = "OrganAdaptor";
	
}
