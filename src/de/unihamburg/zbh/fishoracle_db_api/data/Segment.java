/*
  Copyright (c) 2011-2013 Malte Mader <mader@zbh.uni-hamburg.de>
  Copyright (c) 2011-2013 Center for Bioinformatics, University of Hamburg

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

package de.unihamburg.zbh.fishoracle_db_api.data;

/**
 * @author Malte Mader
 *
 */
public class Segment extends GenericFeature {
	
	private double mean;
	private int numberOfMarkers;
	private int status;
	private double statusScore;
	private String studyName;
	
	/**
	 * Stores information about segments calculated by a segmentation algorithm.
	 * 
	 * @param id the database ID of the segment.
	 * @param loc The genomic location of the segment.
	 * @param mean Stores either the mean intensity value of the segment. A 
	 *         positive value represents a DNA amplification and a negative
	 *         value represents a DNA deletion. Or a segment score indicating
	 *         different types of deletion or amplification.
	 *         Which kind of score is stored is determined by the type.
	 * @param type Stores the segment type. Currently supported are:
	 *         cnv_intensity and cnv_status.  
	 * @param numberOfMarkers The number of SNP markers covered by the segment. 
	 */
	public Segment(int id,
						Location loc,
						String type) {
		super(id, loc, type);
		
		this.mean = -23;
		this.numberOfMarkers = -1;
		this.status = -1;
		this.statusScore = -1.0;
		this.studyName = "";
	}
	
	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public int getNumberOfMarkers() {
		return numberOfMarkers;
	}

	public void setNumberOfMarkers(int numberOfMarkers) {
		this.numberOfMarkers = numberOfMarkers;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getStatusScore() {
		return statusScore;
	}

	public void setStatusScore(double statusScore) {
		this.statusScore = statusScore;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}
}