package de.unihamburg.zbh.fishoracle_db_api.driver;

import java.sql.ResultSet;

import de.unihamburg.zbh.fishoracle_db_api.data.Microarraystudy;

public class MicroarraystudyAdaptorImpl extends BaseAdaptor implements MicroarraystudyAdaptor{

	protected MicroarraystudyAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}

	@Override
	protected String[] columns() {
		return new String[]{"microarraystudy_id",
							"microarraystudy_date_inserted",
							"microarraystudy_labelling",
							"microarraystudy_description",
							"microarraystudy_user_id",
							"microarraystudy_sample_on_chip_id"};
	}

	@Override
	public Object createObject(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] tables() {
		return new String[]{"microarraystudy",
							"cn_segment",
							"sample_on_chip",
							"chip",
							"tissue_sample",
							"organ"};
	}

	@Override
	public int deleteMicroarraystudy() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Microarraystudy[] fetchAllMicroarraystudies(boolean childeren) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Microarraystudy[] fetchAllMicroarraystudyById(boolean childeren) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int storeMicroarraystudy() {
		// TODO Auto-generated method stub
		return 0;
	}

}
