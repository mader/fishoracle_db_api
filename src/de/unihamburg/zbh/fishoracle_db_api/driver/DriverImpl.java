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


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

import com.mysql.jdbc.Connection;

import de.unihamburg.zbh.fishoracle_db_api.util.FishOracleConnection;

public class DriverImpl implements Driver {

	private String host;
	private String database;
	private String user;
	private String password;
	private String port;
	
	private String databaseName = null;
	
	private final static Logger logger = Logger.getLogger(DriverImpl.class
		      .getName());
	
	public DriverImpl(String host, String database, String user,
			String password, String port) {
		super();
		this.host = host;
		this.database = database;
		this.user = user;
		this.password = password;
		this.port = port;
	}

	private HashMap<String, Adaptor> adaptors = new HashMap<String, Adaptor>();
	
	public synchronized Connection getConnection(){
		Connection conn = null;
		try {
			conn =  FishOracleConnection.connect(host, database, user, password, port);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}
		return conn;
	}
	
	static void close(Connection conn){
		try {
			if(conn != null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}
	}
	
	static void close(ResultSet rs){
		try {
			if(rs != null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}
	}
	
	static void close(Statement s){
		try {
			if(s != null){
				s.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}
	}
	
	@Override
	public Adaptor addAdaptor(Adaptor adaptor) {
		return adaptors.put(adaptor.getType(), adaptor);
	}

	@Override
	public synchronized void removeAdaptor(Adaptor adaptor) {
		removeAdaptor(adaptor.getType());
	}
	
	@Override
	public synchronized void removeAdaptor(String type) {
		BaseAdaptor adaptor = (BaseAdaptor) adaptors.remove(type);
	    if (adaptor != null) {
	      adaptor.driver = null;
	      logger.fine(adaptor.getClass().getName() + " from FODriver removed");
	    }
	}
	
	@Override
	public void removeAllAdaptors() {
		for (Iterator<Adaptor> iter = adaptors.values().iterator(); iter.hasNext();){
			removeAdaptor(iter.next());
		}
	}

	@Override
	public synchronized Adaptor getAdaptor(String type) {
		return adaptors.get(type);
	}

	@Override
	public synchronized Adaptor[] getAdaptors() {
		int length = adaptors.size();
	    Adaptor[] adaptorArray = new Adaptor[length];
	    adaptors.values().toArray(adaptorArray);
	    return adaptorArray;
	}
	
	@Override
	public String fetchDatabaseName() {
		if (databaseName==null) {
			Connection conn = null;
			try {
				conn = getConnection(); 
				databaseName = conn.getCatalog();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(conn);
			}  
		}
		return databaseName;
	}
}