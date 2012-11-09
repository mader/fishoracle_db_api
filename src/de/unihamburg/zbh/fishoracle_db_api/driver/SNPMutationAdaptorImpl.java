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

import de.unihamburg.zbh.fishoracle_db_api.data.Location;
import de.unihamburg.zbh.fishoracle_db_api.data.SNPMutation;

public class SNPMutationAdaptorImpl extends BaseAdaptor implements SNPMutationAdaptor {

	protected SNPMutationAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}

	@Override
	protected String[] tables() {
		return new String[]{"mutation"};
	}

	@Override
	protected String[] columns() {
		return new String[]{"mutation.mutation_id",
				"location.location_id",
				"location.location_chromosome",
				"location.location_start",
				"location.location_end",
				"mutation.db_snp_id",
				"mutation.mut_ref",
				"mutation.mut_alt",
				"mutation.quality",
				"mutation.somatic",
				"mutation.confidence",
				"mutation.snp_tool",
				"mutation.platform_id",
				"platform.platform_name",
				"mutation.study_id"};
	}

	@Override
	protected String[][] leftJoins() {
		return new String[][]{{"location","mutation.location_id = location.location_id"},
								{"study","study.study_id = mutation.study_id"},
								{"study_in_project","study.study_id = study_in_project.study_id"},
								{"platform","platform.platform_id = mutation.platform_id"},
								{"tissue_sample","tissue_sample_id = sample_on_platform_tissue_sample_id"},
								{"organ","organ_id = tissue_sample_organ_id"},};
	}
	
	@Override
	public Object createObject(ResultSet rs) {
		
		Location loc = null;
		SNPMutation snpMut = null;
		int id = 0;
		int loc_id = 0;
		String chromosome = null;
		int start = 0;
		int end = 0;
		
		String db_snp_id = null;
		String alt = null;
		String ref = null;
		double quality = 0.0;
		String somatic = null;
		String confidence = null;
		String snpTool = null;
		int studyId = 0;
		
		int platformId = 0;
		String platformName = "";
		
		try {
			if(rs.next()){
				id = rs.getInt(1);
				loc_id = rs.getInt(2);
				chromosome = rs.getString(3);
				start = rs.getInt(4);
				end = rs.getInt(5);
				
				db_snp_id = rs.getString(6);
				ref = rs.getString(7);
				alt = rs.getString(8);
				quality = rs.getDouble(9);
				somatic = rs.getString(10);
				confidence = rs.getString(11);
				snpTool = rs.getString(12);
				platformId = rs.getInt(13);
				platformName = rs.getString(14);
				studyId = rs.getInt(15);
				
				loc = new Location(loc_id, chromosome, start, end);
				
				snpMut = new SNPMutation(id, loc, db_snp_id, ref, alt, quality, somatic, confidence, snpTool);
				
				snpMut.setPlatformId(platformId);
				snpMut.setPlatformName(platformName);
				snpMut.setStudyId(studyId);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return snpMut;
	}
	
	@Override
	public int storeSNPMutation(SNPMutation snpMut, int studyId) {
		Connection conn = null;
		StringBuffer loc_query = new StringBuffer();
		StringBuffer mutation_query = new StringBuffer();
		int newLocId = 0;
		int newMutationId = 0;
		
		try{
			
			conn = getConnection();
			
			loc_query.append("INSERT INTO location ")
			.append("(location_chromosome, " +
					"location_start, " + 
					"location_end) ")
			.append(" VALUES ")
			.append("('" + snpMut.getLocation().getChromosome() + 
					"', '" + snpMut.getLocation().getStart() + 
					"', '" + snpMut.getLocation().getEnd() + "')");
			
			ResultSet rs = executeUpdateGetKeys(conn, loc_query.toString());

			if(rs.next()){
				newLocId = rs.getInt(1);
			}
			
			rs.close();
			
			mutation_query.append("INSERT INTO ").append(getPrimaryTableName())
			.append("(location_id" +
					", db_snp_id" +
					", mut_ref" +
					", mut_alt" +
					", quality" +
					", somatic" +
					", confidence" +
					", snp_tool" +
					", platform_id" +
					", study_id" +
					")")
			.append(" VALUES ")
			.append("('" + newLocId +
					"', '" + snpMut.getDbSnpId() +
					"', '" + snpMut.getRef() + 
					"', '" + snpMut.getAlt() + 
					"', '" + snpMut.getQuality() + 
					"', '" + snpMut.getSomatic() + 
					"', '" + snpMut.getConfidence() + 
					"', '" + snpMut.getSnpTool() + 
					"', '" + snpMut.getPlatformId() + 
					"', '" + studyId + "')");
			
			rs = executeUpdateGetKeys(conn, mutation_query.toString());
			
			if(rs.next()){
				newMutationId = rs.getInt(1);
			}
			
			rs.close();
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return newMutationId;
	}

	@Override
	public void storeSNPMutations(SNPMutation[] snpMuts, int studyId) {
		for(int i = 0; i < snpMuts.length; i++){
			storeSNPMutation(snpMuts[i], studyId);
		}	
	}

	@Override
	public SNPMutation fetchSNPMutationById(int mutationId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		SNPMutation snpMut = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ")
			.append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" LEFT JOIN location ON mutation.location_id = location.location_id")
			.append(" LEFT JOIN platform ON mutation.platform_id = platform.platform_id")
			.append(" WHERE ").append("mutation_id = " + mutationId);
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				snpMut = (SNPMutation) o;
			}
			
			rs.close();
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return snpMut;
	}

	@Override
	public SNPMutation[] fetchSNPMutationsForStudyId(int studyId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		SNPMutation snpMut = null;
		ArrayList<SNPMutation> mutationContainer = new ArrayList<SNPMutation>();
		SNPMutation[] snpMuts = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" LEFT JOIN location ON mutation.location_id = location.location_id")
			.append(" LEFT JOIN platform ON mutation.platform_id = platform.platform_id")
			.append(" WHERE ").append("study_id = '" + studyId + "'")
			.append(" ORDER BY mutation_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				snpMut = (SNPMutation) o;
				mutationContainer.add(snpMut);
			}
			
			rs.close();
			
			snpMuts = new SNPMutation[mutationContainer.size()];
			
			mutationContainer.toArray(snpMuts);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return snpMuts;
	}
	
	@Override
	public SNPMutation[] fetchSNPMutations(String chr,
											int start,
											int end,
											double qualityFilter,
											String[] somaticFilter,
											String[] confidenceFilter,
											String[] snpToolFilter,
											int[] projectFilter,
											int[] organFilter,
											int[] experimentFilter) {
		
		Connection conn = null;
		SNPMutation  snpMut = null;
		ArrayList<SNPMutation> mutationContainer = new ArrayList<SNPMutation>();
		SNPMutation[] snpMuts = null;
		
		StringBuffer query = new StringBuffer();
		
		query.append(getBaseQuery())
		.append(" WHERE ")
		.append("location_chromosome = '" + chr + "'");
		query.append(getMaxiamlOverlappingSQLWhereClause(start, end));
		query.append(getScoreSQLWhereClause("mutation.quality", qualityFilter, true));
		query.append(getArrayFilterSQLWhereClause("mutation.somatic", somaticFilter));
		query.append(getArrayFilterSQLWhereClause("mutation.confidence", confidenceFilter));
		query.append(getArrayFilterSQLWhereClause("mutation.snp_tool", snpToolFilter));
		query.append(getArrayFilterSQLWhereClause("project_id", projectFilter));
		query.append(getArrayFilterSQLWhereClause("organ_id", organFilter));
		query.append(getArrayFilterSQLWhereClause("experiment_id", experimentFilter));
		query.append(" ORDER BY mutation_id ASC");
		
		try{
			conn = getConnection();
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				snpMut = (SNPMutation) o;
				mutationContainer.add(snpMut);
			}
			
			rs.close();
			
			snpMuts = new SNPMutation[mutationContainer.size()];
			
			mutationContainer.toArray(snpMuts);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return snpMuts;
	}

	@Override
	public void deleteSNPMutation(SNPMutation snpMut) {
		Connection conn = null;
		StringBuffer loc_query = new StringBuffer();
		StringBuffer segment_query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			loc_query.append("DELETE FROM location")
			.append(" WHERE ").append("location_id = " + snpMut.getLocation().getId());
			
			executeUpdate(conn, loc_query.toString());
			
			segment_query.append("DELETE FROM ")
			.append(super.getPrimaryTableName())
			.append(" WHERE ").append("mutation_id = " + snpMut.getId());
			
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
	public void deleteSNPMutation(int studyId) {
		Connection conn = null;
		StringBuffer loc_query = new StringBuffer();
		StringBuffer segment_query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			loc_query.append("DELETE location.* FROM ")
			.append(" location ")
			.append(" LEFT JOIN mutation ON mutation.location_id = location.location_id")
			.append(" WHERE ").append("study_id = " + studyId);
			
			executeUpdate(conn, loc_query.toString());
			
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
	public void deleteSNPMutation(int[] studyIds) {
		for(int i= 0; i < studyIds.length; i++) {
			deleteSNPMutation(studyIds[i]);
		}
	}
}