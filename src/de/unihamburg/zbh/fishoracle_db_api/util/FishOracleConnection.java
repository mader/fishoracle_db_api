/*
  Copyright (c) 2009-2011 Malte Mader <mader@zbh.uni-hamburg.de>
  Copyright (c) 2009-2011 Center for Bioinformatics, University of Hamburg

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

package de.unihamburg.zbh.fishoracle_db_api.util;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

/**
 * Establishes a mysql connection to the fish oracle database.
 * 
 * */
public class FishOracleConnection {

	public static Connection connect(String host, String db, String user, String pw, String port) throws Exception {
		String url = "jdbc:mysql://" + host + ":" + port + "/" + db;
		String userName = user;
		String password = pw;
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		return (Connection) (DriverManager.getConnection(url,userName,password));
	}
	
	
	public static String getErrorMessage(Exception e) {
		StringBuffer s = new StringBuffer();
		if (e instanceof SQLException){
			s.append("Error message: " + e.getMessage() + "\n");
			s.append("Error code: " + ((SQLException) e).getErrorCode() + "\n");
		} else {
			s.append(e + "\n");
		}
		return (s.toString());
	}
	
	public static void printErrorMessage(Exception e){
		System.err.println(FishOracleConnection.getErrorMessage(e));
	}
}
