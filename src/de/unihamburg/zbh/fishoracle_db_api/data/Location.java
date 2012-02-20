package de.unihamburg.zbh.fishoracle_db_api.data;

public class Location {

	private String chrosmome;
	private int start;
	private int end;
	
	public Location() {
	}

	public Location(String chrosmome, int start, int end) {
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
	
	public Location maximize(Location loc) throws Exception{
		
		Location maxLoc;
		
		if(this.chrosmome.equals(loc.getChrosmome())){
		
			maxLoc = new Location();
			
			maxLoc.setChrosmome(this.chrosmome);
		
			if(this.start < loc.getStart()){
				maxLoc.setStart(this.start);
			} else {
				maxLoc.setStart(loc.getStart());
			}
		
			if(this.end > loc.getEnd()){
				maxLoc.setEnd(this.end);
			} else {
				maxLoc.setEnd(loc.getEnd());
			}
			
			} else {
				throw new Exception("Chromosomes not equal!");
			} 
		
		return maxLoc;
		
	}
}
