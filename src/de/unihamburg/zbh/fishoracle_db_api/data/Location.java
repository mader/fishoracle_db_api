package de.unihamburg.zbh.fishoracle_db_api.data;

public class Location {

	private String chrosmome;
	private int start;
	private int end;
	
	public Location(String chrosmome, int start, int end) {
		super();
		this.chrosmome = chrosmome;
		this.start = start;
		this.end = end;
	}

	public String getChrosmome() {
		return chrosmome;
	}

	public void setChrosmome(String chrosmome) {
		this.chrosmome = chrosmome;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
}
