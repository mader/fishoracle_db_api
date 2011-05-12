package de.unihamburg.zbh.fishoracle_db_api.driver;

public interface FODriver extends Driver {

	UserAdaptor getUserAdaptor();
	
	OrganAdaptor getOrganAdaptor();
	
	PropertyAdaptor getPropertyAdaptor();
	
	TissueSampleAdaptor getTissueSampleAdaptor();
	
	ChipAdaptor getChipAdaptor();
	
	CnSegmentAdaptor getCnSegmentAdaptor();
	
	MicroarraystudyAdaptor getMicroarraystudyAdaptor();
}
