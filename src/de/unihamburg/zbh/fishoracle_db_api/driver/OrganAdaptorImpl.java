package de.unihamburg.zbh.fishoracle_db_api.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.unihamburg.zbh.fishoracle_db_api.data.Organ;

public class OrganAdaptorImpl extends BaseAdaptor implements OrganAdaptor{

	protected OrganAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}

	@Override
	protected String[] columns() {
		return new String[]{"organ_id",
							"organ_label",
							"organ_type",
							"organ_activity"};
	}

	@Override
	public Object createObject(ResultSet rs) {
		
		Organ organ = null;
		int id = 0;
		String label = null;
		String type = null;
		String activity = null;
		
		try {
			if(rs.next()){
				id = rs.getInt(1);
				label = rs.getString(2);
				type = rs.getString(3);
				activity = rs.getString(4);
				organ = new Organ(id, label, type, activity);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return organ;
	}

	@Override
	protected String[] tables() {
		return new String[]{"organ"};
	}

	@Override
	public void deleteOrgan(Organ organ) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			query.append("DELETE FROM ")
			.append(getPrimaryTableName())
			.append(" WHERE ").append("organ_id = " + organ.getId());
			
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
	public Organ[] fetchAllOrgans() {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Organ organ = null;
		ArrayList<Organ> organContainer = new ArrayList<Organ>();
		Organ[] organs = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("organ_id, organ_label, organ_type, organ_activity")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" ORDER BY organ_id ASC");
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(userRs)) != null) {
				organ = (Organ) o;
				organContainer.add(organ);
			}
			
			if(organ == null){

					throw new AdaptorException("There are no organs available.");
			}
			
			organs = new Organ[organContainer.size()];
			
			organContainer.toArray(organs);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return organs;
	}

	@Override
	public Organ fetchOrganById(int id) {
		
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Organ organ = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("organ_id, organ_label, organ_type, organ_activity")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" WHERE ").append("organ_id = " + id);
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(userRs)) != null) {
				organ = (Organ) o;
				
			}
			
			if(organ == null){
				
				throw new AdaptorException("An organ with ID: " + id + " does not exist.");
				
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return organ;
	}

	@Override
	public Organ[] fetchOrgansByType(String type) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Organ organ = null;
		ArrayList<Organ> organContainer = new ArrayList<Organ>();
		Organ[] organs = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("organ_id, organ_label, organ_type, organ_activity")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" WHERE ").append("organ_type = '" + type + "'")
			.append(" ORDER BY organ_id ASC");
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(userRs)) != null) {
				organ = (Organ) o;
				organContainer.add(organ);
			}
			
			if(organ == null){
				
				throw new AdaptorException("An organ with type: " + type + " does not exist.");
				
			}
			
			organs = new Organ[organContainer.size()];
			
			organContainer.toArray(organs);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return organs;
	}

	@Override
	public Organ[] fetchOrgans(boolean enabled) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Organ organ = null;
		ArrayList<Organ> organContainer = new ArrayList<Organ>();
		Organ[] organs = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("organ_id, organ_label, organ_type, organ_activity")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" WHERE ");
			
			if(enabled){
				query.append("organ_activity = 'enabled'");
				
			} else {
				query.append("organ_activity = 'disabled'");
				
			}
			
			query.append(" ORDER BY organ_id ASC");
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(userRs)) != null) {
				organ = (Organ) o;
				organContainer.add(organ);
			}
			
			if(organ == null){
				
				if(enabled){
					throw new AdaptorException("There are no enabled organs available.");
				} else {
					throw new AdaptorException("There are no disabled organs available.");
				}
			}
			
			organs = new Organ[organContainer.size()];
			
			organContainer.toArray(organs);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return organs;
	}
	
	@Override
	public int storeOrgan(Organ organ) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		int newOrganId = 0;
		
		try{
			
			conn = getConnection();
			
			query.append("INSERT INTO ").append(getPrimaryTableName())
			.append(" (organ_label, organ_type, organ_activity)")
			.append(" VALUES ")
			.append("('" + organ.getLabel() + "', '" + organ.getType() + "', '" + organ.getActivty() + "')");
			
			ResultSet rs = executeUpdateGetKeys(conn, query.toString());
			
			if(rs.next()){
				newOrganId = rs.getInt(1);
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return newOrganId;
	}

	@Override
	public String[] fetchAllTypes() {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		String[] types = null;
		ArrayList<String> typeList = new ArrayList<String>();
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("DISTINCT (organ_type)")
			.append(" FROM ").append(getPrimaryTableName());
			
			ResultSet typeRs = executeQuery(conn, query.toString());
			
			while(typeRs.next()){
				
				typeList.add(typeRs.getString(1));

			}
			
			types = new String[typeList.size()];
			
			typeList.toArray(types);
			
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return types;
	}
}
