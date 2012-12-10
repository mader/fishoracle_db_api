package de.unihamburg.zbh.fishoracle_db_api.data;

import java.util.HashMap;

public abstract class Config {
	
	private HashMap<String, String[]> strArrays;
	private HashMap<String, String> strVal;
	
	public Config() {
		strArrays = new HashMap<String, String[]>();
		strVal = new HashMap<String, String>();
	}
	
	public void addStrArray(String key, String[] value){
		strArrays.put(key, value);
	}
	
	public String[] getStrArray(String key){
		return strArrays.get(key);
	}
	
	public void addStrVal(String key, String value){
		strVal.put(key, value);
	}
	
	public String getStrVal(String key){
		return strVal.get(key);
	}

	public HashMap<String, String[]> getStrArrays() {
		return strArrays;
	}
}
