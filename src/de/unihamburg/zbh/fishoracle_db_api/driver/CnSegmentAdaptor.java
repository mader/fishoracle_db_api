package de.unihamburg.zbh.fishoracle_db_api.driver;

import de.unihamburg.zbh.fishoracle_db_api.data.CnSegment;
import de.unihamburg.zbh.fishoracle_db_api.data.Location;

public interface CnSegmentAdaptor {
	public int storeCnSegment(CnSegment segment, int mstudyId);
	public void storeCnSegments(CnSegment[] segments, int mstudyId);
	public CnSegment fetchCnSegmentById(int id);
	public Location fetchLocationForCnSegmentId(int id);
	public Location fetchMaximalOverlappingCnSegmentRange(String chr,
															int start,
															int end,
															Double lowerTh,
															Double upperTh,
															int[] projectFilter,
															int[] organFilter,
															int[] experimentFilter);
	public CnSegment[] fetchCnSegmentsForMicroarraystudyId(int id);
	public CnSegment[] fetchCnSegments(String chr,
										int start,
										int end,
										Double lowerTh,
										Double upperTh,
										int[] projectFilter,
										int[] organFilter,
										int[] experimentFilter);	
	public void deleteCnSegment(CnSegment segment);
	public void deleteCnSegment(int microarraystudyId);
	public void deleteCnSegment(int[] microarraystudyIds);
	
	final static String TYPE = "CnSegmentAdaptor";
}