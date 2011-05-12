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
		addAdaptor(new TissueSampleAdaptorImpl(this));
		addAdaptor(new ChipAdaptorImpl(this));
		addAdaptor(new CnSegmentAdaptorImpl(this));
		addAdaptor(new MicroarraystudyAdaptorImpl(this));
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
	
	@Override
	public TissueSampleAdaptor getTissueSampleAdaptor() {
		return (TissueSampleAdaptor) getAdaptor(TissueSampleAdaptor.TYPE);
	}
	
	@Override
	public ChipAdaptor getChipAdaptor() {
		return (ChipAdaptor) getAdaptor(ChipAdaptor.TYPE);
	}
	
	@Override
	public CnSegmentAdaptor getCnSegmentAdaptor() {
		return (CnSegmentAdaptor) getAdaptor(CnSegmentAdaptor.TYPE);
	}

	@Override
	public MicroarraystudyAdaptor getMicroarraystudyAdaptor() {
		return (MicroarraystudyAdaptor) getAdaptor(MicroarraystudyAdaptor.TYPE);
	}
}
