package de.unihamburg.zbh.fishoracle_db_api.data;

public class TissueSample {

	private int id;
	private Organ organ;
	private Property[] properties;
	
	public TissueSample(int id, Organ organ, Property[] properties) {
		this.id = id;
		this.organ = organ;
		this.properties = properties;
	}
	
	public int[] getPropertyIds(){
		int[] ids = new int[properties.length];
		for(int i=0; i< properties.length; i++){
			ids[i] = properties[i].getId();
		}
		return ids;
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
