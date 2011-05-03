package de.unihamburg.zbh.fishoracle_db_api.driver;

public interface Driver {
	
Adaptor getAdaptor(String type);

Adaptor[] getAdaptors();

Adaptor addAdaptor(Adaptor adaptor);

void removeAdaptor(Adaptor adaptor);

void removeAdaptor(String type);

void removeAllAdaptors();
	
}
