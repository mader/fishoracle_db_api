package de.unihamburg.zbh.fishoracle_db_api.data;

public class ConfigData extends Config {

	private int id;
	private int userId;
	private int ensemblDBId;
	private String name;
	private TrackData[] tracks;
	
	public ConfigData() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getEnsemblDBId() {
		return ensemblDBId;
	}

	public void setEnsemblDBId(int ensemblDBId) {
		this.ensemblDBId = ensemblDBId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TrackData[] getTracks() {
		return tracks;
	}

	public void setTracks(TrackData[] tracks) {
		this.tracks = tracks;
	}
}
