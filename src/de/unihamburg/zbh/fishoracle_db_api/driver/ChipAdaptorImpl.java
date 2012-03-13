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

import de.unihamburg.zbh.fishoracle_db_api.data.Chip;

/**
 * @author Malte Mader
 *
 */
public class ChipAdaptorImpl extends BaseAdaptor implements ChipAdaptor {

	protected ChipAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}
	
	@Override
	protected String[] tables() {
		return new String[]{"chip"};
	}
	
	@Override
	protected String[] columns() {
		return new String[]{"chip_id",
							"chip_name",
							"chip_type"};
	}
	
	@Override
	public int storeChip(Chip chip) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		int newChipId = 0;
		
		try{
			
			conn = getConnection();
			
			query.append("INSERT INTO ").append(super.getPrimaryTableName())
			.append(" (chip_name, chip_type)")
			.append(" VALUES ")
			.append("('" + chip.getName() + "', '" + chip.getType()  + "')");
			
			ResultSet rs = executeUpdateGetKeys(conn, query.toString());
			
			if(rs.next()){
				newChipId = rs.getInt(1);
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return newChipId;
	}

	@Override	
	public Object createObject(ResultSet rs) {
		Chip chip = null;
		int id = 0;
		String name = null;
		String type = null;
		
		try {
			if(rs.next()){
				id = rs.getInt(1);
				name = rs.getString(2);
				type = rs.getString(3);
				chip = new Chip(id, name, type);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chip;
	}

	@Override
	public Chip[] fetchAllChips() {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Chip chip = null;
		ArrayList<Chip> chipContainer = new ArrayList<Chip>();
		Chip[] chips = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("chip_id, chip_name, chip_type")
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" ORDER BY chip_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				chip = (Chip) o;
				chipContainer.add(chip);
			}
			
			chips = new Chip[chipContainer.size()];
			
			chipContainer.toArray(chips);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return chips;
	}
	
	@Override
	public String[] fetchAllTypes() {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		String[] types = null;
		ArrayList<String> typeList = new ArrayList<String>();
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("DISTINCT (chip_type)")
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" ORDER BY chip_type ASC");
			
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
	public Chip fetchChipById(int id) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Chip chip = null;
		
		try{
			
			conn = getConnection();
			
			query.append("SELECT ").append("chip_id, chip_name, chip_type")
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" WHERE ").append("chip_id = " + id);
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(userRs)) != null) {
				chip = (Chip) o;
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}		
		return chip;
	}
	
	@Override
	public void deleteChip(Chip chip){
		deleteChip(chip.getId());
	}
	
	@Override
	public void deleteChip(int chipId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			query.append("DELETE FROM ")
			.append(super.getPrimaryTableName())
			.append(" WHERE ").append("chip_id = " + chipId);
			
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
