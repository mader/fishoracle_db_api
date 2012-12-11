package de.unihamburg.zbh.fishoracle_db_api.driver;

import de.unihamburg.zbh.fishoracle_db_api.data.ConfigData;

public interface ConfigAdaptor {

	public int storeConfig(ConfigData cd);
	
	public ConfigData fetchConfigById(int configId);
	
	public ConfigData[] fetchConfigForUserId(int userId);
	
	public void deleteConfig(int configId);
	
	final static String TYPE = "ConfigAdaptor";
	
}
