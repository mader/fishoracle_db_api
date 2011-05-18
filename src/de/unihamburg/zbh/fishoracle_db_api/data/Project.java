package de.unihamburg.zbh.fishoracle_db_api.data;

public class Project {

	private int id;
	private String name;
	private String description;
	private ProjectAccess[] projectAccess;
	private Microarraystudy[] mstudies;
	
	public Project(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Microarraystudy[] getMstudies() {
		return mstudies;
	}

	public void setMstudies(Microarraystudy[] mstudies) {
		this.mstudies = mstudies;
	}

	public ProjectAccess[] getProjectAccess() {
		return projectAccess;
	}

	public void setProjectAccess(ProjectAccess[] projectAccess) {
		this.projectAccess = projectAccess;
	}
	
}
