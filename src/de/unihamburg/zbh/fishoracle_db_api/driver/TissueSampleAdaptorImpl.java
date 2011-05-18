package de.unihamburg.zbh.fishoracle_db_api.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.unihamburg.zbh.fishoracle_db_api.data.Organ;
import de.unihamburg.zbh.fishoracle_db_api.data.Property;
import de.unihamburg.zbh.fishoracle_db_api.data.TissueSample;

public class TissueSampleAdaptorImpl extends BaseAdaptor implements TissueSampleAdaptor {

	protected TissueSampleAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}

	@Override
	protected String[] columns() {
		return new String[]{"tissue_sample.tissue_sample_id",
							"organ.organ_id", 
							"organ.organ_label",
							"organ.organ_type",
							"organ.organ_activity",
							"property.property_id",
							"property.property_label",
							"property.property_type",
							"property.property_activity"};
	}

	@Override
	public Object createObject(ResultSet rs) {
		TissueSample tissue = null;
		Property[] properties;
		
		int tissueId = 0;
		int organId = 0;
		String organLabel = null;
		String organType = null;
		String organActivity = null;
		
		try {
			if(rs.next()){
				tissueId = rs.getInt(1);
				organId = rs.getInt(2);
				organLabel = rs.getString(3);
				organType = rs.getString(4);
				organActivity = rs.getString(5);
				Organ organ = new Organ(organId, organLabel, organType, organActivity);
				
				PropertyAdaptor pa = (PropertyAdaptor) driver.getAdaptor("PropertyAdaptor");
				
				properties = pa.fetchPropertiesForTissueSampleId(tissueId);
				
				tissue = new TissueSample(tissueId, organ, properties);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tissue;
	}

	@Override
	protected String[] tables() {
		return new String[]{"tissue_sample",
							"organ",
							"tissue_sample_property",
							"property"};
	}

	@Override
	public TissueSample fetchTissueSampleById(int id) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		TissueSample tissue = null;
		
		try{
			
			conn = getConnection();
			
			query.append("SELECT ").append("tissue_sample.tissue_sample_id," +
											" organ.organ_id," +
											" organ.organ_label," +
											" organ.organ_type," +
											" organ.organ_activity")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" LEFT JOIN ").append("organ ON tissue_sample.tissue_sample_organ_id = organ.organ_id")
			.append(" WHERE ").append("tissue_sample.tissue_sample_id = " + id);
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(userRs)) != null) {
				tissue = (TissueSample) o;
				
			}
			
			if(tissue == null){
				
				throw new AdaptorException("A tissue sample with ID: " + id + " does not exist.");
				
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return tissue;
	}
	
	public int storeTissueSample(TissueSample tissue) {
		
		int[] propertyIds = new int[tissue.getProperties().length];
		
		for(int i = 0; i < tissue.getProperties().length; i++){
			propertyIds[i] = tissue.getProperties()[i].getId();
		}
		return storeTissueSample(tissue.getOrgan().getId(), propertyIds);
	}
	
	@Override
	public int storeTissueSample(int organ_id, int[] propertyIds) {
		Connection conn = null;
		StringBuffer tissueQuery = new StringBuffer();
		StringBuffer tissuePropertyQuery = new StringBuffer();
		ResultSet rs;
		int newTissueSampleId = 0;
		
		try{
			
			conn = getConnection();
			
			tissueQuery.append("INSERT INTO ").append(getPrimaryTableName())
			.append(" (tissue_sample_organ_id)")
			.append(" VALUES ")
			.append("('" + organ_id + "')");
			
			rs = executeUpdateGetKeys(conn, tissueQuery.toString());
			
			if(rs.next()){
				newTissueSampleId = rs.getInt(1);
			}
			
			int i;
			for(i = 0; i < propertyIds.length; i++){
			
				tissuePropertyQuery.append("INSERT INTO ").append("tissue_sample_property")
				.append(" (tissue_sample_id, property_id)").append(" VALUES ")
				.append("( " + newTissueSampleId + ", " + propertyIds[i] +  " )");
				
				executeUpdate(conn, tissuePropertyQuery.toString());
			
				tissuePropertyQuery.delete(0, tissuePropertyQuery.length());
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return newTissueSampleId;
	}
	
		@Override
	public void deleteTissueSample(TissueSample tissue) {
			Connection conn = null;
			StringBuffer tissueQuery = new StringBuffer();
			StringBuffer tissuePropertyQuery = new StringBuffer();
			
			try{
				
				conn = getConnection();
				
				tissuePropertyQuery.append("DELETE FROM ").append("tissue_sample_property")
				.append(" WHERE ").append("tissue_sample_id = " + tissue.getId());

				executeUpdate(conn, tissuePropertyQuery.toString());
				
				tissueQuery.append("DELETE FROM ")
				.append(getPrimaryTableName())
				.append(" WHERE ").append("tissue_sample_id = " + tissue.getId());

				executeUpdate(conn, tissueQuery.toString());
				
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				if(conn != null){
					close(conn);
				}
			}
	}

}
