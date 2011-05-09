package de.unihamburg.zbh.fishoracle_db_api.driver;

import de.unihamburg.zbh.fishoracle_db_api.data.Property;

public interface PropertyAdaptor {

	public Property[] fetchAllProperties();
	public Property[] fetchProperties(boolean enabled);
	public Property fetchPropertyById(int id);
	public Property[] fetchPropertiesByType(String type);
	public Property[] fetchPropertiesForTissueSampleId(int id);
	public String[] fetchAllTypes();
	public int storeProperty(Property property);
	public void deleteProperty(Property property);
	
	final static String TYPE = "PropertyAdaptor";
	
}
