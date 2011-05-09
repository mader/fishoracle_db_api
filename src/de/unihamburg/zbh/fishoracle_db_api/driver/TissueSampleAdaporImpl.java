package de.unihamburg.zbh.fishoracle_db_api.driver;

import java.sql.ResultSet;

import de.unihamburg.zbh.fishoracle_db_api.data.TissueSample;

public class TissueSampleAdaporImpl extends BaseAdaptor implements TissueSampleAdaptor {

	protected TissueSampleAdaporImpl(FODriverImpl driver) {
		super(driver, TYPE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String[] columns() {
		return new String[]{""};
	}

	@Override
	public Object createObject(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] tables() {
		return new String[]{"tissue_sample",
							"organ",
							"tissue_sample_property",
							"property"};
	}

	@Override
	public int deleteTissueSample(TissueSample tissue) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TissueSample fetchTissueSampleById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int storeTissueSample(TissueSample tissue) {
		// TODO Auto-generated method stub
		return 0;
	}

}
