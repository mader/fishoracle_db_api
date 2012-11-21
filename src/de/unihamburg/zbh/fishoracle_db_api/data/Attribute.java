package de.unihamburg.zbh.fishoracle_db_api.data;

public class Attribute {

	private int id;
	private String key;
	private String value;
	
	public Attribute(int id, String key, String value) {
		super();
		this.id = id;
		this.key = key;
		this.value = value;
	}
		
	public Attribute(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public int getValueAsInt() {
		return Integer.parseInt(value);
	}
	
	public double getValueAsDouble() {
		return Double.parseDouble(value);
	}
}
