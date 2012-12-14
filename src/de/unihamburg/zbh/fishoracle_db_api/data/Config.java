package de.unihamburg.zbh.fishoracle_db_api.data;

import java.util.HashMap;

public abstract class Config {
	
	private HashMap<String, String[]> strArrays;
	
	public Config() {
		strArrays = new HashMap<String, String[]>();
	}
	
	public void addStrArray(String key, String[] value){
		strArrays.put(key, value);
	}
	
	public String[] getStrArray(String key){
		return strArrays.get(key);
	}

	public HashMap<String, String[]> getStrArrays() {
		return strArrays;
	}
	
	public void setStrArrays(HashMap<String, String[]> strArrays) {
		this.strArrays = strArrays;
	}
	
	public HashMap<String, String[]>  getStrArray(){
		return strArrays;
	}
}
