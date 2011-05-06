package de.unihamburg.zbh.fishoracle_db_api.data;

public class Organ {

	private int id;
	private String label;
	private String type;
	private String activty;
	
	public Organ(int id, String label, String type, String activty) {
		this.id = id;
		this.label = label;
		this.type = type;
		this.activty = activty;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getActivty() {
		return activty;
	}

	public void setActivty(String activty) {
		this.activty = activty;
	}
}
