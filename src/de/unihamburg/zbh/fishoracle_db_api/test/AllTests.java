/*
  Copyright (c) 2012-2013 Malte Mader <mader@zbh.uni-hamburg.de>
  Copyright (c) 2012-2013 Center for Bioinformatics, University of Hamburg

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

package de.unihamburg.zbh.fishoracle_db_api.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTestSuite(SegmentTest.class);
		suite.addTestSuite(EnsemblDBsAdaptorTest.class);
		suite.addTestSuite(GroupAdaptorTest.class);
		suite.addTestSuite(OrganAdaptorTest.class);
		suite.addTestSuite(PlatformAdaptorTest.class);
		suite.addTestSuite(ProjectAdaptorTest.class);
		suite.addTestSuite(PropertyAdaptorTest.class);
		suite.addTestSuite(SNPMutationAdaptorTest.class);
		suite.addTestSuite(TranslocationAdaptorTest.class);
		suite.addTestSuite(StudyAdaptorTest.class);
		suite.addTestSuite(TissueSampleAdaptorTest.class);
		suite.addTestSuite(UserAdaptorTest.class);
		suite.addTestSuite(ConfigAttributeAdaptorTest.class);
		suite.addTestSuite(TrackConfigAdaptorTest.class);
		suite.addTestSuite(ConfigAdaptorTest.class);
		
		return suite;
	}
}
