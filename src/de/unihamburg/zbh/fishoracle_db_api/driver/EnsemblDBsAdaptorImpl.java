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

import de.unihamburg.zbh.fishoracle_db_api.data.EnsemblDBs;

public class EnsemblDBsAdaptorImpl  extends BaseAdaptor implements EnsemblDBsAdaptor {

	protected EnsemblDBsAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}

	@Override
	protected String[] tables() {
		return new String[]{"ensembl_dbs"};
	}

	@Override
	protected String[] columns() {
		return new String[]{"ensembl_dbs_id",
				"db_name",
				"label",
				"version"};
	}

	@Override
	protected String[][] leftJoins() {
		return null;
	}
	
	@Override
	public Object createObject(ResultSet rs) {
		EnsemblDBs edbs = null;
		int id = 0;
		String dbName = null;
		String label = null;
		int version = 0;
		
		try {
			if(rs.next()){
				id = rs.getInt(1);
				dbName = rs.getString(2);
				label = rs.getString(3);
				version = rs.getInt(4);
				edbs = new EnsemblDBs(id, dbName, label, version);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return edbs;
		
	}

	@Override
	public int storeDB(EnsemblDBs edbs) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		int newEdbsId = 0;
		
		try{
			
			conn = getConnection();
			
			query.append("INSERT INTO ").append(super.getPrimaryTableName())
			.append(" (db_name, label, version)")
			.append(" VALUES ")
			.append("('" + edbs.getDBName() + "', '" + edbs.getLabel() + "', '" + edbs.getVersion() +"')");
			
			ResultSet rs = executeUpdateGetKeys(conn, query.toString());
			
			if(rs.next()){
				newEdbsId = rs.getInt(1);
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return newEdbsId;
	}

	@Override
	public EnsemblDBs[] fetchAllDBs() {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		EnsemblDBs edbs = null;
		ArrayList<EnsemblDBs> edbsContainer = new ArrayList<EnsemblDBs>();
		EnsemblDBs[] edbss = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append(super.columnsToString(this.columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" ORDER BY version DESC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				edbs = (EnsemblDBs) o;
				edbsContainer.add(edbs);
			}
			
			edbss = new EnsemblDBs[edbsContainer.size()];
			
			edbsContainer.toArray(edbss);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return edbss;
	}

	@Override
	public EnsemblDBs fetchDBById(int id) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		EnsemblDBs edbs = null;
		
		try{
			
			conn = getConnection();
			
			query.append("SELECT ").append(super.columnsToString(this.columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" WHERE ").append("ensembl_dbs_id = " + id);
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			Object o;
			
			if((o = createObject(userRs)) != null) {
				edbs = (EnsemblDBs) o;
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}		
		return edbs;
	}
	
	@Override
	public void deleteDB(int edbsId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			query.append("DELETE FROM ")
			.append(super.getPrimaryTableName())
			.append(" WHERE ").append("ensembl_dbs_id = " + edbsId);
			
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