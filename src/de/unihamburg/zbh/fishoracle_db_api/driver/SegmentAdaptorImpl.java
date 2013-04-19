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

import de.unihamburg.zbh.fishoracle_db_api.data.Segment;
import de.unihamburg.zbh.fishoracle_db_api.data.Location;
import de.unihamburg.zbh.fishoracle_db_api.data.Study;

/**
 * @author Malte Mader
 * 
 */
public class SegmentAdaptorImpl extends BaseAdaptor
		implements
		SegmentAdaptor {

	protected SegmentAdaptorImpl(FODriverImpl driver) {
		super(driver, TYPE);
	}

	@Override
	protected String[] tables() {
		return new String[] { "segment" };
	}

	@Override
	protected String[] columns() {
		return new String[] { "segment.segment_id",
								"segment.chromosome",
								"segment.start",
								"segment.end",
								"mean",
								"markers",
								"status",
								"status_score",
								"type",
								"segment.platform_id",
								"platform_name",
								"segment.study_id" };
	}

	@Override
	protected String[][] leftJoins() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int storeSegment(Segment segment, int studyId) {
		Connection conn = null;
		StringBuffer segment_query = new StringBuffer();
		int newSegmentId = 0;

		try {

			conn = getConnection();

			segment_query
					.append("INSERT INTO ")
					.append(getPrimaryTableName())
					.append("(chromosome, " +
							"start, " +
							"end, " +
							"mean, " +
							"markers, " +
							"status, " +
							"status_score, " +
							"type, " +
							"platform_id, " +
							"study_id)")
					.append(" VALUES ")
					.append("('" + segment.getLocation().getChromosome() +
							"', '" + segment.getLocation().getStart() +
							"', '" + segment.getLocation().getEnd() +
							"', '" + segment.getMean() +
							"', '" + segment.getNumberOfMarkers() +
							"', '" + segment.getStatus() +
							"', '" + segment.getStatusScore() +
							"', '" + segment.getType() +
							"', '" + segment.getPlatformId() +
							"', '" + studyId + "')");

			ResultSet rs = executeUpdateGetKeys(conn, segment_query.toString());
			
			if (rs.next()) {
				newSegmentId = rs.getInt(1);
			}
			rs.getStatement().close();
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				close(conn);
			}
		}
		return newSegmentId;
	}

	@Override
	public void storeSegments(Segment[] segments, int studyId) {
		for (int i = 0; i < segments.length; i++) {
			storeSegment(segments[i], studyId);
		}
	}

	@Override
	public Object createObject(ResultSet rs) {
		Location loc = null;
		Segment segment = null;
		int id = 0;
		String chromosome = null;
		int start = 0;
		int end = 0;
		double mean = 0;
		int numberOfMarkers = 0;
		String type = "";
		int status = 0;
		double statusScore = 0.0;
		int studyId = 0;

		int platformId = 0;
		String platformName = "";
		
		try {
			if (rs.next()) {
				id = rs.getInt(1);
				chromosome = rs.getString(2);
				start = rs.getInt(3);
				end = rs.getInt(4);
				mean = rs.getDouble(5);
				numberOfMarkers = rs.getInt(6);
				status = rs.getInt(7);
				statusScore = rs.getDouble(8);
				type = rs.getString(9);
				platformId = rs.getInt(10);
				platformName = rs.getString(11);
				studyId = rs.getInt(12);

				loc = new Location(chromosome, start, end);

				segment = new Segment(id,
										loc,
										type);
				
				segment.setMean(mean);
				segment.setNumberOfMarkers(numberOfMarkers);
				segment.setStatus(status);
				segment.setStatusScore(statusScore);
				
				segment.setPlatformId(platformId);
				segment.setPlatformName(platformName);
				segment.setStudyId(studyId);
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return segment;
	}

	@Override
	public Segment fetchSegmentById(int segmentId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Segment segment = null;
		StudyAdaptor sa = driver.getStudyAdaptor();
		Study s;

		try {

			conn = getConnection();

			query
					.append("SELECT ")
					.append(super.columnsToString(columns()))
					.append(" FROM ")
					.append(super.getPrimaryTableName())
					.append(" LEFT JOIN platform ON segment.platform_id = platform.platform_id")
					.append(" WHERE ")
					.append("segment_id = " + segmentId);

			ResultSet segmentRs = executeQuery(conn, query.toString());

			Object o;

			while ((o = createObject(segmentRs)) != null) {
				segment = (Segment) o;
				s = sa.fetchStudyById(segment.getStudyId());
				if (s != null) {
					segment.setStudyName(s.getName());
				}
			}

			segmentRs.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				close(conn);
			}
		}
		return segment;
	}

	@Override
	public Location fetchLocationForSegmentId(int segmentId) {
		Connection conn = null;
		Location loc = null;
		StringBuffer query = new StringBuffer();
		try {

			conn = getConnection();

			query
					.append("SELECT ")
					.append("chromosome, "
							+ "start, "
							+ "end")
					.append(" FROM ")
					.append(getPrimaryTableName())
					.append(" WHERE ")
					.append("segment_id = " + segmentId);

			ResultSet rs = executeQuery(conn, query.toString());
			
			int start = 0;
			int end = 0;
			String chr = null;
			while (rs.next()) {
				chr = rs.getString(1);
				start = rs.getInt(2);
				end = rs.getInt(3);

				loc = new Location(chr, start, end);
			}

			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				close(conn);
			}
		}
		return loc;
	}

	private String getThresholdSQLClause(Double segMean) {
		String qrystr = "";
		if (segMean > 0) {
			qrystr = qrystr + " AND mean > '" + segMean + "'";
		}
		if (segMean < 0) {
			qrystr = qrystr + " AND mean < '" + segMean + "'";
		}
		return qrystr;
	}

	private String getProjectSQLClause(String[] projectFilter) {

		String projectFilterStr = "";
		if (projectFilter != null && projectFilter.length > 0) {
			for (int i = 0; i < projectFilter.length; i++) {
				if (i == 0) {
					projectFilterStr =
							" project_id = '" + (projectFilter[i]) + "'";
				} else {
					projectFilterStr =
							projectFilterStr + " OR project_id = '"
									+ (projectFilter[i]) + "'";
				}
			}
			projectFilterStr = " AND (" + projectFilterStr + ")";
		}
		return projectFilterStr;
	}

	private String getOrganSQLClause(String[] organFilter) {
		String organFilterStr = "";
		if (organFilter != null && organFilter.length > 0) {
			for (int i = 0; i < organFilter.length; i++) {
				if (i == 0) {
					organFilterStr = " organ_id = '" + (organFilter[i]) + "'";
				} else {
					organFilterStr =
							organFilterStr + " OR organ_id = '"
									+ (organFilter[i]) + "'";
				}
			}
			organFilterStr = " AND (" + organFilterStr + ")";
		}
		return organFilterStr;
	}

	private String getExperimentSQLClause(String[] experimentFilter) {

		String experimentFilterStr = "";
		if (experimentFilter != null && experimentFilter.length > 0) {
			for (int i = 0; i < experimentFilter.length; i++) {
				if (i == 0) {
					experimentFilterStr =
							" study.study_id = '" + (experimentFilter[i]) + "'";
				} else {
					experimentFilterStr =
							experimentFilterStr + " OR study.study_id = '"
									+ (experimentFilter[i]) + "'";
				}
			}
			experimentFilterStr = " AND (" + experimentFilterStr + ")";
		}
		return experimentFilterStr;
	}

	@Override
	public Location fetchMaximalOverlappingSegmentRange(
			String chr,
			int start,
			int end,
			Double segMean,
			String[] projectFilter,
			String[] organFilter,
			String[] experimentFilter) {

		Location loc = null;
		Connection conn = null;

		StringBuffer query = new StringBuffer();

		query
				.append("SELECT ")
				.append("MIN(start) as minstart, " +
						"MAX(end) as maxend")
				.append(" FROM ")
				.append(getPrimaryTableName())
				.append(" LEFT JOIN platform ON segment.platform_id = platform.platform_id")
				.append(" LEFT JOIN study " +
						"ON study.study_id = segment.study_id")
				.append(" LEFT JOIN study_in_project ON " +
						"study.study_id = study_in_project.study_id")
				.append(" LEFT JOIN tissue_sample ON " +
						"tissue_sample.study_id = study.study_id")
				.append(" LEFT JOIN organ ON " +
						"organ_id = tissue_sample_organ_id ")
				.append(" WHERE ")
				.append("chromosome = '" + chr + "'");
		query.append(getMaxiamlOverlappingSQLWhereClause(start, end));
		query.append(getThresholdSQLClause(segMean));
		query.append(getProjectSQLClause(projectFilter));
		query.append(getOrganSQLClause(organFilter));
		// query.append(getExperimentSQLClause(experimentFilter));
		
		try {
			conn = getConnection();
			ResultSet rangeRs = executeQuery(conn, query.toString());
			
			int qstart = 0;
			int qend = 0;

			if (rangeRs.next()) {
				qstart = rangeRs.getInt(1);
				qend = rangeRs.getInt(2);
			}

			rangeRs.close();

			if (qstart == 0 && qend == 0) {
				loc = new Location(chr, start, end);
			} else {
				loc = new Location(chr, qstart, qend);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				close(conn);
			}
		}
		return loc;
	}

	@Override
	public Segment[] fetchSegmentsForStudyId(int studyId) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		Segment segment = null;
		ArrayList<Segment> segmentContainer = new ArrayList<Segment>();
		Segment[] segments = null;

		try {

			conn = getConnection();

			query
					.append("SELECT ")
					.append(super.columnsToString(columns()))
					.append(" FROM ")
					.append(super.getPrimaryTableName())
					.append(" LEFT JOIN platform ON segment.platform_id = platform.platform_id")
					.append(" WHERE ")
					.append("study_id = '" + studyId + "'")
					.append(" ORDER BY segment_id ASC");

			ResultSet rs = executeQuery(conn, query.toString());

			Object o;

			while ((o = createObject(rs)) != null) {
				segment = (Segment) o;
				segmentContainer.add(segment);
			}

			rs.close();

			segments = new Segment[segmentContainer.size()];

			segmentContainer.toArray(segments);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				close(conn);
			}
		}
		return segments;
	}

	/* FIXME Experiments need to be fetched additionally, not restrictively... */
	@Override
	public Segment[] fetchSegments(
			String chr,
			int start,
			int end,
			Double segMean,
			String[] projectFilter,
			String[] organFilter,
			String[] experimentFilter) {

		Connection conn = null;
		Segment segment = null;
		ArrayList<Segment> segmentContainer = new ArrayList<Segment>();
		Segment[] segments = null;

		StringBuffer query = new StringBuffer();

		query
				.append("SELECT ")
				.append(super.columnsToString(columns()))
				.append(" FROM ")
				.append(super.getPrimaryTableName())
				.append(" LEFT JOIN platform ON " +
						"segment.platform_id = platform.platform_id")
				.append(" LEFT JOIN study ON " +
						"study.study_id = segment.study_id")
				.append(" LEFT JOIN study_in_project ON " +
						"study.study_id = study_in_project.study_id")
				.append(" LEFT JOIN tissue_sample ON " +
						" tissue_sample.study_id = study.study_id")
				.append(" LEFT JOIN organ ON " +
						"organ_id = tissue_sample_organ_id ")
				.append(" WHERE ")
				.append("chromosome = '" + chr + "'");
		query.append(getMaxiamlOverlappingSQLWhereClause(start, end));
		query.append(getThresholdSQLClause(segMean));
		query.append(getProjectSQLClause(projectFilter));
		query.append(getOrganSQLClause(organFilter));
		query.append(getExperimentSQLClause(experimentFilter));
		query.append(" AND type = 'dnacopy'");
		
		try {
			conn = getConnection();
			ResultSet rs = executeQuery(conn, query.toString());

			Object o;

			while ((o = createObject(rs)) != null) {
				segment = (Segment) o;
				//s = sa.fetchStudyById(segment.getStudyId());
				//segment.setStudyName(s.getName());
				segmentContainer.add(segment);
			}

			rs.close();

			segments = new Segment[segmentContainer.size()];

			segmentContainer.toArray(segments);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				close(conn);
			}
		}
		return segments;
	}

	@Override
	public void deleteSegment(Segment segment) {
		Connection conn = null;
		StringBuffer segment_query = new StringBuffer();

		try {

			conn = getConnection();

			segment_query
					.append("DELETE FROM ")
					.append(super.getPrimaryTableName())
					.append(" WHERE ")
					.append("segment_id = " + segment.getId());

			executeUpdate(conn, segment_query.toString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				close(conn);
			}
		}
	}

	public void deleteSegment(int studyId) {
		Connection conn = null;
		StringBuffer segment_query = new StringBuffer();

		try {

			conn = getConnection();

			segment_query
					.append("DELETE FROM ")
					.append(super.getPrimaryTableName())
					.append(" WHERE ")
					.append("study_id = " + studyId);

			executeUpdate(conn, segment_query.toString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				close(conn);
			}
		}
	}

	public void deleteSegment(int[] studyIds) {
		for (int i = 0; i < studyIds.length; i++) {
			deleteSegment(studyIds[i]);
		}
	}
}