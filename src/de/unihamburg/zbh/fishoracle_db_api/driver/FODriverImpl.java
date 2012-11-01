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

public class FODriverImpl extends DriverImpl implements FODriver{

	public FODriverImpl(String host, String database, String user,
			String password, String port) {
		super(host, database, user, password, port);
		loadAdaptors();
	}
	
	protected void loadAdaptors() {
		addAdaptor(new UserAdaptorImpl(this));
		addAdaptor(new OrganAdaptorImpl(this));
		addAdaptor(new PropertyAdaptorImpl(this));
		addAdaptor(new TissueSampleAdaptorImpl(this));
		addAdaptor(new PlatformAdaptorImpl(this));
		addAdaptor(new SegmentAdaptorImpl(this));
		addAdaptor(new SNPMutationAdaptorImpl(this));
		addAdaptor(new TranslocationAdaptorImpl(this));
		addAdaptor(new GenericAdaptorImpl(this));
		addAdaptor(new StudyAdaptorImpl(this));
		addAdaptor(new GroupAdaptorImpl(this));
		addAdaptor(new ProjectAdaptorImpl(this));
		addAdaptor(new EnsemblDBsAdaptorImpl(this));
	}

	@Override
	public synchronized UserAdaptor getUserAdaptor() {
		return (UserAdaptor) getAdaptor(UserAdaptor.TYPE);
	}

	@Override
	public OrganAdaptor getOrganAdaptor() {
		return (OrganAdaptor) getAdaptor(OrganAdaptor.TYPE);
	}

	@Override
	public PropertyAdaptor getPropertyAdaptor() {
		return (PropertyAdaptor) getAdaptor(PropertyAdaptor.TYPE);
	}
	
	@Override
	public TissueSampleAdaptor getTissueSampleAdaptor() {
		return (TissueSampleAdaptor) getAdaptor(TissueSampleAdaptor.TYPE);
	}
	
	@Override
	public PlatformAdaptor getPlatformAdaptor() {
		return (PlatformAdaptor) getAdaptor(PlatformAdaptor.TYPE);
	}
	
	@Override
	public SegmentAdaptor getSegmentAdaptor() {
		return (SegmentAdaptor) getAdaptor(SegmentAdaptor.TYPE);
	}
	
	@Override
	public SNPMutationAdaptor getSNPMutationAdaptor() {
		return (SNPMutationAdaptor) getAdaptor(SNPMutationAdaptor.TYPE);
	}
	
	@Override
	public TranslocationAdaptor getTranslocationAdaptor() {
		return (TranslocationAdaptor) getAdaptor(TranslocationAdaptor.TYPE);
	}
	
	@Override
	public GenericAdaptor getGenericAdaptor() {
		return (GenericAdaptor) getAdaptor(GenericAdaptor.TYPE);
	}
	
	@Override
	public StudyAdaptor getStudyAdaptor() {
		return (StudyAdaptor) getAdaptor(StudyAdaptor.TYPE);
	}

	@Override
	public GroupAdaptor getGroupAdaptor() {
		return (GroupAdaptor) getAdaptor(GroupAdaptor.TYPE);
	}

	@Override
	public ProjectAdaptor getProjectAdaptor() {
		return (ProjectAdaptor) getAdaptor(ProjectAdaptor.TYPE);
	}
	
	@Override
	public EnsemblDBsAdaptor getEnsemblDBsAdaptor() {
		return (EnsemblDBsAdaptor) getAdaptor(EnsemblDBsAdaptor.TYPE);
	}
}
