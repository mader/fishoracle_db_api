package de.unihamburg.zbh.fishoracle_db_api.data;

public class TissueSample {

	private int id;
	private Organ organ;
	private Property[] properties;
	
	public TissueSample(Organ organ, int id, Property[] properties) {
		this.id = id;
		this.organ = organ;
		this.properties = properties;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Organ getOrgan() {
		return organ;
	}

	public void setOrgan(Organ organ) {
		this.organ = organ;
	}

	public Property[] getProperties() {
		return properties;
	}

	public void setProperties(Property[] properties) {
		this.properties = properties;
	}
	
}
