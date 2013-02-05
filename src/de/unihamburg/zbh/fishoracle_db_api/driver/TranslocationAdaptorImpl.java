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

import de.unihamburg.zbh.fishoracle_db_api.data.Location;
import de.unihamburg.zbh.fishoracle_db_api.data.Translocation;

/**
 * @author Malte Mader
 *
 */
public class TranslocationAdaptorImpl extends BaseAdaptor implements TranslocationAdaptor {

	protected TranslocationAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}
	
	@Override
	protected String[] tables() {
		return new String[]{"translocation"};
	}

	@Override
	protected String[] columns() {
		return new String[]{"translocation.translocation_id",
								"translocation.chromosome",
								"translocation.start",
								"translocation.end",
								"translocation.translocation_ref_id",
								"translocation.platform_id",
								"platform_name",
								"translocation.study_id"};
	}

	@Override
	protected String[][] leftJoins() {
		return new String[][]{{"study","study.study_id = translocation.study_id"},
				{"study_in_project","study.study_id = study_in_project.study_id"},
				{"platform","platform.platform_id = translocation.platform_id"},
				{"tissue_sample","tissue_sample.study_id = study.study_id"},
				{"organ","organ_id = tissue_sample_organ_id"}};
	}

	@Override
	public Object createObject(ResultSet rs) {
		
		Location loc = null;
		Translocation transloc = null;
		int id = 0;
		String chromosome = null;
		int start = 0;
		int end = 0;
		int ref_id = 0;
		int studyId = 0;

		int platformId = 0;
		String platformName = "";
		
		try {
			if(rs.next()){
				id = rs.getInt(1);
				chromosome = rs.getString(2);
				start = rs.getInt(3);
				end = rs.getInt(4);
				ref_id = rs.getInt(5);
				platformId = rs.getInt(6);
				platformName = rs.getString(7);
				studyId = rs.getInt(8);
				
				loc = new Location(chromosome, start, end);
				
				transloc = new Translocation(id, loc, ref_id);
				
				transloc.setPlatformId(platformId);
				transloc.setPlatformName(platformName);
				transloc.setStudyId(studyId);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return transloc;
	}
	
	@Override
	public int[] storeTranslocation(Translocation[] transloc, int studyId) {
		Connection conn = null;
		StringBuffer transloc_query;
		StringBuffer update_query;
		int[] newTranslocIds = new int[2];
		
		try{
			
			conn = getConnection();
			
			for(int i = 0; i < transloc.length; i++){
			
				transloc_query = new StringBuffer();
				
				transloc_query.append("INSERT INTO ").append(getPrimaryTableName())
				.append("(chromosome" +
						", start " +
						", end " +
						", translocation_ref_id" +
						", platform_id" +
						", study_id" +
						")")
						.append(" VALUES ")
						.append("('" + transloc[i].getLocation().getChromosome() +
								"', '" + transloc[i].getLocation().getStart() +
								"', '" + transloc[i].getLocation().getEnd() +
								"', '0', " + 
								"'" + transloc[i].getPlatformId() +
								"', '" + studyId + "')");
				
				ResultSet rs = executeUpdateGetKeys(conn, transloc_query.toString());
			
				if(rs.next()){
					newTranslocIds[i] = rs.getInt(1);
				}
				
				rs.close();
			}
			
			update_query = new StringBuffer();
			
			update_query.append("UPDATE ").append(getPrimaryTableName())
			.append(" SET ")
			.append("translocation_ref_id = ")
					.append("'" + newTranslocIds[0] + "'")
					.append(" WHERE translocation_id = '" + newTranslocIds[1] + "'");
			
			executeUpdateGetKeys(conn, update_query.toString());
			
			update_query = new StringBuffer();
			
			update_query.append("UPDATE ").append(getPrimaryTableName())
			.append(" SET ")
			.append("translocation_ref_id = ")
					.append("'" + newTranslocIds[1] + "'")
					.append(" WHERE translocation_id = '" + newTranslocIds[0] + "'");
					
			executeUpdateGetKeys(conn, update_query.toString());
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return newTranslocIds;
	}

	@Override
	public void storeTranslocations(Translocation[][] translocs, int studyId) {
		for(int i = 0; i < translocs.length; i++){
			storeTranslocation(translocs[i], studyId);
		}
	}

	@Override
	public Translocation[] fetchTranslocationById(int translocationId) {
		return fetchTranslocationById(translocationId, true);
	}
	
	@Override
	public Translocation[] fetchTranslocationById(int translocationId, boolean fetchReference) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Translocation translocReq = null;
		Translocation translocRef = null;
		Translocation[] transloc = new Translocation[2];
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ")
			.append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" LEFT JOIN platform ON translocation.platform_id = platform.platform_id")
			.append(" WHERE ").append("translocation_id = " + translocationId);
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				translocReq = (Translocation) o;
				
				transloc[0] = translocReq;
				
				if(fetchReference){
					translocRef = fetchTranslocationById(translocReq.getRefId(), false)[0];
					transloc[1] = translocRef;
				}
			}
			
			rs.close();
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return transloc;
	}

	@Override
	public Translocation[][] fetchTranslocationsForStudyId(int studyId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Translocation translocReq = null;
		Translocation translocRef = null;
		Translocation[] transloc = new Translocation[2];
		ArrayList<Translocation[]> translocContainer = new ArrayList<Translocation[]>();
		Translocation[][] translocs = null;
		
		try{
			
			conn = getConnection();
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" LEFT JOIN platform ON translocation.platform_id = platform.platform_id")
			.append(" WHERE ").append("study_id = '" + studyId + "'")
			.append(" AND ").append("translocation_id = (translocation_ref_id - 1)")
			.append(" ORDER BY translocation_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				translocReq = (Translocation) o;
				
				transloc[0] = translocReq;
				translocRef = fetchTranslocationById(translocReq.getRefId(), false)[0];
				transloc[1] = translocRef;
				
				translocContainer.add(transloc);
			}
			
			rs.close();
			
			translocs = new Translocation[translocContainer.size()][];
			
			translocContainer.toArray(translocs);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return translocs;
	}

	@Override
	public Translocation[][] fetchTranslocations(String chr, int start, int end,
			int[] projectFilter, int[] organFilter, int[] experimentFilter) {
		
		Connection conn = null;
		Translocation translocReq = null;
		Translocation translocRef = null;
		Translocation[]  transloc = null;
		ArrayList<Translocation[]> translocContainer = new ArrayList<Translocation[]>();
		Translocation[][] translocs = null;
		
		StringBuffer query = new StringBuffer();
		
		query.append(getBaseQuery())
		.append(" WHERE ")
		.append("chromosome = '" + chr + "'");
		query.append(getMaxiamlOverlappingSQLWhereClause(start, end));
		query.append(getArrayFilterSQLWhereClause("project_id", projectFilter));
		query.append(getArrayFilterSQLWhereClause("organ_id", organFilter));
		query.append(getArrayFilterSQLWhereClause("experiment_id", experimentFilter))
		.append(" ORDER BY translocation_id ASC");
		
		try{
			conn = getConnection();
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				
				transloc = new Translocation[2];
				
				translocReq = (Translocation) o;
				
				transloc[0] = translocReq;
				translocRef = fetchTranslocationById(translocReq.getRefId(), false)[0];
				transloc[1] = translocRef;
				
				translocContainer.add(transloc);
			}
			
			rs.close();
			
			translocs = new Translocation[translocContainer.size()][];
			
			translocContainer.toArray(translocs);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return translocs;
		
	}

	@Override
	public void deleteTranslocation(Translocation[] transloc) {
		Connection conn = null;
		StringBuffer transloc_query;
		
		try{
			
			conn = getConnection();
			
			for(int i = 0; i < transloc.length; i++){
			
				transloc_query = new StringBuffer();
			
				transloc_query.append("DELETE FROM ")
				.append(super.getPrimaryTableName())
				.append(" WHERE ").append("translocation_id = " + transloc[i].getId());
			
				executeUpdate(conn, transloc_query.toString());
			}
			
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
	}

	@Override
	public void deleteTranslocation(int studyId) {
		Connection conn = null;
		StringBuffer segment_query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			segment_query.append("DELETE FROM ")
			.append(super.getPrimaryTableName())
			.append(" WHERE ").append("study_id = " + studyId);
			
			executeUpdate(conn, segment_query.toString());
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
	}

	@Override
	public void deleteTranslocation(int[] studyIds) {
		for(int i= 0; i < studyIds.length; i++) {
			deleteTranslocation(studyIds[i]);
		}
	}
}