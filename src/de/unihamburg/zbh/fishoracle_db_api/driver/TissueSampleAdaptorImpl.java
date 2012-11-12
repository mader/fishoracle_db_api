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

import com.mysql.jdbc.Connection;

import de.unihamburg.zbh.fishoracle_db_api.data.Organ;
import de.unihamburg.zbh.fishoracle_db_api.data.Property;
import de.unihamburg.zbh.fishoracle_db_api.data.TissueSample;

/**
 * @author Malte Mader
 *
 */
public class TissueSampleAdaptorImpl extends BaseAdaptor implements TissueSampleAdaptor {

	protected TissueSampleAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}

	@Override
	protected String[] tables() {
		return new String[]{"tissue_sample",
							"organ",
							"tissue_sample_property",
							"property"};
	}

	@Override
	protected String[] columns() {
		return new String[]{"tissue_sample_id",
							"tissue_sample_organ_id",
							"study_id"};
	}

	@Override
	protected String[][] leftJoins() {
		return null;
	}
	
	public int storeTissueSample(TissueSample tissue) {
		
		int[] propertyIds = new int[tissue.getProperties().length];
		
		for(int i = 0; i < tissue.getProperties().length; i++){
			propertyIds[i] = tissue.getProperties()[i].getId();
		}
		return storeTissueSample(tissue.getOrgan().getId(), propertyIds, tissue.getStudyId());
	}

	@Override
	public int storeTissueSample(int organ_id, int[] propertyIds, int studyId) {
		Connection conn = null;
		StringBuffer tissueQuery = new StringBuffer();
		StringBuffer tissuePropertyQuery = new StringBuffer();
		ResultSet rs;
		int newTissueSampleId = 0;
		
		try{
			
			conn = getConnection();
			
			tissueQuery.append("INSERT INTO ").append(getPrimaryTableName())
			.append(" (tissue_sample_organ_id, " +
					"study_id)")
			.append(" VALUES ")
			.append("('" + organ_id +
					"', '" + studyId + 
					"')");
			
			rs = executeUpdateGetKeys(conn, tissueQuery.toString());
			
			if(rs.next()){
				newTissueSampleId = rs.getInt(1);
			}
			
			rs.close();
			
			int i;
			for(i = 0; i < propertyIds.length; i++){
			
				tissuePropertyQuery.append("INSERT INTO ").append("tissue_sample_property")
				.append(" (tissue_sample_id, property_id)").append(" VALUES ")
				.append("( " + newTissueSampleId + ", " + propertyIds[i] +  " )");
				
				executeUpdate(conn, tissuePropertyQuery.toString());
			
				tissuePropertyQuery.delete(0, tissuePropertyQuery.length());
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return newTissueSampleId;
	}

	@Override
	public Object createObject(ResultSet rs) {
		return createObject(rs, true);
	}
	
	public Object createObject(ResultSet rs, boolean withChildren) {
		TissueSample tissue = null;
		Organ organ = null;
		Property[] properties = null;
		
		int tissueId = 0;
		int organId = 0;
		int studyId = 0;
		
		try {
			if(rs.next()){
				tissueId = rs.getInt(1);
				organId = rs.getInt(2);
				studyId = rs.getInt(3);
				
				if(withChildren){
					
					OrganAdaptor oa = (OrganAdaptor) driver.getAdaptor("OrganAdaptor");
					
					organ = oa.fetchOrganById(organId);
					
					PropertyAdaptor pa = (PropertyAdaptor) driver.getAdaptor("PropertyAdaptor");
				
					properties = pa.fetchPropertiesForTissueSampleId(tissueId);
					
				} else {
					
					properties = new Property[0];
					
				}
				
				tissue = new TissueSample(tissueId, organ, properties);
				tissue.setStudyId(studyId);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tissue;
	}
	
	@Override
	public TissueSample fetchTissueSampleById(int tissueSampleId){
		return fetchTissueSampleById(tissueSampleId, true);
	}
	
	@Override
	public TissueSample fetchTissueSampleById(int tissueSampleId, boolean withChildren) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		TissueSample tissue = null;
		
		try{
			
			conn = getConnection();
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" WHERE ").append("tissue_sample.tissue_sample_id = " + tissueSampleId);
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs, withChildren)) != null) {
				tissue = (TissueSample) o;
				
			}
			
			rs.close();
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return tissue;
	}
	
	@Override
	public void deleteTissueSample(TissueSample tissue) {
		Connection conn = null;
		StringBuffer tissueQuery = new StringBuffer();
		StringBuffer tissuePropertyQuery = new StringBuffer();
			
		try{
				
			conn = getConnection();
				
			tissuePropertyQuery.append("DELETE FROM ").append("tissue_sample_property")
			.append(" WHERE ").append("tissue_sample_id = " + tissue.getId());

			executeUpdate(conn, tissuePropertyQuery.toString());
				
			tissueQuery.append("DELETE FROM ")
			.append(getPrimaryTableName())
			.append(" WHERE ").append("tissue_sample_id = " + tissue.getId());

			executeUpdate(conn, tissueQuery.toString());
				
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
	}
}