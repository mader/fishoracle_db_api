package de.unihamburg.zbh.fishoracle_db_api.driver;

import de.unihamburg.zbh.fishoracle_db_api.data.ConfigData;

public interface ConfigAdaptor {

	public int storeConfig(ConfigData td);
	
	public ConfigData fetchConfigById(int configId);
	
	public ConfigData[] fetchConfigForUserId(int userId);
	
	public int deleteConfig(int configId);
	
}
