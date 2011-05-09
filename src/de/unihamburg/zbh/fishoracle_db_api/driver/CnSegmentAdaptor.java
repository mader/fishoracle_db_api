package de.unihamburg.zbh.fishoracle_db_api.driver;

import de.unihamburg.zbh.fishoracle_db_api.data.CnSegment;
import de.unihamburg.zbh.fishoracle_db_api.data.Location;

public interface CnSegmentAdaptor {
	public int storeCnSegment(CnSegment segment);
	public CnSegment fetchCnSegmentById(int id);
	public Location fetchLocationForCnSegmentId(int id);
	//public Location getMaxCNCRange(String chr, int start, int end, Double lowerTh, Double upperTh, String[] organFilter)
	//public CopyNumberChange[] fetchCNCData(String chr,
	//		int start, 
	//		int end, 
	//		Double lowerTh, 
	//		Double upperTh,
	//		String[] organFilter)
	
	public void deleteCnSegment(CnSegment segment);
	
	
	
	final static String TYPE = "CnSegmentAdaptor";
}