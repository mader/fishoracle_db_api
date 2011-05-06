package de.unihamburg.zbh.fishoracle_db_api.data;

public class Microarraystudy {

	private CnSegment segments[];
	private Chip chip;
	private TissueSample tissue;
	private Project[] Projects;
	int userId;
	
	public Microarraystudy(CnSegment[] segments, Chip chip,
			TissueSample tissue, Project[] projects, int userId) {
		super();
		this.segments = segments;
		this.chip = chip;
		this.tissue = tissue;
		Projects = projects;
		this.userId = userId;
	}

	public CnSegment[] getSegments() {
		return segments;
	}

	public void setSegments(CnSegment[] segments) {
		this.segments = segments;
	}

	public Chip getChip() {
		return chip;
	}

	public void setChip(Chip chip) {
		this.chip = chip;
	}

	public TissueSample getTissue() {
		return tissue;
	}

	public void setTissue(TissueSample tissue) {
		this.tissue = tissue;
	}

	public Project[] getProjects() {
		return Projects;
	}

	public void setProjects(Project[] projects) {
		Projects = projects;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
