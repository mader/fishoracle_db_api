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

import java.util.Date;

public class Microarraystudy {

	private int id;
	private CnSegment segments[];
	private Chip chip;
	private TissueSample tissue;
	private Date date;
	private String name;
	private String Description;
	private int chipId;
	private int organ_id;
	private int[] propertyIds;
	private int userId;
	
	public Microarraystudy(int id, Date date, String name, String description,
			int userId) {
		super();
		this.id = id;
		this.date = date;
		this.name = name;
		Description = description;
		this.userId = userId;
	}

	public Microarraystudy(CnSegment[] segments, String name,
			String description, int chipId, int organId, int[] propertyIds,
			int userId) {
		super();
		this.segments = segments;
		this.name = name;
		Description = description;
		this.chipId = chipId;
		organ_id = organId;
		this.propertyIds = propertyIds;
		this.userId = userId;
	}

	public Microarraystudy(int id, CnSegment[] segments, Chip chip,
			TissueSample tissue, Date date, String name,
			String description) {
		super();
		this.id = id;
		this.segments = segments;
		this.chip = chip;
		this.tissue = tissue;
		this.date = date;
		this.name = name;
		Description = description;
	}

	public CnSegment[] getSegments() {
		return segments;
	}

	public void setSegments(CnSegment[] segments) {
		this.segments = segments;
	}

	public Chip getChip() {
		return chip;
	}

	public void setChip(Chip chip) {
		this.chip = chip;
	}

	public TissueSample getTissue() {
		return tissue;
	}

	public void setTissue(TissueSample tissue) {
		this.tissue = tissue;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getChipId() {
		return chipId;
	}

	public void setChipId(int chipId) {
		this.chipId = chipId;
	}

	public int getOrgan_id() {
		return organ_id;
	}

	public void setOrgan_id(int organId) {
		organ_id = organId;
	}

	public int[] getPropertyIds() {
		return propertyIds;
	}

	public void setPropertyIds(int[] propertyIds) {
		this.propertyIds = propertyIds;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
