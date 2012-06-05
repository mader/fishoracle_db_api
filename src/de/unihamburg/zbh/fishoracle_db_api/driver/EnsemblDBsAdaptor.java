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

import de.unihamburg.zbh.fishoracle_db_api.data.EnsemblDBs;

/**
 * Stores available Ensembl databases.
 * 
 * @author Malte Mader
 *
 */
public interface EnsemblDBsAdaptor {

	/**
	 * Stores a new Ensembl database name.
	 * 
	 * @param edbs
	 * @return Returns the database ID of the newly added database name.
	 */
	public int storeDB(EnsemblDBs edbs);
	
	/**
	 * Fetches all database names.
	 * 
	 * @return Array of Ensembl databases objects.
	 */
	public EnsemblDBs[] fetchAllDBs();
	
	/**
	 * Remove a database name from the database.
	 * 
	 * @param edbsId The database name id.
	 */
	public void deleteDB(int edbsId);
	
	final static String TYPE = "EnsemblDBsAdaptor";
}