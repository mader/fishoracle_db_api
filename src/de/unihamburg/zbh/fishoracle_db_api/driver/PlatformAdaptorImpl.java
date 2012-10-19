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
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

import de.unihamburg.zbh.fishoracle_db_api.data.Platform;

/**
 * @author Malte Mader
 *
 */
public class PlatformAdaptorImpl extends BaseAdaptor implements PlatformAdaptor {

	protected PlatformAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}
	
	@Override
	protected String[] tables() {
		return new String[]{"platform"};
	}
	
	@Override
	protected String[] columns() {
		return new String[]{"platform_id",
							"platform_name",
							"platform_type"};
	}
	
	@Override
	protected String[][] leftJoins() {
		return null;
	}
	
	@Override
	public int storePlatform(Platform platform) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		int newPlatformId = 0;
		
		try{
			
			conn = getConnection();
			
			query.append("INSERT INTO ").append(super.getPrimaryTableName())
			.append(" (platform_name, platform_type)")
			.append(" VALUES ")
			.append("('" + platform.getName() + "', '" + platform.getType()  + "')");
			
			ResultSet rs = executeUpdateGetKeys(conn, query.toString());
			
			if(rs.next()){
				newPlatformId = rs.getInt(1);
			}
			
			rs.close();
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return newPlatformId;
	}

	@Override	
	public Object createObject(ResultSet rs) {
		Platform platform = null;
		int id = 0;
		String name = null;
		String type = null;
		
		try {
			if(rs.next()){
				id = rs.getInt(1);
				name = rs.getString(2);
				type = rs.getString(3);
				platform = new Platform(id, name, type);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return platform;
	}

	@Override
	public Platform[] fetchAllPlatforms() {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Platform platform = null;
		ArrayList<Platform> platformContainer = new ArrayList<Platform>();
		Platform[] platforms = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("platform_id, platform_name, platform_type")
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" ORDER BY platform_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				platform = (Platform) o;
				platformContainer.add(platform);
			}
			
			rs.close();
			
			platforms = new Platform[platformContainer.size()];
			
			platformContainer.toArray(platforms);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return platforms;
	}
	
	@Override
	public String[] fetchAllTypes() {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		String[] types = null;
		ArrayList<String> typeList = new ArrayList<String>();
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("DISTINCT (platform_type)")
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" ORDER BY platform_type ASC");
			
			ResultSet typeRs = executeQuery(conn, query.toString());
			
			while(typeRs.next()){
				
				typeList.add(typeRs.getString(1));
	
			}
			
			typeRs.close();
			
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
	public Platform fetchPlatformById(int id) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Platform platform = null;
		
		try{
			
			conn = getConnection();
			
			query.append("SELECT ").append("platform_id, platform_name, platform_type")
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" WHERE ").append("platform_id = " + id);
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			if((o = createObject(rs)) != null) {
				platform = (Platform) o;
			}
			
			rs.close();
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}		
		return platform;
	}
	
	@Override
	public void deletePlatform(Platform platform){
		deletePlatform(platform.getId());
	}
	
	@Override
	public void deletePlatform(int platformId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			query.append("DELETE FROM ")
			.append(super.getPrimaryTableName())
			.append(" WHERE ").append("platform_id = " + platformId);
			
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