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
		return new String[] { "segment_id",
								"location.location_id",
								"location.location_chromosome",
								"location.location_start",
								"location.location_end",
								"mean",
								"markers",
								"status",
								"status_score",
								"type",
								"segment.platform_id",
								"platform_name",
								"study_id" };
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
		StringBuffer loc_query = new StringBuffer();
		int newLocId = 0;
		int newSegmentId = 0;

		try {

			conn = getConnection();

			loc_query
					.append("INSERT INTO location ")
					.append("(location_chromosome, " + 
							"location_start, " +
							"location_end) ")
					.append(" VALUES ")
					.append("('" + segment.getLocation().getChromosome() +
							 "', '" +
							 segment.getLocation().getStart() +
							 "', '" +
							 segment.getLocation().getEnd() + "')");

			ResultSet rs = executeUpdateGetKeys(conn, loc_query.toString());

			if (rs.next()) {
				newLocId = rs.getInt(1);
			}

			rs.close();

			segment_query
					.append("INSERT INTO ")
					.append(getPrimaryTableName())
					.append("(location_id, " + 
							"mean, " +
							"markers, " +
							"status, " +
							"status_score, " +
							"type, " +
							", platform_id" +
							"study_id)")
					.append(" VALUES ")
					.append("('" + newLocId + 
							"', '" + segment.getMean() +
							"', '" + segment.getNumberOfMarkers() +
							"', '" + segment.getStatus() +
							"', '" + segment.getStatusScore() +
							"', '" + segment.getType() +
							"', '" + segment.getPlatformId() +
							"', '" + studyId + "')");

			rs = executeUpdateGetKeys(conn, segment_query.toString());

			if (rs.next()) {
				newSegmentId = rs.getInt(1);
			}

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
		int loc_id = 0;
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
				loc_id = rs.getInt(2);
				chromosome = rs.getString(3);
				start = rs.getInt(4);
				end = rs.getInt(5);
				mean = rs.getDouble(6);
				numberOfMarkers = rs.getInt(7);
				status = rs.getInt(8);
				statusScore = rs.getDouble(9);
				type = rs.getString(10);
				platformId = rs.getInt(11);
				platformName = rs.getString(12);
				studyId = rs.getInt(13);

				loc = new Location(loc_id, chromosome, start, end);

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
					.append(" LEFT JOIN location ON segment.location_id =" +
							" location.location_id")
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
					.append(
							"location.location_id, "
									+ "location.location_chromosome, "
									+ "location.location_start, "
									+ "location.location_end")
					.append(" FROM ")
					.append(getPrimaryTableName())
					.append(
							" LEFT JOIN location ON segment.location_id = " +
							"location.location_id")
					.append(" LEFT JOIN platform ON segment.platform_id = platform.platform_id")
					.append(" WHERE ")
					.append("segment_id = " + segmentId);

			ResultSet rs = executeQuery(conn, query.toString());

			int loc_id = 0;
			int start = 0;
			int end = 0;
			String chr = null;
			while (rs.next()) {
				loc_id = rs.getInt(1);
				chr = rs.getString(2);
				start = rs.getInt(3);
				end = rs.getInt(4);

				loc = new Location(loc_id, chr, start, end);
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

	private String getThresholdSQLClause(Double lowerTh, Double upperTh) {
		String qrystr = "";
		if (lowerTh == null && upperTh != null) {
			qrystr = qrystr + " AND mean > '" + upperTh + "'";
		}
		if (lowerTh != null && upperTh == null) {
			qrystr = qrystr + " AND mean < '" + lowerTh + "'";
		}
		if (lowerTh != null && upperTh != null) {
			qrystr =
					qrystr + " AND (mean < '" + lowerTh + "' AND "
							+ "mean > '" + upperTh + "')";
		}
		return qrystr;
	}

	private String getProjectSQLClause(int[] projectFilter) {

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

	private String getOrganSQLClause(int[] organFilter) {
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

	private String getExperimentSQLClause(int[] experimentFilter) {

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
			Double lowerTh,
			Double upperTh,
			int[] projectFilter,
			int[] organFilter,
			int[] experimentFilter) {

		Location loc = null;
		Connection conn = null;

		StringBuffer query = new StringBuffer();

		query
				.append("SELECT ")
				.append("location.location_id, " +
						"MIN(location_start) as minstart, " +
						"MAX(location_end) as maxend")
				.append(" FROM ")
				.append(getPrimaryTableName())
				.append(" LEFT JOIN location " +
						"ON segment.location_id = location.location_id")
				.append(" LEFT JOIN platform ON segment.platform_id = platform.platform_id")
				.append(" LEFT JOIN study " +
						"ON study.study_id = segment.study_id")
				.append(" LEFT JOIN study_in_project ON " +
						"study.study_id = study_in_project.study_id")
				.append(" LEFT JOIN tissue_sample ON " +
						"tissue_sample.tissue_sample_id = study.tissue_sample_id")
				.append(" LEFT JOIN organ ON " +
						"organ_id = tissue_sample_organ_id ")
				.append(" WHERE ")
				.append("location_chromosome = '" + chr + "'");
		query.append(getMaxiamlOverlappingSQLWhereClause(start, end));
		query.append(getThresholdSQLClause(lowerTh, upperTh));
		query.append(getProjectSQLClause(projectFilter));
		query.append(getOrganSQLClause(organFilter));
		// query.append(getExperimentSQLClause(experimentFilter));
		
		try {
			conn = getConnection();
			ResultSet rangeRs = executeQuery(conn, query.toString());

			int loc_id = 0;
			int qstart = 0;
			int qend = 0;

			if (rangeRs.next()) {
				loc_id = rangeRs.getInt(1);
				qstart = rangeRs.getInt(2);
				qend = rangeRs.getInt(3);
			}

			rangeRs.close();

			if (qstart == 0 && qend == 0) {
				loc = new Location(loc_id, chr, start, end);
			} else {
				loc = new Location(loc_id, chr, qstart, qend);
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
					.append(
							" LEFT JOIN location " +
							"ON segment.location_id = location.location_id")
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
			Double lowerTh,
			Double upperTh,
			int[] projectFilter,
			int[] organFilter,
			int[] experimentFilter) {

		Connection conn = null;
		Segment segment = null;
		ArrayList<Segment> segmentContainer = new ArrayList<Segment>();
		Segment[] segments = null;
		StudyAdaptor sa = driver.getStudyAdaptor();
		Study s;

		StringBuffer query = new StringBuffer();

		query
				.append("SELECT ")
				.append(super.columnsToString(columns()))
				.append(" FROM ")
				.append(super.getPrimaryTableName())
				.append(" LEFT JOIN location ON " +
						"segment.location_id = location.location_id")
				.append(" LEFT JOIN study ON " +
						"study.study_id = segment.study_id")
				.append(" LEFT JOIN study_in_project ON " +
						"study.study_id = study_in_project.study_id")
				.append(" LEFT JOIN sample_on_platform ON " +
						" sample_on_platform_id = study_sample_on_platform_id")
				.append(" LEFT JOIN tissue_sample ON " +
						"tissue_sample_id = sample_on_chip_tissue_sample_id")
				.append(" LEFT JOIN organ ON " +
						"organ_id = tissue_sample_organ_id ")
				.append(" WHERE ")
				.append("location_chromosome = '" + chr + "'");
		query.append(getMaxiamlOverlappingSQLWhereClause(start, end));
		query.append(getThresholdSQLClause(lowerTh, upperTh));
		query.append(getProjectSQLClause(projectFilter));
		query.append(getOrganSQLClause(organFilter));
		query.append(getExperimentSQLClause(experimentFilter));

		try {
			conn = getConnection();
			ResultSet rs = executeQuery(conn, query.toString());

			Object o;

			while ((o = createObject(rs)) != null) {
				segment = (Segment) o;
				s = sa.fetchStudyById(segment.getStudyId());
				segment.setStudyName(s.getName());
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
		StringBuffer loc_query = new StringBuffer();
		StringBuffer segment_query = new StringBuffer();

		try {

			conn = getConnection();

			loc_query
					.append("DELETE FROM location")
					.append(" WHERE ")
					.append("location_id = " + segment.getLocation().getId());

			executeUpdate(conn, loc_query.toString());

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
		StringBuffer loc_query = new StringBuffer();
		StringBuffer segment_query = new StringBuffer();

		try {

			conn = getConnection();

			loc_query
					.append("DELETE location.* FROM")
					.append(" location")
					.append(
							" LEFT JOIN segment ON " +
							"segment.location_id = location.location_id")
					.append(" WHERE ")
					.append("study_id = " + studyId);

			executeUpdate(conn, loc_query.toString());

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