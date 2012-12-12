/*
  Copyright (c) 2012 Malte Mader <mader@zbh.uni-hamburg.de>
  Copyright (c) 2012 Center for Bioinformatics, University of Hamburg

  Permission to use, copy, modify, and distribute this software for any
  purpose with or without fee is hereby granted, provided that the above
  copyright notice and this permission notice appear in all copies.

  THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
  WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
  MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
  ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
  WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
  ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
  OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
*/

package de.unihamburg.zbh.fishoracle_db_api.driver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

import de.unihamburg.zbh.fishoracle_db_api.data.Attribute;

public class ConfigAttributeAdaptorImpl extends BaseAdaptor implements ConfigAttributeAdaptor {
	
	protected ConfigAttributeAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}

	@Override
	protected String[] tables() {
		return new String[]{"config_attribute"};
	}

	@Override
	protected String[] columns() {
		return new String[]{"config_attribute.config_attribute_id",
				"config_attribute.key",
				"config_attribute.value"};
	}

	@Override
	protected String[][] leftJoins() {
		return null;
	}

	@Override
	public Object createObject(ResultSet rs) {
		
		int id = 0;
		String key = "";
		String value = "";
		Attribute a = null;
		
		try {
			if(rs.next()){
				id = rs.getInt(1);
				key = rs.getString(2);
				value = rs.getString(3);
				
				a = new Attribute(id, key, value);	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return a;
	}
	
	@Override
	public int storeAttribute(String key, String value) {
		
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		int newAttrId = 0;
		
		try{
			
			conn = getConnection();
			
			query.append("INSERT INTO config_attribute ")
			.append("(`key`, " +
					"value) ")
			.append(" VALUES ")
			.append("('" + key + 
					"', '" + value + "')");
			
			ResultSet rs = executeUpdateGetKeys(conn, query.toString());
			
			if(rs.next()){
				newAttrId = rs.getInt(1);
			}
			
			rs.close();
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return newAttrId;
	}
	
	@Override
	public int addAttributeToConfig(int attribId, int configId, boolean global){
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		int newAttrId = 0;
		
		try{
			
			conn = getConnection();
			
			if(global){
				
				query.append("INSERT INTO attrib_in_config")
				.append("(config_id, " +
						"config_attribute_id )")  
				.append(" VALUES ")
				.append("('" + configId + 
						"', '" + attribId + "')");
				
			} else {
				query.append("INSERT INTO attrib_in_track_config")
				.append("(track_config_id, " +
						"config_attribute_id )")  
						.append(" VALUES ")
						.append("('" + configId + 
						"', '" + attribId + "')");
			}
			
			ResultSet rs = executeUpdateGetKeys(conn, query.toString());
			
			if(rs.next()){
				newAttrId = rs.getInt(1);
			}
			
			rs.close();
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return newAttrId;
	}
	
	@Override
	public String[] fetchAllKeys() {
		
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		ArrayList<String> keyContainer = new ArrayList<String>();
		String[] keys = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT DISTINCT `key`")
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" ORDER BY `key` ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			while(rs.next()){
				
				keyContainer.add(rs.getString(1));
	
			}

			rs.close();
			
			keys = new String[keyContainer.size()];
			
			keyContainer.toArray(keys);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return keys;
	}

	@Override
	public String[] fetchKeysForConfigId(int configId, boolean global) {
		
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		ArrayList<String> keyContainer = new ArrayList<String>();
		String[] keys = null;
		
		try{
			
			conn = getConnection();	
			
			if(global){
				query.append("SELECT DISTINCT `key`")
				.append(" FROM ").append(super.getPrimaryTableName())
				.append(" LEFT JOIN attrib_in_config")
				.append(" ON config_attribute.config_attribute_id = attrib_in_config.config_attribute_id")
				.append(" WHERE config_id = " + configId)
				.append(" ORDER BY `key` ASC");
			} else {
				query.append("SELECT DISTINCT `key`")
				.append(" FROM ").append(super.getPrimaryTableName())
				.append(" LEFT JOIN attrib_in_track_config")
				.append(" ON config_attribute.config_attribute_id = attrib_in_track_config.config_attribute_id")
				.append(" WHERE track_config_id = " + configId)
				.append(" ORDER BY `key` ASC");
			}
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			while(rs.next()){
				
				keyContainer.add(rs.getString(1));
	
			}

			rs.close();
			
			keys = new String[keyContainer.size()];
			
			keyContainer.toArray(keys);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return keys;
		
	}

	@Override
	public Attribute fetchAttributeById(int attribId) {
		
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Attribute a = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" WHERE ").append("config_attribute_id = '" + attribId + "'")
			.append(" ORDER BY config_attribute_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			if ((o = createObject(rs)) != null) {
				a = (Attribute) o;
			}
			
			rs.close();
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return a;
		
	}

	@Override
	public Attribute fetchAttribute(String key, String value) {
		
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Attribute a = null;
		
		try{
			conn = getConnection();	
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName());
			
			query.append(" WHERE ").append("`key` = '" + key + "'");
			query.append(" AND value = '" + value + "'");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				a = (Attribute) o;
			}
			
			rs.close();
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return a;
	}
	
	@Override
	public Attribute[] fetchAttribute(String key, int configId, boolean global) {
		
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Attribute a = null;
		ArrayList<Attribute> aContainer = new ArrayList<Attribute>();
		Attribute[] attribs = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName());
			
			if(global){
				query.append(" LEFT JOIN attrib_in_config")
				.append(" ON config_attribute.config_attribute_id = attrib_in_config.config_attribute_id");
			} else {
				query.append(" LEFT JOIN attrib_in_track_config")
				.append(" ON config_attribute.config_attribute_id = attrib_in_track_config.config_attribute_id");
			}
			query.append(" WHERE ").append("`key` = '" + key + "'");
			
			if(global){
				query.append(" AND config_id = '" + configId + "'");
			} else {
				query.append(" AND track_config_id = '" + configId + "'");
			}
			query.append(" ORDER BY config_attribute_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				a = (Attribute) o;
				aContainer.add(a);
			}
			
			rs.close();
			
			attribs = new Attribute[aContainer.size()];
			
			aContainer.toArray(attribs);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return attribs;
	}

	@Override
	public void deleteAttribute(int attribId) {
		
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			query.append("DELETE FROM ")
			.append(super.getPrimaryTableName())
			.append(" WHERE ").append("config_attribute_id = " + attribId);
			
			executeUpdate(conn, query.toString());
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
	}
}
