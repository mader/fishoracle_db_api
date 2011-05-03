package de.unihamburg.zbh.fishoracle_db_api.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

import de.unihamburg.zbh.fishoracle_db_api.data.util.FishOracleConnection;

public class DriverImpl implements Driver {

	private String host;
	private String database;
	private String user;
	private String password;
	private String port;
	
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
	
	void close(Connection conn){
		try {
			if(conn != null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}
	}
	
	void close(ResultSet rs){
		try {
			if(rs != null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}
	}
	
	void close(Statement s){
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
		// TODO Auto-generated method stub
	}
	
	@Override
	public synchronized void removeAdaptor(String type) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void removeAllAdaptors() {
		for (Iterator<Adaptor> iter = adaptors.values().iterator(); iter.hasNext();){
			removeAdaptor(iter.next());
		}
	}

	@Override
	public synchronized Adaptor getAdaptor(String type) {
		return adaptors.get(type) ;
	}

	@Override
	public synchronized Adaptor[] getAdaptors() {
		int length = adaptors.size();
	    Adaptor[] adaptorArray = new Adaptor[length];
	    adaptors.values().toArray(adaptorArray);
	    return adaptorArray;
	}
}
