package de.unihamburg.zbh.fishoracle_db_api.driver;

public class FODriverImpl extends DriverImpl implements FODriver{

	public FODriverImpl(String host, String database, String user,
			String password, String port) {
		super(host, database, user, password, port);
		loadAdaptors();
	}
	
	protected void loadAdaptors() {
		addAdaptor(new UserAdaptorImpl(this));
		addAdaptor(new OrganAdaptorImpl(this));
		addAdaptor(new PropertyAdaptorImpl(this));
	}

	@Override
	public synchronized UserAdaptor getUserAdaptor() {
		return (UserAdaptor) getAdaptor(UserAdaptor.TYPE);
	}

	@Override
	public OrganAdaptor getOrganAdaptor() {
		return (OrganAdaptor) getAdaptor(OrganAdaptor.TYPE);
	}

	@Override
	public PropertyAdaptor getPropertyAdaptor() {
		return (PropertyAdaptor) getAdaptor(PropertyAdaptor.TYPE);
	}
}
