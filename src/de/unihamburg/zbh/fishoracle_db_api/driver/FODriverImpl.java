package de.unihamburg.zbh.fishoracle_db_api.driver;

public class FODriverImpl extends DriverImpl implements FODriver{

	public FODriverImpl(String host, String database, String user,
			String password, String port) {
		super(host, database, user, password, port);
	}
	
	protected void loadAdaptors() {
		//addAdaptor(new XXXAdaptorImpl(this));
	}

}
