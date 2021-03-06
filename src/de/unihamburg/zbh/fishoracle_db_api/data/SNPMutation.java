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

public class SNPMutation extends GenericFeature {
	
	private String dbSnpId;
	private String ref;
	private String alt;
	private double quality;
	private String somatic;
	private String confidence;
	private String snpTool;
	
	public SNPMutation(int id,
						Location location,
						String db_snp_id,
						String ref,
						String alt,
						double quality,
						String somatic,
						String confidence,
						String snpTool) {
		super(id, location, snpTool);
		
		this.dbSnpId = db_snp_id;
		this.ref = ref;
		this.alt = alt;
		this.quality = quality;
		this.somatic = somatic;
		this.confidence = confidence;
		this.snpTool = snpTool;
	}

	public String getDbSnpId() {
		return dbSnpId;
	}

	public void setDbSnpId(String dbSnpId) {
		this.dbSnpId = dbSnpId;
	}
	
	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}
	
	public double getQuality() {
		return quality;
	}

	public void setQuality(double quality) {
		this.quality = quality;
	}

	public String getSomatic() {
		return somatic;
	}

	public void setSomatic(String somatic) {
		this.somatic = somatic;
	}

	public String getConfidence() {
		return confidence;
	}

	public void setConfidence(String confidence) {
		this.confidence = confidence;
	}

	public String getSnpTool() {
		return snpTool;
	}

	public void setSnpTool(String snpTool) {
		this.snpTool = snpTool;
	}
}