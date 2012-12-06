package de.unihamburg.zbh.fishoracle_db_api.data;

import java.util.HashMap;

public abstract class Config {

	private HashMap<String, int[]> intArrays;
	private HashMap<String, String[]> strArrays;
	private HashMap<String, String> strVal;
	private HashMap<String, Integer> intVal;
	private HashMap<String, Double> doubleVal;
	
	public Config() {
		intArrays = new HashMap<String, int[]>();
		strArrays = new HashMap<String, String[]>();
		strVal = new HashMap<String, String>();
		intVal = new HashMap<String, Integer>();
		doubleVal = new HashMap<String, Double>();
	}

	public void addIntArray(String key, int[] value){
		intArrays.put(key, value);
	}
	
	public int[] getIntArray(String key){
		return intArrays.get(key);
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
	
	public void addIntVal(String key, int value){
		intVal.put(key, value);
	}
	
	public int getIntVal(String key){
		return intVal.get(key);
	}
	
	public void addDoubleVal(String key, double value){
		doubleVal.put(key, value);
	}
	
	public double getDoubleVal(String key){
		return doubleVal.get(key);
	}
}
