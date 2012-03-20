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

import de.unihamburg.zbh.fishoracle_db_api.data.Microarraystudy;

public interface MicroarraystudyAdaptor {

	public Microarraystudy[] fetchAllMicroarraystudies();
	public Microarraystudy[] fetchAllMicroarraystudies(boolean withChildren);
	
	public Microarraystudy fetchMicroarraystudyById(int id);
	public Microarraystudy fetchMicroarraystudyById(int id, boolean withChilden);
	
	public Microarraystudy[] fetchMicroarraystudiesForProject(int projectId);
	public Microarraystudy[] fetchMicroarraystudiesForProject(int projectId, boolean withChrildren);
	public Microarraystudy[] fetchMicroarraystudiesForProject(int[] projectIds);
	public Microarraystudy[] fetchMicroarraystudiesForProject(int[] projectIds, boolean withChrildren);
	
	public int storeMicroarraystudy(Microarraystudy mstudy,int projectId);
	public void deleteMicroarraystudy(int mstudyId);
	public void deleteMicroarraystudy(Microarraystudy mstudy);
	
	final static String TYPE = "MicroarraystudyAdaptor";
	
}
