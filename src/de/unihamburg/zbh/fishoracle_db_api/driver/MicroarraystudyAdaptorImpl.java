package de.unihamburg.zbh.fishoracle_db_api.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import de.unihamburg.zbh.fishoracle_db_api.data.Chip;
import de.unihamburg.zbh.fishoracle_db_api.data.CnSegment;
import de.unihamburg.zbh.fishoracle_db_api.data.Microarraystudy;
import de.unihamburg.zbh.fishoracle_db_api.data.TissueSample;

public class MicroarraystudyAdaptorImpl extends BaseAdaptor implements MicroarraystudyAdaptor{

	protected MicroarraystudyAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}

	@Override
	protected String[] columns() {
		return new String[]{"microarraystudy_id",
							"microarraystudy_date_inserted",
							"microarraystudy_name",
							"microarraystudy_description",
							"microarraystudy_user_id",
							"microarraystudy_sample_on_chip_id"};
	}
	
	@Override
	public Object createObject(ResultSet rs) {
		return createObject(rs, true);
	}
	
	public Object createObject(ResultSet rs, boolean withChildren) {
		Microarraystudy mstudy = null;
		CnSegment[] segments;
		TissueSample tissue;
		Chip chip;
		
		int mstudyId = 0;
		Date date;
		String mstudyName = null;
		String mstudyDescription = null;
		int chipId = 0;
		int tissueSampleId = 0;
		
		try {
			if(rs.next()){
				mstudyId = rs.getInt(1);
				date = rs.getDate(2);
				mstudyName = rs.getString(3);
				mstudyDescription = rs.getString(4);
				chipId = rs.getInt(5);
				tissueSampleId = rs.getInt(6);
				
				mstudy = new Microarraystudy(mstudyId, date, mstudyName, mstudyDescription, 0);
				
				TissueSampleAdaptor ta = (TissueSampleAdaptor) driver.getAdaptor("TissueSampleAdaptor");
				tissue = ta.fetchTissueSampleById(tissueSampleId);
				
				if(withChildren){
					mstudy.setTissue(tissue);
				} else {
					mstudy.setOrgan_id(tissue.getOrgan().getId());
					mstudy.setPropertyIds(tissue.getPropertyIds());
				}
				
				ChipAdaptor ca = (ChipAdaptor) driver.getAdaptor("ChipAdaptor");
				chip = ca.fetchChipById(chipId);
				
				if(withChildren){
					mstudy.setChip(chip);
				} else {
					mstudy.setChipId(chip.getId());
				}
				
				CnSegmentAdaptor sa = (CnSegmentAdaptor) driver.getAdaptor("CnSegmentAdaptor");
				segments = sa.fetchCnSegmentsForMicroarraystudyId(mstudyId);
				
				if(withChildren){
					mstudy.setSegments(segments);
				} else {
					mstudy.setChipId(chip.getId());
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return mstudy;
	}

	@Override
	protected String[] tables() {
		return new String[]{"microarraystudy",
							"cn_segment",
							"sample_on_chip",
							"chip",
							"tissue_sample",
							"organ"};
	}
	
	public Microarraystudy[] fetchAllMicroarraystudies() {
		return fetchAllMicroarraystudies(false);
	}
	
	@Override
	public Microarraystudy[] fetchAllMicroarraystudies(boolean withChildren) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Microarraystudy mstudy = null;
		ArrayList<Microarraystudy> mstudyContainer = new ArrayList<Microarraystudy>();
		Microarraystudy[] mstudies = null;
		
		try{
			
			conn = getConnection();	
			
			
			query.append("SELECT ").append("microarraystudy.microarraystudy_id, " +
					"microarraystudy.microarraystudy_date_inserted, " +
					"microarraystudy.microarraystudy_name, " +
					"microarraystudy.microarraystudy_description, " +
					"sample_on_chip.sample_on_chip_chip_id, " +
					"sample_on_chip.sample_on_chip_tissue_sample_id")
					.append(" FROM ").append(getPrimaryTableName())
					.append(" LEFT JOIN ").append("sample_on_chip ON microarraystudy.microarraystudy_id = sample_on_chip.sample_on_chip_microarraystudy_id")
					.append(" LEFT JOIN ").append("tissue_sample ON sample_on_chip.sample_on_chip_tissue_sample_id = tissue_sample.tissue_sample_id");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs, withChildren)) != null) {
				mstudy = (Microarraystudy) o;
				mstudyContainer.add(mstudy);
			}
			
			mstudies = new Microarraystudy[mstudyContainer.size()];
			
			mstudyContainer.toArray(mstudies);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return mstudies;
	}

	public Microarraystudy[] fetchMicroarraystudiesForProject(int projectId) {
		return fetchMicroarraystudiesForProject(projectId, false);
	}
	
	@Override
	public Microarraystudy[] fetchMicroarraystudiesForProject(int projectId, boolean withChrildren) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Microarraystudy mstudy = null;
		ArrayList<Microarraystudy> mstudyContainer = new ArrayList<Microarraystudy>();
		Microarraystudy[] mstudies = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("microarraystudy.microarraystudy_id, " +
					"microarraystudy.microarraystudy_date_inserted, " +
					"microarraystudy.microarraystudy_name, " +
					"microarraystudy.microarraystudy_description, " +
					"sample_on_chip.sample_on_chip_chip_id, " +
					"sample_on_chip.sample_on_chip_tissue_sample_id")
					.append(" FROM ").append(getPrimaryTableName())
					.append(" LEFT JOIN ").append("sample_on_chip ON microarraystudy.microarraystudy_id = sample_on_chip.sample_on_chip_microarraystudy_id")
					.append(" LEFT JOIN ").append("microarraystudy_in_project ON microarraystudy.microarraystudy_id = microarraystudy_in_project.microarraystudy_id")
					.append(" WHERE ").append("microarraystudy_in_project.project_id = " + projectId);
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs, withChrildren)) != null) {
				mstudy = (Microarraystudy) o;
				mstudyContainer.add(mstudy);
			}
			
			mstudies = new Microarraystudy[mstudyContainer.size()];
			
			mstudyContainer.toArray(mstudies);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return mstudies;
	}
	
	public Microarraystudy fetchMicroarraystudyById(int id){
		 return fetchMicroarraystudyById(id, false);
	}
	
	@Override
	public Microarraystudy fetchMicroarraystudyById(int id, boolean withChilden) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Microarraystudy mstudy = null;
		
		try{
			
			conn = getConnection();
			
			query.append("SELECT ").append("microarraystudy.microarraystudy_id, " +
											"microarraystudy.microarraystudy_date_inserted, " +
											"microarraystudy.microarraystudy_name, " +
											"microarraystudy.microarraystudy_description, " +
											"sample_on_chip.sample_on_chip_chip_id, " +
											"sample_on_chip.sample_on_chip_tissue_sample_id")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" LEFT JOIN ").append("sample_on_chip ON microarraystudy.microarraystudy_id = sample_on_chip.sample_on_chip_microarraystudy_id")
			.append(" LEFT JOIN ").append("tissue_sample ON sample_on_chip.sample_on_chip_tissue_sample_id = tissue_sample.tissue_sample_id")
			.append(" WHERE ").append("microarraystudy.microarraystudy_id = " + id);
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs, withChilden)) != null) {
				mstudy = (Microarraystudy) o;
				
			}
			
			if(mstudy == null){
				
				throw new AdaptorException("A microarraystudy with ID: " + id + " does not exist.");
				
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return mstudy;
	}

	@Override
	public int storeMicroarraystudy(Microarraystudy mstudy, int projectId) {
		Connection conn = null;
		StringBuffer mstudyQuery = new StringBuffer();
		StringBuffer socQuery = new StringBuffer();
		StringBuffer socUpdateQuery = new StringBuffer();
		int newMstudyId = 0;
		
		try{
			
			conn = getConnection();
			
			TissueSampleAdaptor ta = (TissueSampleAdaptor) driver.getAdaptor("TissueSampleAdaptor");
			int newTissueSampleId = ta.storeTissueSample(mstudy.getOrgan_id(), mstudy.getPropertyIds());
			
			socQuery.append("INSERT INTO ").append("sample_on_chip")
			.append(" ( sample_on_chip_chip_id, " +
					"sample_on_chip_tissue_sample_id)")
			.append(" VALUES ")
			.append("(" + mstudy.getChipId() + ", ")
			.append(newTissueSampleId + ")");
			
			ResultSet socRs = executeUpdateGetKeys(conn, socQuery.toString());
			
			int newSampleOnChipId = 0;
			if(socRs.next()){
				newSampleOnChipId = socRs.getInt(1);
			}
			
			mstudyQuery.append("INSERT INTO ").append(getPrimaryTableName())
			.append(" (" +
					"microarraystudy_date_inserted, " +
					"microarraystudy_name, " +
					"microarraystudy_description, " +
					"microarraystudy_user_id, " +
					"microarraystudy_sample_on_chip_id" +
					")")
			.append(" VALUES ")
			.append("( CURDATE() " +
					", '" + mstudy.getName() + 
					"', '" + mstudy.getDescription() +
					"', '" + mstudy.getUserId() + 
					"', '" +  newSampleOnChipId +
					"')");
			
			ResultSet mstudyRs = executeUpdateGetKeys(conn, mstudyQuery.toString());
			
			if(mstudyRs.next()){
				newMstudyId = mstudyRs.getInt(1);
			}
			
		socUpdateQuery.append("UPDATE ").append("sample_on_chip")
		.append(" SET sample_on_chip_microarraystudy_id = " + newMstudyId)
		.append(" WHERE ").append("sample_on_chip_id = " + newSampleOnChipId);
			
		 executeUpdate(conn, socUpdateQuery.toString());
		 
		 CnSegmentAdaptor sa = (CnSegmentAdaptor) driver.getAdaptor("CnSegmentAdaptor");
		 sa.storeCnSegments(mstudy.getSegments(), newMstudyId);
		 
		 ProjectAdaptor pra = (ProjectAdaptor) driver.getAdaptor("ProjectAdaptor");
		 pra.addMicroarraystudyToProject(newMstudyId, projectId);
		 
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return newMstudyId;
	}

	public void deleteMicroarraystudy(int mstudyId) {
		
		Microarraystudy m = fetchMicroarraystudyById(mstudyId, true);
		deleteMicroarraystudy(m);	
	}
	
	@Override
	public void deleteMicroarraystudy(Microarraystudy mstudy) {
		Connection conn = null;
		StringBuffer socQuery = new StringBuffer();
		StringBuffer mstudyQuery = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			CnSegmentAdaptor sa = (CnSegmentAdaptor) driver.getAdaptor("CnSegmentAdaptor");
			sa.deleteCnSegment(mstudy.getId());
			
			TissueSampleAdaptor ta = (TissueSampleAdaptor) driver.getAdaptor("TissueSampleAdaptor");
			ta.deleteTissueSample(mstudy.getTissue());
			
			socQuery.append("DELETE FROM ").append("sample_on_chip")
			.append(" WHERE ").append("sample_on_chip_microarraystudy_id = " + mstudy.getId());

			executeUpdate(conn, socQuery.toString());
				
			mstudyQuery.append("DELETE FROM ")
			.append(getPrimaryTableName())
			.append(" WHERE ").append("microarraystudy_id = " + mstudy.getId());

			executeUpdate(conn, mstudyQuery.toString());
				
		} catch (Exception e){
				e.printStackTrace();
		} finally {
			if(conn != null){
					close(conn);
			}
		}
	}
}
