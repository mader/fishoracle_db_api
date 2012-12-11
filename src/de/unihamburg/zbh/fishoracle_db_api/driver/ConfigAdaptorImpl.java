package de.unihamburg.zbh.fishoracle_db_api.driver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import com.mysql.jdbc.Connection;

import de.unihamburg.zbh.fishoracle_db_api.data.Attribute;
import de.unihamburg.zbh.fishoracle_db_api.data.ConfigData;
import de.unihamburg.zbh.fishoracle_db_api.data.TrackData;

public class ConfigAdaptorImpl extends BaseAdaptor implements ConfigAdaptor {

	protected ConfigAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}
	
	@Override
	protected String[] tables() {
		return new String[]{"config"};
	}

	@Override
	protected String[] columns() {
		return new String[]{"config.config_id",
				"config.user_id",
				"config.ensembl_db_id",
				"config.name"};
	}

	@Override
	protected String[][] leftJoins() {
		return null;
	}

	@Override
	public Object createObject(ResultSet rs) {
		
		int id = 0;
		int userId = 0;
		int ensemblDBId = 0;
		String name = "";
		ConfigData cd = null;
		
		try {
			if(rs.next()){
				id = rs.getInt(1);
				userId = rs.getInt(2);
				ensemblDBId = rs.getInt(3);
				name = rs.getString(4);
				
				cd = new ConfigData();
				cd.setId(id);
				cd.setUserId(userId);
				cd.setEnsemblDBId(ensemblDBId);
				cd.setName(name);
				
				ConfigAttributeAdaptor caa = driver.getConfigAttributeAdaptor();
				
				String[] keys = caa.fetchKeysForConfigId(id, true);
				
				String[] arr = null;
				Attribute[] a;
				
				for(int i = 0; i < keys.length; i++){
					a = caa.fetchAttribute(keys[i], id, true);
						
					arr = new String[a.length];
						
					for(int j = 0; j < a.length; j++){
						arr[j] = a[j].getValue();
					}
						
					cd.addStrArray(keys[i], arr);
				}
				
				TrackConfigAdaptor tca = driver.getTrackConfigAdaptor();
				
				TrackData[] td = tca.fetchTrackConfigForConfigId(id);
				
				cd.setTracks(td);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cd;
	}
	
	@Override
	public int storeConfig(ConfigData cd) {
		
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		int newConfigId = 0;
		
		try{
			
			conn = getConnection();
			
			query.append("INSERT INTO config")
			.append("(user_id, " +
					"ensmebl_db_id) ")
			.append(" VALUES ")
			.append("('" + cd.getUserId() + 
					"', '" + cd.getEnsemblDBId() + "')");
			
			ResultSet rs = executeUpdateGetKeys(conn, query.toString());
			
			if(rs.next()){
				newConfigId = rs.getInt(1);
			}
			
			rs.close();
			
			ConfigAttributeAdaptor caa = driver.getConfigAttributeAdaptor();
			Attribute a;
			int attribId = 0;
			
			Map<String, String[]> map = cd.getStrArrays();
			for(Map.Entry<String, String[]> entry : map.entrySet()){
				
				String key = entry.getKey();
				String[] val = entry.getValue();
				
				for(int j = 0; j < val.length; j++){
					
					if((a = caa.fetchAttribute(key, val[j])) == null){
						attribId = caa.storeAttribute(key, val[j]);
					} else {
						attribId = a.getId();
					}
					
					query.append("INSERT INTO attrib_in_config")
					.append("(config_id, " +
							"config_attribute_id )")  
					.append(" VALUES ")
					.append("('" + cd.getId() + 
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
		return newConfigId;
	}

	@Override
	public ConfigData fetchConfigById(int configId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		ConfigData cd = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" WHERE ").append("config_id = '" + configId + "'")
			.append(" ORDER BY config_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			if ((o = createObject(rs)) != null) {
				cd = (ConfigData) o;
			}
			
			rs.close();
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return cd;
	}

	@Override
	public ConfigData[] fetchConfigForUserId(int userId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		ConfigData cd = null;
		ArrayList<ConfigData> cdContainer = new ArrayList<ConfigData>();
		ConfigData[] cds = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName());
			query.append(" WHERE ").append("user_id = '" + userId + "'");
			query.append(" ORDER BY name ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				cd = (ConfigData) o;
				cdContainer.add(cd);
			}
			
			rs.close();
			
			cds = new ConfigData[cdContainer.size()];
			
			cdContainer.toArray(cds);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return cds;
	}

	@Override
	public void deleteConfig(int configId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			query.append("DELETE FROM ")
			.append(super.getPrimaryTableName())
			.append(" WHERE ").append("config_id = " + configId);
			
			executeUpdate(conn, query.toString());
			
			query.append("DELETE FROM ")
			.append("attrib_in_config")
			.append(" WHERE ").append("config_id = " + configId);
			
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
