package de.unihamburg.zbh.fishoracle_db_api.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.unihamburg.zbh.fishoracle_db_api.data.CnSegment;
import de.unihamburg.zbh.fishoracle_db_api.data.Location;

public class CnSegmentAdaptorImpl extends BaseAdaptor implements CnSegmentAdaptor {

	protected CnSegmentAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}

	@Override
	protected String[] columns() {
		return new String[]{"cn_segment_id",
							"cn_segment_chromosome",
							"cn_segment_start",
							"cn_segment_end",
							"cn_segment_mean",
							"cn_segment_markers",
							"cn_segment_microarraystudy_id"};
	}

	@Override
	public Object createObject(ResultSet rs) {
		CnSegment segment = null;
		int id = 0;
		String chromosome = null;
		int start = 0;
		int end = 0;
		double mean = 0;
		int numberOfMarkers = 0;
		int studyId = 0;
		
		try {
			if(rs.next()){
				id = rs.getInt(1);
				chromosome = rs.getString(2);
				start = rs.getInt(3);
				end = rs.getInt(4);
				mean = rs.getDouble(5);
				numberOfMarkers = rs.getInt(6);
				studyId = rs.getInt(7);
				
				segment = new CnSegment(id, chromosome, start, end, mean, numberOfMarkers, studyId);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return segment;
	}

	@Override
	protected String[] tables() {
		return new String[]{"cn_segment"};
	}

	//TODO testen
	public void deleteCnSegment(int[] microarraystudyIds ){
		for(int i= 0; i < microarraystudyIds.length; i++) {
			deleteCnSegment(microarraystudyIds[i]);
		}
		
	}
	
	//TODO testen
	public void deleteCnSegment(int microarraystudyId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			query.append("DELETE FROM ")
			.append(getPrimaryTableName())
			.append(" WHERE ").append("cn_segment_microarraystudy_id = " + microarraystudyId);
			
			executeUpdate(conn, query.toString());
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
	}
	
	@Override
	public void deleteCnSegment(CnSegment segment) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			query.append("DELETE FROM ")
			.append(getPrimaryTableName())
			.append(" WHERE ").append("cn_segment_id = " + segment.getId());
			
			executeUpdate(conn, query.toString());
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
	}

	@Override
	public CnSegment fetchCnSegmentById(int id) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		CnSegment segment = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ")
			.append("cn_segment_id, " +
					"cn_segment_chromosome, " +
					"cn_segment_start, " +
					"cn_segment_end, " +
					"cn_segment_mean, " +
					"cn_segment_markers, " +
					"cn_segment_microarraystudy_id")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" WHERE ").append("cn_segment_id = " + id);
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(userRs)) != null) {
				segment = (CnSegment) o;
				
			}
			
			if(segment == null){
				
				throw new AdaptorException("A segment with ID: " + id + " does not exist.");
				
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return segment;
	}

	//TODO Test
	@Override
	public void storeCnSegments(CnSegment[] segments, int mstudyId) {
		for(int i = 0; i < segments.length; i++){
			storeCnSegment(segments[i], mstudyId);
		}
	}
	
	@Override
	public int storeCnSegment(CnSegment segment, int mstudyId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		int nor = 0;
		
		try{
			
			conn = getConnection();
			
			query.append("INSERT INTO ").append(getPrimaryTableName())
			.append(" (cn_segment_chromosome, " +
					"cn_segment_start, " +
					"cn_segment_end, " +
					"cn_segment_mean, " +
					"cn_segment_markers, " +
					"cn_segment_microarraystudy_id)")
			.append(" VALUES ")
			.append("('" + segment.getChromosome() + 
					"', '" + segment.getStart() + 
					"', '" + segment.getEnd() +
					"', '" + segment.getMean() +
					"', '" + segment.getNumberOfMarkers() + 
					"', '" + mstudyId + "')");
			
			nor = executeUpdate(conn, query.toString());
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return nor;
	}

	@Override
	public Location fetchLocationForCnSegmentId(int id) {
		Connection conn = null;
		Location loc = null;
		StringBuffer query = new StringBuffer();
		try{
			
			conn = getConnection();
			
			query.append("SELECT ").append("cn_segment_chromosome, cn_segment_start, cn_segment_end")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" WHERE ").append("cn_segment_id = " + id);
				
			ResultSet rs = executeQuery(conn, query.toString());
			
			int start = 0;
			int end = 0;
			String chr = null;
			while(rs.next()){
				chr = rs.getString(1);
				start = rs.getInt(2);
				end = rs.getInt(3);
				
				loc = new Location(chr, start, end);
			}
				
			if(loc == null){
					
				AdaptorException e = new AdaptorException("Couldn't find the segment with the ID " + id);
				throw e;
			}
				
		} catch (AdaptorException e){
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null){
					close(conn);
			
			}
		}
			
		return loc;
	}

	private String getMaxiamlOverlappingSQLWhereClause(int start, int end){
		StringBuffer query = new StringBuffer();
		
		query.append(" AND ((cn_segment_start <= " + start) 
		.append(" AND cn_segment_end >= " + end + ")")
		.append(" OR ")
		.append("(cn_segment_start >= " + start)
		.append(" AND cn_segment_end <= " + end + ")")
		.append(" OR ")
	    .append("(cn_segment_start >= " + start)
	    .append(" AND cn_segment_start <= " + end + ")")
	    .append(" OR ")
	    .append("(cn_segment_end >= " + start)
	    .append(" AND cn_segment_end <= " + end + "))");
		return query.toString();
	}
	
	private String getThresholdSQLClause(Double lowerTh, Double upperTh){
		String qrystr = "";
		if(lowerTh == null && upperTh != null){
			qrystr = qrystr + " AND cn_segment_mean > '" + upperTh + "'"; 
		} 
		if (lowerTh != null && upperTh == null){
			qrystr = qrystr + " AND cn_segment_mean < '" + lowerTh + "'";
		} 
		if (lowerTh != null && upperTh != null){
			qrystr = qrystr + " AND (cn_segment_mean < '" + lowerTh + "' AND " +
			"cn_segment_mean > '" + upperTh + "')";
		}
		return qrystr;
	}
	
	private String getProjectSQLClause(int[] projectFilter){
		
		String projectFilterStr = "";
		if(projectFilter.length > 0 && projectFilter != null){
			for(int i = 0; i < projectFilter.length; i++){
				if(i == 0){
					projectFilterStr = " project_id = '" + (projectFilter[i]) + "'";
				} else {
					projectFilterStr = projectFilterStr + " OR project_id = '" + (projectFilter[i]) + "'";
				}
			}
			projectFilterStr = " AND (" + projectFilterStr + ")";
		}
		return projectFilterStr;
	}
	
	private String getOrganSQLClause(int[] organFilter){
		String organFilterStr = "";
		if(organFilter.length > 0 && organFilter != null){
			for(int i = 0; i < organFilter.length; i++){
				if(i == 0){
					organFilterStr = " organ_id = '" + (organFilter[i]) + "'";
				} else {
					organFilterStr = organFilterStr + " OR organ_id = '" + (organFilter[i]) + "'";
				}
			}
			organFilterStr = " AND (" + organFilterStr + ")";
		}
		return organFilterStr;
	}
	
	private String getExperimentSQLClause(int[] experimentFilter){
		
		String experimentFilterStr = "";
		if(experimentFilter.length > 0 && experimentFilter!= null){
			for(int i = 0; i < experimentFilter.length; i++){
				if(i == 0){
					experimentFilterStr = " microarraystudy_id = '" + (experimentFilter[i]) + "'";
				} else {
					experimentFilterStr = experimentFilterStr + " OR microarraystudy_id = '" + (experimentFilter[i]) + "'";
				}
			}
			experimentFilterStr = " AND (" + experimentFilterStr + ")";
		}
		return experimentFilterStr;
	}
	
	@Override
	public Location fetchMaximalOverlappingCnSegmentRange(String chr,
															int start,
															int end,
															Double lowerTh,
															Double upperTh,
															int[] projectFilter,
															int[] organFilter,
															int[] experimentFilter) {

		Location loc = null;
		Connection conn = null;
		
		StringBuffer query = new StringBuffer();
		
		query.append("SELECT ").append("MIN(cn_segment_start) as minstart, MAX(cn_segment_end) as maxend")
		.append(" FROM ").append(getPrimaryTableName())
		.append(" LEFT JOIN microarraystudy ON microarraystudy_id = cn_segment_microarraystudy_id")
		.append(" LEFT JOIN microarraystudy_in_project ON microarraystudy_id = microarraystudy_in_project.microarraystudy_id")
		.append(" LEFT JOIN sample_on_chip ON sample_on_chip_id = microarraystudy_sample_on_chip_id")
		.append(" LEFT JOIN tissue_sample ON tissue_sample_id = sample_on_chip_tissue_sample_id")
		.append(" LEFT JOIN organ ON organ_id = tissue_sample_organ_id ")
		.append(" WHERE ") 
		.append("cn_segment_chromosome = '" + chr + "'");
		query.append(getMaxiamlOverlappingSQLWhereClause(start, end));
		query.append(getThresholdSQLClause(lowerTh, upperTh));
		query.append(getProjectSQLClause(projectFilter));
		query.append(getOrganSQLClause(organFilter));
		query.append(getExperimentSQLClause(experimentFilter));
		
		try{
			conn = getConnection();
			ResultSet rangeRs = executeQuery(conn, query.toString());
			
			int qstart = 0;
			int qend = 0;
			
			if(rangeRs.next()){
				qstart = rangeRs.getInt(1);
				qend = rangeRs.getInt(2);
			}
			
			if(qstart == 0 && qend == 0){
				loc = new Location(chr, start, end);
			} else {
				loc = new Location(chr, qstart, qend);
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		
		return loc;
	}

	@Override
	public CnSegment[] fetchCnSegments(String chr,
										int start,
										int end,
										Double lowerTh,
										Double upperTh,
										int[] projectFilter,
										int[] organFilter,
										int[] experimentFilter) {
		
		Connection conn = null;
		CnSegment  segment = null;
		ArrayList<CnSegment> segmentContainer = new ArrayList<CnSegment>();
		CnSegment[] segments = null;
		
		StringBuffer query = new StringBuffer();
		
		query.append("SELECT ").append("cn_segment_start, cn_segment_start, cn_segment_end")
		.append(" FROM ").append(getPrimaryTableName())
		.append(" LEFT JOIN microarraystudy ON microarraystudy_id = cn_segment_microarraystudy_id")
		.append(" LEFT JOIN microarraystudy_in_project ON microarraystudy_id = microarraystudy_in_project.microarraystudy_id")
		.append(" LEFT JOIN sample_on_chip ON sample_on_chip_id = microarraystudy_sample_on_chip_id")
		.append(" LEFT JOIN tissue_sample ON tissue_sample_id = sample_on_chip_tissue_sample_id")
		.append(" LEFT JOIN organ ON organ_id = tissue_sample_organ_id ")
		.append(" WHERE ") 
		.append("cn_segment_chromosome = '" + chr + "'");
		query.append(getMaxiamlOverlappingSQLWhereClause(start, end));
		query.append(getThresholdSQLClause(lowerTh, upperTh));
		query.append(getProjectSQLClause(projectFilter));
		query.append(getOrganSQLClause(organFilter));
		query.append(getExperimentSQLClause(experimentFilter));
		
		try{
			conn = getConnection();
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				segment = (CnSegment) o;
				segmentContainer.add(segment);
			}
			
			segments = new CnSegment[segmentContainer.size()];
			
			segmentContainer.toArray(segments);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		
		return segments;
	}
	
	@Override
	public CnSegment[] fetchCnSegmentsForMicroarraystudyId(int id) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		CnSegment  segment = null;
		ArrayList<CnSegment> segmentContainer = new ArrayList<CnSegment>();
		CnSegment[] segments = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("cn_segment_id, " +
											"cn_segment_chromosome, " +
											"cn_segment_start, " +
											"cn_segment_end, " +
											"cn_segment_mean, " +
											"cn_segment_markers, " +
											"cn_segment_microarraystudy_id")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" WHERE ").append("cn_segment_microarraystudy_id = '" + id + "'")
			.append(" ORDER BY cn_segment_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				segment = (CnSegment) o;
				segmentContainer.add(segment);
			}
			
			if(segment == null){
				
				throw new AdaptorException("A segment with microarraystudy ID: " + id + " does not exist.");
				
			}
			
			segments = new CnSegment[segmentContainer.size()];
			
			segmentContainer.toArray(segments);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return segments;
	}
}
