package de.unihamburg.zbh.fishoracle_db_api.driver;

import java.sql.ResultSet;

import de.unihamburg.zbh.fishoracle_db_api.data.ConfigData;

public class ConfigAdaptorImpl extends BaseAdaptor implements ConfigAdaptor {

	protected ConfigAdaptorImpl(FODriverImpl driver, String type) {
		super(driver, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int storeConfig(ConfigData td) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ConfigData fetchConfigById(int configId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConfigData[] fetchConfigForUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteConfig(int configId) {
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
