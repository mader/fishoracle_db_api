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

package de.unihamburg.zbh.fishoracle_db_api.data;

/**
 * @author Malte Mader
 *
 */
public class CnSegment {

	private int id;
	private String chromosome;
	private int start;
	private int end;
	private double mean;
	private int numberOfMarkers;
	private int microarraystudyId;
	private String microarraystudyName;
	
	/**
	 * Stores information about segments calculated by a segmentation algorithm.
	 * 
	 * @param id the database ID of the segment.
	 * @param chromosome The Chromosome.
	 * @param start The start position of the segment.
	 * @param end The end position of the segment.
	 * @param mean The meqan intensity value of the segment. A positive value
	 *         represents a DNA amplification and a negative value represents
	 *         a DNA deletion. 
	 * @param numberOfMarkers The number of SNP markers covered by the segment. 
	 */
	public CnSegment(int id, String chromosome, int start, int end,
			double mean, int numberOfMarkers) {
		this.id = id;
		this.chromosome = chromosome;
		this.start = start;
		this.end = end;
		this.mean = mean;
		this.numberOfMarkers = numberOfMarkers;
		this.microarraystudyId = 0;
	}
	
	public CnSegment(int id, String chromosome, int start,
			int end, double mean, int numberOfMarkers, int microarraystudyId) {
		this.id = id;
		this.chromosome = chromosome;
		this.start = start;
		this.end = end;
		this.mean = mean;
		this.numberOfMarkers = numberOfMarkers;
		this.microarraystudyId = microarraystudyId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getChromosome() {
		return chromosome;
	}

	public void setChromosome(String chromosome) {
		this.chromosome = chromosome;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
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

	public int getMicroarraystudyId() {
		return microarraystudyId;
	}

	public void setMicroarraystudyId(int microarraystudyId) {
		this.microarraystudyId = microarraystudyId;
	}

	public String getMicroarraystudyName() {
		return microarraystudyName;
	}

	public void setMicroarraystudyName(String microarraystudyName) {
		this.microarraystudyName = microarraystudyName;
	}
}