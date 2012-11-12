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

import de.unihamburg.zbh.fishoracle_db_api.data.GenericFeature;
import de.unihamburg.zbh.fishoracle_db_api.data.Location;

public class GenericAdaptorImpl extends BaseAdaptor implements GenericAdaptor {
	
	protected GenericAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}

	@Override
	protected String[] tables() {
		return new String[]{"feature"};
	}

	@Override
	protected String[] columns() {
		return new String[]{"feature.feature_id",
				"location.location_id",
				"location.location_chromosome",
				"location.location_start",
				"location.location_end",
				"feature.feature_type",
				"feature.platform_id",
				"platform.platform_name",
				"feature.study_id"};
	}

	@Override
	protected String[][] leftJoins() {
		return new String[][]{{"location","feature.location_id = location.location_id"},
				{"study","study.study_id = feature.study_id"},
				{"study_in_project","study.study_id = study_in_project.study_id"},
				{"platform","platform.platform_id = feature.platform_id"},
				{"tissue_sample","tissue_sample.study_id = study.study_id"},
				{"organ","organ_id = tissue_sample_organ_id"},};
	}

	@Override
	public Object createObject(ResultSet rs) {
		
		Location loc = null;
		GenericFeature feature = null;
		int id = 0;
		int loc_id = 0;
		String chromosome = null;
		int start = 0;
		int end = 0;
		
		String featureType = null;
		int platformId = 0;
		String platformName = "";
		int studyId = 0;
		
		try {
			if(rs.next()){
				id = rs.getInt(1);
				loc_id = rs.getInt(2);
				chromosome = rs.getString(3);
				start = rs.getInt(4);
				end = rs.getInt(5);
				featureType = rs.getString(6);
				platformId = rs.getInt(7);
				platformName = rs.getString(8);
				studyId = rs.getInt(9);
				
				loc = new Location(loc_id, chromosome, start, end);
				
				feature = new GenericFeature(id, loc, featureType);
				feature.setPlatformId(platformId);
				feature.setPlatformName(platformName);
				feature.setStudyId(studyId);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return feature;
	}
	
	@Override
	public int storeGenericFeature(GenericFeature feature, int studyId) {
		Connection conn = null;
		StringBuffer loc_query = new StringBuffer();
		StringBuffer feature_query = new StringBuffer();
		int newLocId = 0;
		int newFeatureId = 0;
		
		try{
			
			conn = getConnection();
			
			loc_query.append("INSERT INTO location ")
			.append("(location_chromosome, " +
					"location_start, " + 
					"location_end) ")
			.append(" VALUES ")
			.append("('" + feature.getLocation().getChromosome() + 
					"', '" + feature.getLocation().getStart() + 
					"', '" + feature.getLocation().getEnd() + "')");
			
			ResultSet rs = executeUpdateGetKeys(conn, loc_query.toString());

			if(rs.next()){
				newLocId = rs.getInt(1);
			}
			
			rs.close();
			
			feature_query.append("INSERT INTO ").append(getPrimaryTableName())
			.append("(location_id" +
					", feature_type" +
					", platform_id" +
					", study_id" +
					")")
			.append(" VALUES ")
			.append("('" + newLocId +
					"', '" + feature.getType() +
					"', '" + feature.getPlatformId() +
					"', '" + studyId + "')");
			
			rs = executeUpdateGetKeys(conn, feature_query.toString());
			
			if(rs.next()){
				newFeatureId = rs.getInt(1);
			}
			
			rs.close();
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return newFeatureId;
	}
	
	@Override
	public void storeGenericFeatures(GenericFeature[] features, int studyId) {
		for(int i = 0; i < features.length; i++){
			storeGenericFeature(features[i], studyId);
		}	
	}
	
	@Override
	public GenericFeature fetchGenericFeatureById(int featureId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		GenericFeature feature = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ")
			.append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" LEFT JOIN location ON feature.location_id = location.location_id")
			.append(" LEFT JOIN platform ON feature.platform_id = platform.platform_id")
			.append(" WHERE ").append("feature_id = " + featureId);
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				feature = (GenericFeature) o;
			}
			
			rs.close();
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return feature;
	}
	
	@Override
	public String[] fetchAllTypes() {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		String[] types = null;
		ArrayList<String> typeList = new ArrayList<String>();
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append("DISTINCT (feature_type)")
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" ORDER BY feature_type ASC");
			
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
	public GenericFeature[] fetchGenericFeaturesForStudyId(int studyId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		GenericFeature feature = null;
		ArrayList<GenericFeature> featureContainer = new ArrayList<GenericFeature>();
		GenericFeature[] features = null;
		
		try{
			
			conn = getConnection();	
			
			query.append("SELECT ").append(super.columnsToString(columns()))
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" LEFT JOIN location ON feature.location_id = location.location_id")
			.append(" LEFT JOIN platform ON feature.platform_id = platform.platform_id")
			.append(" WHERE ").append("study_id = '" + studyId + "'")
			.append(" ORDER BY feature_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				feature = (GenericFeature) o;
				featureContainer.add(feature);
			}
			
			rs.close();
			
			features = new GenericFeature[featureContainer.size()];
			
			featureContainer.toArray(features);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return features;
	}
	
	@Override
	public GenericFeature[] fetchGenericFeatures(String chr,
													int start,
													int end,
													String[] featureTypeFilter,
													int[] projectFilter,
													int[] organFilter,
													int[] experimentFilter) {
		
		Connection conn = null;
		GenericFeature  feature = null;
		ArrayList<GenericFeature> featureContainer = new ArrayList<GenericFeature>();
		GenericFeature[] features = null;
		
		StringBuffer query = new StringBuffer();
		
		query.append(getBaseQuery())
		.append(" WHERE ")
		.append("location_chromosome = '" + chr + "'");
		query.append(getMaxiamlOverlappingSQLWhereClause(start, end));
		query.append(getArrayFilterSQLWhereClause("feature.feature_type", featureTypeFilter));
		query.append(getArrayFilterSQLWhereClause("project_id", projectFilter));
		query.append(getArrayFilterSQLWhereClause("organ_id", organFilter));
		query.append(getArrayFilterSQLWhereClause("experiment_id", experimentFilter));
		
		try{
			conn = getConnection();
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				feature = (GenericFeature) o;
				featureContainer.add(feature);
			}
			
			rs.close();
			
			features = new GenericFeature[featureContainer.size()];
			
			featureContainer.toArray(features);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return features;
	}
	
	@Override
	public void deleteGenericFeature(GenericFeature feature) {
		Connection conn = null;
		StringBuffer loc_query = new StringBuffer();
		StringBuffer feature_query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			loc_query.append("DELETE FROM location")
			.append(" WHERE ").append("location_id = " + feature.getLocation().getId());
			
			executeUpdate(conn, loc_query.toString());
			
			feature_query.append("DELETE FROM ")
			.append(super.getPrimaryTableName())
			.append(" WHERE ").append("feature_id = " + feature.getId());
			
			executeUpdate(conn, feature_query.toString());
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
	}
	
	@Override
	public void deleteGenericFeature(int studyId) {
		Connection conn = null;
		StringBuffer loc_query = new StringBuffer();
		StringBuffer feature_query = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			loc_query.append("DELETE location.* FROM ")
			.append(" location ")
			.append(" LEFT JOIN feature ON feature.location_id = location.location_id")
			.append(" WHERE ").append("study_id = " + studyId);
			
			executeUpdate(conn, loc_query.toString());
			
			feature_query.append("DELETE FROM ")
			.append(super.getPrimaryTableName())
			.append(" WHERE ").append("study_id = " + studyId);
			
			executeUpdate(conn, feature_query.toString());
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
	}
	
	@Override
	public void deleteGenericFeature(int[] studyIds) {
		for(int i= 0; i < studyIds.length; i++) {
			deleteGenericFeature(studyIds[i]);
		}
	}
}
