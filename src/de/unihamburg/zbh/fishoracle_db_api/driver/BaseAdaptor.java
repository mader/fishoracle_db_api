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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import com.mysql.jdbc.Connection;

public abstract class BaseAdaptor implements Adaptor{

	private final static Logger logger = Logger.getLogger(BaseAdaptor.class
		      .getName());
	
	protected FODriverImpl driver;
	private String featureType;
	
	protected BaseAdaptor(FODriverImpl driver, String type) {
		this.driver = driver;
		this.featureType = type;
	}
	
	public final FODriver getDriver() {
		return driver;
	}
	
	public static void close(Connection conn){
		FODriverImpl.close(conn);
	}
	
	public static void close(ResultSet rs){
		FODriverImpl.close(rs);
	}
	
	public static void rollback(Connection conn) {
		try {
			if (conn != null) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}
	}
	
	public static int executeUpdate(Connection conn, String sql){
		int nor = 0;
		try {
			nor = conn.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}
		return nor;
	}
	
	public static ResultSet executeUpdateGetKeys(Connection conn, String sql){
		ResultSet rs = null;
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}
		return rs;
	}
	
	public static int executeUpdate(PreparedStatement ps, String sql){
		int nor = 0;
		try {
			nor = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}
		return nor;
	}
	
	public static ResultSet executeQuery(Connection conn, String sql){
		ResultSet rs = null;
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}
		return rs;
	}
	
	public static ResultSet executeQuery(PreparedStatement ps, String sql) {
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}
		return rs;
	}

	public Connection getConnection(){
		return driver.getConnection();
	}
	
	@Override
	public String getType() {
		return featureType;
	}
	
	protected abstract String[] tables();
	
	protected abstract String[] columns();
	
	protected abstract String[][] leftJoins();
	
	public abstract Object createObject(ResultSet rs);
	
	public String leftJoinToString(String[][] leftJoins) {
		
		int i;
		String qry = "";
		String[] innerArr;
		for(i = 0; i < leftJoins.length; i++)
		{
			qry += " LEFT JOIN ";
			innerArr = leftJoins[i];
			qry += innerArr[0];
			qry += " ON ";
			qry += innerArr[1];
		}
		return qry;
	}
	
	public String columnsToString(String[] columns) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < columns.length; i++) {
			result.append(columns[i]);
			if (i < columns.length - 1) {
				result.append(", ");
			}
		}
		return result.toString();
	}
	
	public String tablesToString(String[] tables) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < tables.length; i++) {
			result.append(tables[i]);
			if (i < tables.length - 1) {
				result.append(", ");
			}
		}
		return result.toString();
	}
	
	public String getBaseQuery() {
		String qry;
		int i;
		
		qry = "SELECT ";
		
		for(i = 0; i < columns().length; i++)
		{
			qry += columns()[i];
			if(i + 1 != columns().length)
			{
				qry += ", ";
			}
		}
		
		qry += " FROM ";
		qry += getPrimaryTableName();
		String leftJoins = leftJoinToString(leftJoins());
		qry += leftJoins;
		
		return qry;
	}
	
	public String getPrimaryTableName() {
		String[] tables = tables();
		return tables[0];
	}
	
	public String getArrayFilterSQLWhereClause(String column , int[] filter){
		String filterStr = "";
		if(filter != null && filter.length > 0){
			for(int i = 0; i < filter.length; i++){
				if(i == 0){
					filterStr = " " + column + " = '" + (filter[i]) + "'";
				} else {
					filterStr = filterStr + " OR " + column + " = '" + (filter[i]) + "'";
				}
			}
			filterStr = " AND (" + filterStr + ")";
		}
		return filterStr;
	}
	
	public String getArrayFilterSQLWhereClause(String column , String[] filter){
		String filterStr = "";
		if(filter != null && filter.length > 0){
			for(int i = 0; i < filter.length; i++){
				if(i == 0){
					filterStr = " " + column + " = '" + (filter[i]) + "'";
				} else {
					filterStr = filterStr + " OR " + column + " = '" + (filter[i]) + "'";
				}
			}
			filterStr = " AND (" + filterStr + ")";
		}
		return filterStr;
	}
	
	public String getMaxiamlOverlappingSQLWhereClause(int start, int end){
		StringBuffer query = new StringBuffer();
		
		query.append(" AND ((start <= " + start) 
		.append(" AND end >= " + end + ")")
		.append(" OR ")
		.append("(start >= " + start)
		.append(" AND end <= " + end + ")")
		.append(" OR ")
	    .append("(start >= " + start)
	    .append(" AND start <= " + end + ")")
	    .append(" OR ")
	    .append("(end >= " + start)
	    .append(" AND end <= " + end + "))");
		return query.toString();
	}
	
	public String getScoreSQLWhereClause(String column, Double score, boolean greaterThan) {
		
		String qrystr = "";
		String order;
		
		if(greaterThan){
			order = " > ";
		} else {
			order = " < ";
		}
		
		qrystr = qrystr + " AND " + column + order + "'" + score + "'"; 
		
		return qrystr;
	}
	
	public void truncateTable(String table){
		String query = null;
		Connection conn = null;

		try {
			conn = getConnection();
			query = "TRUNCATE TABLE "+ table;

			executeUpdate(conn, query);

			query = "ALTER TABLE "+ table + " AUTO_INCREMENT = 1";
			
			executeUpdate(conn, query);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		} finally {
			close(conn);
		}
	}
		
	public long fetchCount(){
		return fetchCount(this.getPrimaryTableName(),"");
	}
	
	public long fetchCount(String tablename, String constraint){
		long result = 0;
		String query = null;
		Connection conn = null;
		
		try {
			conn = getConnection();
			query = "SELECT count(distinct(" + tablename + "_id))" + " FROM "+ tablename;
			if(!constraint.equals("")){
				query += " WHERE " + constraint;
			}
			ResultSet rs = executeQuery(conn, query);
			if (rs.next())
				result = rs.getLong(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		} finally {
			close(conn);
		}
		return result;
	}
}