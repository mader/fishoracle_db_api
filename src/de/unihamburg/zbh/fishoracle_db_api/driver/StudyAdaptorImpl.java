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
import java.util.Date;

import de.unihamburg.zbh.fishoracle_db_api.data.Platform;
import de.unihamburg.zbh.fishoracle_db_api.data.Study;
import de.unihamburg.zbh.fishoracle_db_api.data.TissueSample;

/**
 * @author Malte Mader
 *
 */
public class StudyAdaptorImpl extends BaseAdaptor implements StudyAdaptor{

	protected StudyAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}

	@Override
	protected String[] tables() {
		return new String[]{"study",
							"cn_segment",
							"sample_on_platform",
							"platform",
							"tissue_sample",
							"organ"};
	}

	@Override
	protected String[] columns() {
		return new String[]{"study_id",
							"study_date_inserted",
							"study_name",
							"study_type",
							"study_assembly",
							"study_description",
							"study_user_id",
							"study_sample_on_platform_id"};
	}
	
	@Override
	protected String[][] leftJoins() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int storeStudy(Study study, int projectId) {
		Connection conn = null;
		StringBuffer studyQuery = new StringBuffer();
		StringBuffer socQuery = new StringBuffer();
		StringBuffer socUpdateQuery = new StringBuffer();
		int newStudyId = 0;
		
		try{
			
			conn = getConnection();
			
			//TODO Test if study name is unique. If not throw exception.
			
			TissueSampleAdaptor tsa = (TissueSampleAdaptor) driver.getAdaptor("TissueSampleAdaptor");
			int newTissueSampleId = tsa.storeTissueSample(study.getOrganId(), study.getPropertyIds());
			
			socQuery.append("INSERT INTO ").append("sample_on_platform")
			.append(" ( sample_on_platform_platform_id, " +
					"sample_on_platform_tissue_sample_id)")
			.append(" VALUES ")
			.append("(" + study.getPlatformId() + ", ")
			.append(newTissueSampleId + ")");
			
			ResultSet socRs = executeUpdateGetKeys(conn, socQuery.toString());
			
			int newSampleOnPlatformId = 0;
			if(socRs.next()){
				newSampleOnPlatformId = socRs.getInt(1);
			}
			
			studyQuery.append("INSERT INTO ").append(getPrimaryTableName())
			.append(" (" +
					"study_date_inserted, " +
					"study_name, " +
					"study_type, " +
					"study_assembly, " +
					"study_description, " +
					"study_user_id, " +
					"study_sample_on_platform_id" +
					")")
			.append(" VALUES ")
			.append("( CURDATE() " +
					", '" + study.getName() + 
					"', '" + study.getType() +
					"', '" + study.getAssembly() +
					"', '" + study.getDescription() +
					"', '" + study.getUserId() + 
					"', '" +  newSampleOnPlatformId +
					"')");
			
			ResultSet mstudyRs = executeUpdateGetKeys(conn, studyQuery.toString());
			
			if(mstudyRs.next()){
				newStudyId = mstudyRs.getInt(1);
			}
			
		socUpdateQuery.append("UPDATE ").append("sample_on_platform")
		.append(" SET sample_on_platform_study_id = " + newStudyId)
		.append(" WHERE ").append("sample_on_platform_id = " + newSampleOnPlatformId);
		
		 executeUpdate(conn, socUpdateQuery.toString());
		 
		 if(study.getSegments() != null){
			 CnSegmentAdaptor sa = (CnSegmentAdaptor) driver.getAdaptor("CnSegmentAdaptor");
			 sa.storeCnSegments(study.getSegments(), newStudyId);
		 }
		 if(study.getMutations() != null){
			 SNPMutationAdaptor snpm = (SNPMutationAdaptor) driver.getAdaptor("SNPMutationAdaptor");
			 snpm.storeSNPMutations(study.getMutations(), newStudyId);
		 }
		 if(study.getTranslocs() != null){
			 TranslocationAdaptor ta = (TranslocationAdaptor) driver.getAdaptor("TranslocationAdaptor");
			 ta.storeTranslocations(study.getTranslocs(), newStudyId);
		 }
		 if(study.getFeatures() != null){
			 GenericAdaptor gfa = (GenericAdaptor) driver.getAdaptor("GenericAdaptor");
			 gfa.storeGenericFeatures(study.getFeatures(), newStudyId);
		 }
		 
		 ProjectAdaptor pra = (ProjectAdaptor) driver.getAdaptor("ProjectAdaptor");
		 pra.addMicroarraystudyToProject(newStudyId, projectId);
		 
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return newStudyId;
	}

	@Override
	public Object createObject(ResultSet rs) {
		return createObject(rs, true);
	}
	
	public Object createObject(ResultSet rs, boolean withChildren) {
		Study study = null;
		TissueSample tissue;
		Platform platform;
		
		int studyId = 0;
		Date date;
		String studyName = null;
		String studyType = null;
		String studyAssembly = null;
		String studyDescription = null;
		int platformId = 0;
		int tissueSampleId = 0;
		
		try {
			if(rs.next()){
				studyId = rs.getInt(1);
				date = rs.getDate(2);
				studyName = rs.getString(3);
				studyType = rs.getString(4);
				studyAssembly = rs.getString(5);
				studyDescription = rs.getString(6);
				platformId = rs.getInt(7);
				tissueSampleId = rs.getInt(8);
				
				study = new Study(studyId, date, studyName,studyType, studyAssembly, studyDescription, 0);
				
				TissueSampleAdaptor ta = (TissueSampleAdaptor) driver.getAdaptor("TissueSampleAdaptor");
				tissue = ta.fetchTissueSampleById(tissueSampleId);
				
				if(withChildren){
					study.setTissue(tissue);
				} else {
					study.setOrganId(tissue.getOrgan().getId());
					study.setPropertyIds(tissue.getPropertyIds());
				}
				
				PlatformAdaptor ca = (PlatformAdaptor) driver.getAdaptor("PlatformAdaptor");
				platform = ca.fetchPlatformById(platformId);
				
				if(withChildren){
					study.setPlatform(platform);
				} else {
					study.setPlatformId(platform.getId());
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return study;
	}
	
	@Override
	public Study[] fetchAllStudies() {
		return fetchAllStudies(true);
	}
	
	@Override
	public Study[] fetchAllStudies(boolean withChildren) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Study study = null;
		ArrayList<Study> studyContainer = new ArrayList<Study>();
		Study[] studies = null;
		
		try{
			
			conn = getConnection();	
			
			
			query.append("SELECT ").append("study.study_id, " +
					"study.study_date_inserted, " +
					"study.study_name, " +
					"study.study_type, " +
					"study.study_assembly, " +
					"study.study_description, " +
					"sample_on_platform.sample_on_platform_platform_id, " +
					"sample_on_platform.sample_on_platform_tissue_sample_id")
					.append(" FROM ").append(super.getPrimaryTableName())
					.append(" LEFT JOIN ")
					.append("sample_on_platform ON study.study_id = sample_on_platform.sample_on_platform_study_id")
					.append(" LEFT JOIN ")
					.append("tissue_sample ON sample_on_platform.sample_on_platform_tissue_sample_id = tissue_sample.tissue_sample_id")
					.append(" ORDER BY study_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs, withChildren)) != null) {
				study = (Study) o;
				studyContainer.add(study);
			}
			
			studies = new Study[studyContainer.size()];
			
			studyContainer.toArray(studies);
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return studies;
	}

	public Study fetchStudyById(int id){
		 return fetchStudyById(id, false);
	}

	@Override
	public Study fetchStudyById(int id, boolean withChilden) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Study study = null;
		
		try{
			
			conn = getConnection();
			
			query.append("SELECT ").append("study.study_id, " +
											"study.study_date_inserted, " +
											"study.study_name, " +
											"study.study_type, " +
											"study.study_assembly, " +
											"study.study_description, " +
											"sample_on_platform.sample_on_platform_platform_id, " +
											"sample_on_platform.sample_on_platform_tissue_sample_id")
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" LEFT JOIN ")
			.append("sample_on_platform ON study.study_id = sample_on_platform.sample_on_platform_study_id")
			.append(" LEFT JOIN ")
			.append("tissue_sample ON sample_on_platform.sample_on_platform_tissue_sample_id = tissue_sample.tissue_sample_id")
			.append(" WHERE ").append("study.study_id = " + id)
			.append(" ORDER BY study_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs, withChilden)) != null) {
				study = (Study) o;
				
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return study;
	}
	
	@Override
	public Study fetchStudyForName(String studyName, boolean withChilden){
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Study study = null;
		
		try{
			
			conn = getConnection();
			
			query.append("SELECT ").append("study.study_id, " +
											"study.study_date_inserted, " +
											"study.study_name, " +
											"study.study_type, " +
											"study.study_assembly, " +
											"study.study_description, " +
											"sample_on_platform.sample_on_platform_platform_id, " +
											"sample_on_platform.sample_on_platform_tissue_sample_id")
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" LEFT JOIN ")
			.append("sample_on_platform ON study.study_id = sample_on_platform.sample_on_platform_study_id")
			.append(" LEFT JOIN ")
			.append("tissue_sample ON sample_on_platform.sample_on_platform_tissue_sample_id = tissue_sample.tissue_sample_id")
			.append(" WHERE ").append("study.study_name = '" + studyName + "'")
			.append(" ORDER BY study_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs, withChilden)) != null) {
				study = (Study) o;
				
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return study;
		
		
	}
	
	@Override
	public Study[] fetchStudiesForProject(int projectId) {
		int[] p = {projectId};
		return fetchStudiesForProject(p, true);
	}
	
	@Override
	public Study[] fetchStudiesForProject(int projectId, boolean withChildren) {
		int[] p = {projectId};
		return fetchStudiesForProject(p, withChildren);
	}
	
	@Override
	public Study[] fetchStudiesForProject(int[] projectIds){
		return fetchStudiesForProject(projectIds, true);
	}
	
	@Override
	public Study[] fetchStudiesForProject(int[] projectIds, boolean withChrildren) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Study study = null;
		ArrayList<Study> studyContainer = new ArrayList<Study>();
		Study[] studies = null;
		
		if(projectIds.length > 0){
		
			try{
			
				conn = getConnection();	
			
				query.append("SELECT ").append("study.study_id, " +
						"study.study_date_inserted, " +
						"study.study_name, " +
						"study.study_type, " +
						"study.study_assembly, " +
						"study.study_description, " +
						"sample_on_platform.sample_on_platform_platform_id, " +
						"sample_on_platform.sample_on_platform_tissue_sample_id")
						.append(" FROM ").append(super.getPrimaryTableName())
						.append(" LEFT JOIN ")
						.append("sample_on_platform ON study.study_id = sample_on_platform.sample_on_platform_study_id")
						.append(" LEFT JOIN ")
						.append("study_in_project ON study.study_id = study_in_project.study_id")
						.append(" WHERE ");
			
				String whereClause = "";
			
				for(int i = 0; i < projectIds.length; i++){
					if(i + 1 != projectIds.length){
						whereClause += "study_in_project.project_id = " + projectIds[i] + " OR ";
					} else {
						whereClause += "study_in_project.project_id = " + projectIds[i];	
					}
				}
			
				query.append(whereClause);
				query.append(" ORDER BY study.study_id");
			
				ResultSet rs = executeQuery(conn, query.toString());
			
				Object o;
			
				while ((o = createObject(rs, withChrildren)) != null) {
					study = (Study) o;
					studyContainer.add(study);
				}
			
				studies = new Study[studyContainer.size()];
			
				studyContainer.toArray(studies);
			
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				if(conn != null){
					close(conn);
				}
			}
		} else {
			studies = new Study[0];
		}
		
		return studies;
	}
	
	@Override
	public void deleteStudy(int studyId) {
		
		Study m = fetchStudyById(studyId, true);
		deleteStudy(m);
	}
	
	@Override
	public void deleteStudy(Study study) {
		Connection conn = null;
		StringBuffer studyInProjectQuery = new StringBuffer();
		StringBuffer socQuery = new StringBuffer();
		StringBuffer studyQuery = new StringBuffer();
		
		try{
			
			conn = getConnection();
			
			CnSegmentAdaptor csa = (CnSegmentAdaptor) driver.getAdaptor("CnSegmentAdaptor");
			csa.deleteCnSegment(study.getId());
			
			SNPMutationAdaptor snpm = (SNPMutationAdaptor) driver.getAdaptor("SNPMutationAdaptor");
			snpm.deleteSNPMutation(study.getId());
			
			TissueSampleAdaptor ta = (TissueSampleAdaptor) driver.getAdaptor("TissueSampleAdaptor");
			ta.deleteTissueSample(study.getTissue());
			
			studyInProjectQuery.append("DELETE FROM ")
			.append("study_in_project")
			.append(" WHERE ").append("study_id = " + study.getId());
			
			executeUpdate(conn, studyInProjectQuery.toString());
			
			socQuery.append("DELETE FROM ").append("sample_on_platform")
			.append(" WHERE ").append("sample_on_platform_study_id = " + study.getId());

			executeUpdate(conn, socQuery.toString());
			
			studyQuery.append("DELETE FROM ")
			.append(getPrimaryTableName())
			.append(" WHERE ").append("study_id = " + study.getId());

			executeUpdate(conn, studyQuery.toString());
				
		} catch (Exception e){
				e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
	}
}