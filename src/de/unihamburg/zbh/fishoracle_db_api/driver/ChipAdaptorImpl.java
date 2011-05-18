package de.unihamburg.zbh.fishoracle_db_api.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.unihamburg.zbh.fishoracle_db_api.data.Chip;

public class ChipAdaptorImpl extends BaseAdaptor implements ChipAdaptor {

	protected ChipAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}

	@Override
	protected String[] columns() {
		return new String[]{"chip_id",
							"chip_name",
							"chip_type"};
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
	protected String[] tables() {
		return new String[]{"chip"};
	}

	@Override
	public void deleteChip(Chip chip) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			query.append("DELETE FROM ")
			.append(getPrimaryTableName())
			.append(" WHERE ").append("chip_id = " + chip.getId());
			
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
	public Chip fetchChipById(int id) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Chip chip = null;
		
		try{
			
			conn = getConnection();
			
			query.append("SELECT ").append("chip_id, chip_name, chip_type")
			.append(" FROM ").append(getPrimaryTableName())
			.append(" WHERE ").append("chip_id = " + id);
			
			ResultSet userRs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(userRs)) != null) {
				chip = (Chip) o;
				
			}
			
			if(chip == null){
				
				throw new AdaptorException("A chip with ID: " + id + " does not exist.");
				
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
	
	//TODO test
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
			.append(" FROM ").append(getPrimaryTableName());
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				chip = (Chip) o;
				chipContainer.add(chip);
			}
			
			if(chip == null){

					throw new AdaptorException("There are no organs available.");
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
	public int storeChip(Chip chip) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		int nor = 0;
		
		try{
			
			conn = getConnection();
			
			query.append("INSERT INTO ").append(getPrimaryTableName())
			.append(" (chip_name, chip_type)")
			.append(" VALUES ")
			.append("('" + chip.getName() + "', '" + chip.getType()  + "')");
			
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
}
