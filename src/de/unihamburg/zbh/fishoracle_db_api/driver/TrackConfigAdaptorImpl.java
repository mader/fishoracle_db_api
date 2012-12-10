package de.unihamburg.zbh.fishoracle_db_api.driver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.mysql.jdbc.Connection;

import de.unihamburg.zbh.fishoracle_db_api.data.Attribute;
import de.unihamburg.zbh.fishoracle_db_api.data.TrackData;

public class TrackConfigAdaptorImpl extends BaseAdaptor implements TrackConfigAdaptor {

	protected TrackConfigAdaptorImpl(FODriverImpl driver, String type) {
		super(driver, TYPE);
		
	}

	@Override
	protected String[] tables() {
		// TODO Auto-generated method stub
		return null;
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
				
				String[] keys = caa.fetchKeysForConfigId(id);
				
				String[] arr = null;
				Attribute[] a;
				String s;
				
				for(int i = 0; i < keys.length; i++){
					a = caa.fetchAttribute(keys[i], id, false);
					
					if(a.length > 1){
						
						arr = new String[a.length];
						
						for(int j = 0; j < a.length; j++){
							arr[j] = a[j].getValue();
						}
						
						td.addStrArray(keys[i], arr);
					}
					if(a.length == 1){
						s = a[0].getValue();
						
						td.addStrVal(keys[i], s);
					}
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
			
			query.append("INSERT INTO track_config")
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
					
					query.append("INSERT INTO attrib_in_track_config")
					.append("(track_config_id, " +
							"config_attribute_id )")  
					.append(" VALUES ")
					.append("('" + td.getId() + 
							"', '" + attribId + "')");
					
					rs = executeUpdateGetKeys(conn, query.toString());
					rs.close();
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrackData[] fetchTrackConfigForConfigId(int ConfigId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteTrackConfig(int trackConfigId) {
		// TODO Auto-generated method stub
		return 0;
	}
}
