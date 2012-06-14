/*
  Copyright (c) 2011-2012 Malte Mader <mader@zbh.uni-hamburg.de>
  Copyright (c) 2011-2012 Center for Bioinformatics, University of Hamburg

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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.unihamburg.zbh.fishoracle_db_api.data.Property;

public class PropertyAdaptorImpl extends BaseAdaptor implements PropertyAdaptor {
	
	protected PropertyAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}

	@Override
	protected String[] tables() {
		return new String[]{"property"};
	}

	@Override
	protected String[] columns() {
		return new String[]{"property_id",
							"property_label",
							"property_type",
							"property_activity"};
	}

	@Override
	protected String[][] leftJoins() {
		return null;
	}
	
	@Override
	public int storeProperty(Property property) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		int newPropertyId = 0;
		
		try{
			
			conn = getConnection();
			
			query.append("INSERT INTO ").append(getPrimaryTableName())
			.append(" (property_label, property_type, property_activity)")
			.append(" VALUES ")
			.append("('" + property.getLabel() + "', '" + property.getType() + "', '" + property.getActivty() + "')");
			
			ResultSet rs = executeUpdateGetKeys(conn, query.toString());
			
			if(rs.next()){
				newPropertyId = rs.getInt(1);
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return newPropertyId;
	}

	@Override
	public Object createObject(ResultSet rs) {
		
		Property property = null;
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
				property = new Property(id, label, type, activity);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return property;
	}

	@Override
	public Property[] fetchAllProperties() {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Property property = null;
		ArrayList<Property> propertyContainer = new ArrayList<Property>();
		Property[] properties = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" ORDER BY property_id ASC");
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(userRs)) != null) {
				property = (Property) o;
				propertyContainer.add(property);
			}
			
			properties = new Property[propertyContainer.size()];
			
			propertyContainer.toArray(properties);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return properties;
	}

	@Override
	public Property[] fetchProperties(boolean enabled) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Property property = null;
		ArrayList<Property> propertyContainer = new ArrayList<Property>();
		Property[] properties = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" WHERE ");
			
			if(enabled){
				query.append("property_activity = 'enabled'");
				
			} else {
				query.append("property_activity = 'disabled'");
				
			}
			
			query.append(" ORDER BY property_id ASC");
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(userRs)) != null) {
				property = (Property) o;
				propertyContainer.add(property);
			}
			
			properties = new Property[propertyContainer.size()];
			
			propertyContainer.toArray(properties);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return properties;
	}

	@Override
	public Property fetchPropertyById(int propertyId) {
		
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Property property = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" WHERE ").append("property_id = " + propertyId);
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(userRs)) != null) {
				property = (Property) o;
				
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return property;
	}

	@Override
	public String[] fetchAllTypes() {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		String[] types = null;
		ArrayList<String> typeList = new ArrayList<String>();
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("DISTINCT (property_type)")
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" ORDER BY property_type ASC");
			
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

	@Override
	public Property[] fetchPropertiesByType(String type) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Property property = null;
		ArrayList<Property> propertyContainer = new ArrayList<Property>();
		Property[] properties = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" WHERE ").append("property_type = '" + type + "'")
			.append(" ORDER BY property_id ASC");
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(userRs)) != null) {
				property = (Property) o;
				propertyContainer.add(property);
			}
			
			properties = new Property[propertyContainer.size()];
			
			propertyContainer.toArray(properties);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return properties;
	}

	@Override
	public Property[] fetchPropertiesForTissueSampleId(int tissueSampleId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Property property = null;
		ArrayList<Property> propertyContainer = new ArrayList<Property>();
		Property[] properties = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" RIGHT JOIN tissue_sample_property ON tissue_sample_property.property_id = property.property_id")
			.append(" WHERE ").append("tissue_sample_property.tissue_sample_id = '" + tissueSampleId + "'")
			.append(" ORDER BY property_id ASC");
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(userRs)) != null) {
				property = (Property) o;
				propertyContainer.add(property);
			}
			
			properties = new Property[propertyContainer.size()];
						
			propertyContainer.toArray(properties);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return properties;
	}
	
	@Override
	public void deleteProperty(Property property) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			query.append("DELETE FROM ")
			.append(getPrimaryTableName())
			.append(" WHERE ").append("property_id = " + property.getId());
			
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