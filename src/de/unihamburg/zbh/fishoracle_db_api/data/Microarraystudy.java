package de.unihamburg.zbh.fishoracle_db_api.data;

import java.util.Date;

public class Microarraystudy {

	private int id;
	private CnSegment segments[];
	private Chip chip;
	private TissueSample tissue;
	private Project[] Projects;
	private Date date;
	private String name;
	private String Description;
	private int chipId;
	private int organ_id;
	private int[] propertyIds;
	private int userId;
	
	public Microarraystudy(CnSegment[] segments, String name,
			String description, int chipId, int organId, int[] propertyIds,
			int userId) {
		super();
		this.segments = segments;
		this.name = name;
		Description = description;
		this.chipId = chipId;
		organ_id = organId;
		this.propertyIds = propertyIds;
		this.userId = userId;
	}

	public Microarraystudy(int id, CnSegment[] segments, Chip chip,
			TissueSample tissue, Project[] projects, Date date, String name,
			String description) {
		super();
		this.id = id;
		this.segments = segments;
		this.chip = chip;
		this.tissue = tissue;
		Projects = projects;
		this.date = date;
		this.name = name;
		Description = description;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getChipId() {
		return chipId;
	}

	public void setChipId(int chipId) {
		this.chipId = chipId;
	}

	public int getOrgan_id() {
		return organ_id;
	}

	public void setOrgan_id(int organId) {
		organ_id = organId;
	}

	public int[] getPropertyIds() {
		return propertyIds;
	}

	public void setPropertyIds(int[] propertyIds) {
		this.propertyIds = propertyIds;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
