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

/**
 * @author Malte Mader
 *
 */
public class Study {

	private int id;
	private Segment segments[];
	private SNPMutation mutations[];
	private Translocation translocs[][];
	private GenericFeature features[];
	private TissueSample tissue;
	private Date date;
	private String name;
	private String assembly;
	private String Description;
	private int organId;
	private int[] propertyIds;
	private int userId;
	private String files[];
	
	private boolean hasSegment;
	private boolean hasMutation;
	private boolean hasTranslocation;
	private boolean hasGeneric;
	
	public Study() {
	}

	public Study(int id,
					Date date,
					String name,
					String assembly,
					String description,
					int userId) {
		this.id = id;
		this.date = date;
		this.name = name;
		this.assembly = assembly;
		Description = description;
		this.userId = userId;
	}

	public Study(String name,
					String assembly,
					String description,
					int organId,
					int[] propertyIds,
					int userId) {
		this.segments = null;
		this.mutations = null;
		this.name = name;
		this.assembly = assembly;
		Description = description;
		this.organId = organId;
		this.propertyIds = propertyIds;
		this.userId = userId;
	}

	public Study(int id,
					TissueSample tissue,
					Date date,
					String name,
					String description) {
		this.id = id;
		this.segments = null;
		this.mutations = null;
		this.tissue = tissue;
		this.date = date;
		this.name = name;
		Description = description;
	}

	public Segment[] getSegments() {
		return segments;
	}

	public void setSegments(Segment[] segments) {
		this.segments = segments;
	}
	
	public SNPMutation[] getMutations() {
		return mutations;
	}

	public void setMutations(SNPMutation[] mutations) {
		this.mutations = mutations;
	}
	
	public Translocation[][] getTranslocs() {
		return translocs;
	}

	public void setTranslocs(Translocation[][] translocs) {
		this.translocs = translocs;
	}
	
	public GenericFeature[] getFeatures() {
		return features;
	}

	public void setFeatures(GenericFeature[] features) {
		this.features = features;
	}
	
	public String[] getFiles() {
		return files;
	}

	public void setFiles(String[] files) {
		this.files = files;
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

	public String getAssembly() {
		return assembly;
	}

	public void setAssembly(String assembly) {
		this.assembly = assembly;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getOrganId() {
		return organId;
	}

	public void setOrganId(int organId) {
		this.organId = organId;
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

	public boolean isHasSegment() {
		return hasSegment;
	}

	public void setHasSegment(boolean hasSegment) {
		this.hasSegment = hasSegment;
	}

	public boolean isHasMutation() {
		return hasMutation;
	}

	public void setHasMutation(boolean hasMutation) {
		this.hasMutation = hasMutation;
	}

	public boolean isHasTranslocation() {
		return hasTranslocation;
	}

	public void setHasTranslocation(boolean hasTranslocation) {
		this.hasTranslocation = hasTranslocation;
	}

	public boolean isHasGeneric() {
		return hasGeneric;
	}

	public void setHasGeneric(boolean hasGeneric) {
		this.hasGeneric = hasGeneric;
	}
}