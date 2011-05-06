package de.unihamburg.zbh.fishoracle_db_api.data;

public class CnSegment {

	private int id;
	private String stableId;
	private String chromosome;
	private int start;
	private int end;
	private int mean;
	private int numberOfMarkers;
	
	public CnSegment(int id, String stableId, String chromosome, int start,
			int end, int mean, int numberOfMarkers) {
		this.id = id;
		this.stableId = stableId;
		this.chromosome = chromosome;
		this.start = start;
		this.end = end;
		this.mean = mean;
		this.numberOfMarkers = numberOfMarkers;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStableId() {
		return stableId;
	}

	public void setStableId(String stableId) {
		this.stableId = stableId;
	}

	public String getChromosome() {
		return chromosome;
	}

	public void setChromosome(String chromosome) {
		this.chromosome = chromosome;
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

	public int getMean() {
		return mean;
	}

	public void setMean(int mean) {
		this.mean = mean;
	}

	public int getNumberOfMarkers() {
		return numberOfMarkers;
	}

	public void setNumberOfMarkers(int numberOfMarkers) {
		this.numberOfMarkers = numberOfMarkers;
	}
}
