package de.unihamburg.zbh.fishoracle_db_api.driver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import com.mysql.jdbc.Connection;

import de.unihamburg.zbh.fishoracle_db_api.data.Attribute;
import de.unihamburg.zbh.fishoracle_db_api.data.TrackData;

public class TrackConfigAdaptorImpl extends BaseAdaptor implements TrackConfigAdaptor {

	protected TrackConfigAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
		
	}

	@Override
	protected String[] tables() {
		return new String[]{"track_config"};
	}

	@Override
	protected String[] columns() {
		return new String[]{"track_config.track_config_id",
				"track_config.title",
				"track_config.track_number"};
	}

	@Override
	protected String[][] leftJoins() {
		return null;
	}

	@Override
	public Object createObject(ResultSet rs) {
		int id = 0;
		String title = "";
		int trackNumber = 0;
		TrackData td = null;
		
		try {
			if(rs.next()){
				id = rs.getInt(1);
				title = rs.getString(2);
				trackNumber = rs.getInt(3);
				
				td = new TrackData(title,trackNumber);
				td.setId(id);
				
				ConfigAttributeAdaptor caa = driver.getConfigAttributeAdaptor();
				
				String[] keys = caa.fetchKeysForConfigId(id, false);
				
				String[] arr = null;
				Attribute[] a;
				
				for(int i = 0; i < keys.length; i++){
					a = caa.fetchAttribute(keys[i], id, false);
						
					arr = new String[a.length];
						
					for(int j = 0; j < a.length; j++){
						arr[j] = a[j].getValue();
					}
						
					td.addStrArray(keys[i], arr);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return td;
	}
	
	@Override
	public int storeTrackConfig(TrackData td) {
		
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		int newTrackConfigId = 0;
		
		try{
			
			conn = getConnection();
			
			query.append("INSERT INTO track_config ")
			.append("(config_id, " +
					"title, " + 
					"track_number) ")
			.append(" VALUES ")
			.append("('" + td.getConfigId() + 
					"', '" + td.getTrackName() + 
					"', '" + td.getTrackNumber() + "')");
			
			ResultSet rs = executeUpdateGetKeys(conn, query.toString());
			
			if(rs.next()){
				newTrackConfigId = rs.getInt(1);
			}
			
			rs.close();
			
			ConfigAttributeAdaptor caa = driver.getConfigAttributeAdaptor();
			Attribute a;
			int attribId = 0;
			
			Map<String, String[]> map = td.getStrArrays();
			for(Map.Entry<String, String[]> entry : map.entrySet()){
				
				String key = entry.getKey();
				String[] val = entry.getValue();
				
				for(int j = 0; j < val.length; j++){
					
					if((a = caa.fetchAttribute(key, val[j])) == null){
						attribId = caa.storeAttribute(key, val[j]);
					} else {
						attribId = a.getId();
					}
					caa.addAttributeToConfig(attribId, td.getId(), false);
				}
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return newTrackConfigId;
	}

	@Override
	public TrackData fetchTrackConfigById(int trackConfigId) {
		
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		TrackData td = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" WHERE ").append("trackl_config_id = '" + trackConfigId + "'")
			.append(" ORDER BY track_config_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			if ((o = createObject(rs)) != null) {
				td = (TrackData) o;
			}
			
			rs.close();
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return td;
	}

	@Override
	public TrackData[] fetchTrackConfigForConfigId(int ConfigId) {
		
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		TrackData td = null;
		ArrayList<TrackData> tdContainer = new ArrayList<TrackData>();
		TrackData[] tds = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName());
			query.append(" WHERE ").append("config_id = '" + ConfigId + "'");
			query.append(" ORDER BY track_number ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				td = (TrackData) o;
				tdContainer.add(td);
			}
			
			rs.close();
			
			tds = new TrackData[tdContainer.size()];
			
			tdContainer.toArray(tds);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return tds;
	}

	@Override
	public void deleteTrackConfig(int trackConfigId) {
		
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			query.append("DELETE FROM ")
			.append(super.getPrimaryTableName())
			.append(" WHERE ").append("track_config_id = " + trackConfigId);
			
			executeUpdate(conn, query.toString());
			
			query.append("DELETE FROM ")
			.append("attrib_in_track_config")
			.append(" WHERE ").append("track_config_id = " + trackConfigId);
			
			executeUpdate(conn, query.toString());
			
			//TODO remove attributes if there are no references to them left...
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
	}
}
