package de.unihamburg.zbh.fishoracle_db_api.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.unihamburg.zbh.fishoracle_db_api.data.CnSegment;
import de.unihamburg.zbh.fishoracle_db_api.data.Location;

public class CnSegmentAdaptorImpl extends BaseAdaptor implements CnSegmentAdaptor {

	protected CnSegmentAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
		// TODO Auto-generated constructor stub
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

	@Override
	public int storeCnSegment(CnSegment segment) {
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
					"', '" + segment.getMicroarraystudyId() + "')");
			
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
			
			query.append("SELECT ").append("cnc_segment_chromosome, cnc_segment_start, cnc_segment_end")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" WHERE ").append("cnc_segment_id = " + id);
				
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
}
