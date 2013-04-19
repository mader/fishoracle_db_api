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
import java.util.Date;

import com.mysql.jdbc.Connection;

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
							"segment",
							"mutation",
							"translocation",
							"feature",
							"tissue_sample",
							"organ"};
	}

	@Override
	protected String[] columns() {
		return new String[]{"study_id",
							"study_date_inserted",
							"study_name",
							"study_assembly",
							"study_description",
							"study_user_id"};
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
		int newStudyId = 0;
		
		try{
			
			conn = getConnection();
			
			studyQuery.append("INSERT INTO ").append(getPrimaryTableName())
			.append(" (" +
					"study_date_inserted, " +
					"study_name, " +
					"study_assembly, " +
					"study_description, " +
					"study_user_id " +
					")")
			.append(" VALUES ")
			.append("( CURDATE() " +
					", '" + study.getName() + 
					"', '" + study.getAssembly() +
					"', '" + study.getDescription() +
					"', '" + study.getUserId() + 
					"')");
			
			ResultSet mstudyRs = executeUpdateGetKeys(conn, studyQuery.toString());
			
			if(mstudyRs.next()){
				newStudyId = mstudyRs.getInt(1);
			}
			
			mstudyRs.getStatement().close();
			mstudyRs.close();
			
			TissueSampleAdaptor tsa = (TissueSampleAdaptor) driver.getAdaptor("TissueSampleAdaptor");
			tsa.storeTissueSample(study.getOrganId(), study.getPropertyIds(), newStudyId);
			
		 mstudyRs.close();
		 
		 if(study.getSegments() != null){
			 SegmentAdaptor sa = (SegmentAdaptor) driver.getAdaptor("SegmentAdaptor");
			 sa.storeSegments(study.getSegments(), newStudyId);
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
		 pra.addStudyToProject(newStudyId, projectId);
		 
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
	
	public boolean hasType(String type, int studyId) {
		
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		int count = 0;
		boolean hasType = false;
		
		try{
			
			conn = getConnection();	
			
			
			query.append("SELECT ").append("count(*)")
					.append(" FROM ").append(type)
					.append(" WHERE ")
					.append("study_id = " + studyId );
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			if(rs.next()){
				count = rs.getInt(1);
			}
			
			rs.close();
			
			if(count > 0){
				hasType = true;
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}

		return hasType;
	}
	
	public Object createObject(ResultSet rs, boolean withChildren) {
		Study study = null;
		TissueSample tissue;
		
		int studyId = 0;
		Date date;
		String studyName = null;
		String studyAssembly = null;
		String studyDescription = null;
		int tissueSampleId = 0;
		
		try {
			if(rs.next()){
				studyId = rs.getInt(1);
				date = rs.getDate(2);
				studyName = rs.getString(3);
				studyAssembly = rs.getString(4);
				studyDescription = rs.getString(5);
				tissueSampleId = rs.getInt(6);
				
				study = new Study(studyId, date, studyName, studyAssembly, studyDescription, 0);
				
				TissueSampleAdaptor ta = (TissueSampleAdaptor) driver.getAdaptor("TissueSampleAdaptor");
				tissue = ta.fetchTissueSampleById(tissueSampleId);
				
				if(withChildren){
					study.setTissue(tissue);
				} else {
					study.setOrganId(tissue.getOrgan().getId());
					study.setPropertyIds(tissue.getPropertyIds());
				}
				
				study.setHasSegment(hasType("segment", study.getId()));
				study.setHasMutation(hasType("mutation", study.getId()));
				study.setHasTranslocation(hasType("translocation", study.getId()));
				study.setHasGeneric(hasType("feature", study.getId()));
				
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
					"study.study_assembly, " +
					"study.study_description, " +
					"tissue_sample.tissue_sample_id")
					.append(" FROM ").append(super.getPrimaryTableName())
					.append(" LEFT JOIN ")
					.append("tissue_sample ON tissue_sample.study_id = study.study_id")
					.append(" ORDER BY study_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs, withChildren)) != null) {
				study = (Study) o;
				studyContainer.add(study);
			}
			
			rs.close();
			
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
											"study.study_assembly, " +
											"study.study_description, " +
											"tissue_sample.tissue_sample_id")
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" LEFT JOIN ")
			.append("tissue_sample ON tissue_sample.study_id = study.study_id")
			.append(" WHERE ").append("study.study_id = " + id)
			.append(" ORDER BY study_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs, withChilden)) != null) {
				study = (Study) o;
				
			}
			
			rs.close();
			
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
											"study.study_assembly, " +
											"study.study_description, " +
											"tissue_sample.tissue_sample_id")
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" LEFT JOIN ")
			.append("tissue_sample ON tissue_sample.study_id = study.study_id")
			.append(" WHERE ").append("study.study_name = '" + studyName + "'")
			.append(" ORDER BY study_id ASC");
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs, withChilden)) != null) {
				study = (Study) o;
				
			}
			
			rs.close();
			
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
						"study.study_assembly, " +
						"study.study_description, " +
						"tissue_sample.tissue_sample_id")
						.append(" FROM ").append(super.getPrimaryTableName())
						.append(" LEFT JOIN ")
						.append("tissue_sample ON tissue_sample.study_id = study.study_id")
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
				query.append(" ORDER BY study.study_name");
			
				ResultSet rs = executeQuery(conn, query.toString());
			
				Object o;
			
				while ((o = createObject(rs, withChrildren)) != null) {
					study = (Study) o;
					studyContainer.add(study);
				}
				
				rs.close();
				
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
	public Study[] fetchStudiesNotInProject(int projectId,
												int notInProjectId,
												boolean withChrildren) {
		
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Study studyNotInProject = null;
		ArrayList<Study> studyContainer = new ArrayList<Study>();
		Study[] studies = null;
		
		try{
			
			conn = getConnection();
			
			/* First get all studies that _are_ in the project.*/
			Study[] studiesInProject = fetchStudiesForProject(notInProjectId);

			/* Then get all studies but the users we just received. */
			query.append("SELECT ").append("study.study_id," +
					"study_date_inserted," + 
					"study_name," +
					"study_assembly," +
					"study_description," + 
					"study_user_id")
			.append(" FROM ").append(super.getPrimaryTableName())
			.append(" LEFT JOIN study_in_project ON ")
			.append("study_in_project.study_id = study.study_id");
			
			String whereClause = " ";
			
			boolean where = true;
			if(studiesInProject.length > 0){
				for(int i=0; i < studiesInProject.length; i++){
					
					if(where){
						whereClause += " WHERE study.study_id != " + studiesInProject[i].getId();	
						where = false;
					} else {
						whereClause += " AND study.study_id != " + studiesInProject[i].getId();
					}
				}
			}
			
			if(where){
				whereClause += " WHERE project_id = " + projectId;
			} else {
				whereClause += " AND project_id = " + projectId;
			}
			query.append(whereClause).append(" ORDER BY study.study_id ASC");
			
			studyContainer = new ArrayList<Study>();
			
			ResultSet rs = executeQuery(conn, query.toString());
			
			Object o;
			
			while ((o = createObject(rs)) != null) {
				studyNotInProject = (Study) o;
				studyContainer.add(studyNotInProject);
			}
			
			rs.close();
			
			studies = new Study[studyContainer.size()];
			studyContainer.toArray(studies);
			
		} catch (Exception e){
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		return studies;
	}
	
	@Override
	public int countStudyInProjects(int studyId) {
		
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		int count = 0;
		
		try{
			
			conn = getConnection();	
		
			query.append("SELECT COUNT(study_in_project_id)")
			.append(" FROM ")
			.append("study_in_project")
			.append(" WHERE ")
			.append("study_id = ")
			.append(studyId);
		
			ResultSet rs = executeQuery(conn, query.toString());
			
			if(rs.next()){
				count = rs.getInt(1);
			}
			
			rs.close();
		
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				close(conn);
			}
		}
		
		return count;
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
			
			SegmentAdaptor csa = (SegmentAdaptor) driver.getAdaptor("SegmentAdaptor");
			csa.deleteSegment(study.getId());
			
			SNPMutationAdaptor snpm = (SNPMutationAdaptor) driver.getAdaptor("SNPMutationAdaptor");
			snpm.deleteSNPMutation(study.getId());
			
			TranslocationAdaptor tra = (TranslocationAdaptor) driver.getAdaptor("TranslocationAdaptor");
			tra.deleteTranslocation(study.getId());
			
			GenericAdaptor ga = (GenericAdaptor) driver.getAdaptor("GenericAdaptor");
			ga.deleteGenericFeature(study.getId());
			
			TissueSampleAdaptor ta = (TissueSampleAdaptor) driver.getAdaptor("TissueSampleAdaptor");
			ta.deleteTissueSample(study.getTissue());
			
			studyInProjectQuery.append("DELETE FROM ")
			.append("study_in_project")
			.append(" WHERE ").append("study_id = " + study.getId());
			
			executeUpdate(conn, studyInProjectQuery.toString());
			
			socQuery.append("DELETE FROM ").append("tissue_sample")
			.append(" WHERE ").append("study_id = " + study.getId());

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