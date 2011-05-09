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
	protected String[] columns() {
		return new String[]{"property_id",
							"property_label",
							"property_type",
							"property_activity"};
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
	protected String[] tables() {
		return new String[]{"property"};
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

	@Override
	public Property[] fetchAllProperties() {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Property property = null;
		ArrayList<Property> propertyContainer = new ArrayList<Property>();
		Property[] properties = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("property_id, property_label, property_type, property_activity")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" ORDER BY property_id ASC");
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(userRs)) != null) {
				property = (Property) o;
				propertyContainer.add(property);
			}
			
			if(property == null){

					throw new AdaptorException("There are no properties available.");
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
	public Property fetchPropertyById(int id) {
		
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Property property = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("property_id, property_label, property_type, property_activity")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" WHERE ").append("property_id = " + id);
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(userRs)) != null) {
				property = (Property) o;
				
			}
			
			if(property == null){
				
				throw new AdaptorException("A property with ID: " + id + " does not exist.");
				
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
	public Property[] fetchPropertiesByType(String type) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Property property = null;
		ArrayList<Property> propertyContainer = new ArrayList<Property>();
		Property[] properties = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("property_id, property_label, property_type, property_activity")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" WHERE ").append("property_type = '" + type + "'")
			.append(" ORDER BY property_id ASC");
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(userRs)) != null) {
				property = (Property) o;
				propertyContainer.add(property);
			}
			
			if(property == null){
				
				throw new AdaptorException("A property with type: " + type + " does not exist.");
				
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
			
			query.append("SELECT ").append("property_id, property_label, property_type, property_activity")
			.append(" FROM ").append(getPrimaryTableName())
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
			
			if(property == null){
				
				if(enabled){
					throw new AdaptorException("There are no enabled properties available.");
				} else {
					throw new AdaptorException("There are no disabled properties available.");
				}
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
	public Property[] fetchPropertiesForTissueSampleId(int id) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Property property = null;
		ArrayList<Property> propertyContainer = new ArrayList<Property>();
		Property[] properties = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("property.property_id, property_label, property_type, property_activity")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" RIGHT JOIN tissue_sample_property ON tissue_sample_property.property_id = property.property_id")
			.append(" WHERE ").append("tissue_sample_property.tissue_sample_id = '" + id + "'")
			.append(" ORDER BY property_id ASC");
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(userRs)) != null) {
				property = (Property) o;
				propertyContainer.add(property);
			}
			
			if(property == null){
				
				throw new AdaptorException("No properties are associated with tissue id: " + id);
				
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
	public int storeProperty(Property property) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		int nor = 0;
		
		try{
			
			conn = getConnection();
			
			query.append("INSERT INTO ").append(getPrimaryTableName())
			.append(" (property_label, property_type, property_activity)")
			.append(" VALUES ")
			.append("('" + property.getLabel() + "', '" + property.getType() + "', '" + property.getActivty() + "')");
			
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
	public String[] fetchAllTypes() {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		String[] types = null;
		ArrayList<String> typeList = new ArrayList<String>();
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("DISTINCT (property_type)")
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
