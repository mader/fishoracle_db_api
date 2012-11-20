/*
  Copyright (c) 2012 Malte Mader <mader@zbh.uni-hamburg.de>
  Copyright (c) 2012 Center for Bioinformatics, University of Hamburg

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

public class ConfigAttributeAdaptorImpl extends BaseAdaptor implements ConfigAttributeAdaptor {

	protected ConfigAttributeAdaptorImpl(FODriverImpl driver, String type) {
		super(driver, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int storeAttribute(String key, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] fetchAllKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] fetchKeysForConfigId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] fetchKeysForTrackConfigId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] fetchAttributesAsString(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] fetchAttributesAsInt(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] fetchAttributesAsDouble(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] fetchAttributeById(int attribId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] fetchAttribute(String Key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteAttribute(int attrib_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected String[] tables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] columns() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[][] leftJoins() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createObject(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}

}
