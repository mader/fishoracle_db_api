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

package de.unihamburg.zbh.fishoracle_db_api.test;

import de.unihamburg.zbh.fishoracle_db_api.data.Chip;
import de.unihamburg.zbh.fishoracle_db_api.driver.BaseAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.ChipAdaptor;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriver;
import de.unihamburg.zbh.fishoracle_db_api.driver.FODriverImpl;

/**
 * @author Malte Mader
 *
 */
public class TestData {

	private FODriver driver;
	private ChipAdaptor ca;
	
	public TestData() {
		driver = new FODriverImpl("localhost", "emptyoracle", "fouser", "fish4me", "3306");
		ca = (ChipAdaptor) driver.getAdaptor("ChipAdaptor");
	}
	
	public void createAndStoreChipData(){
		
		Chip[] chips = createChipData();
		
		for(int i = 0; i < chips.length; i++){
			ca.storeChip(chips[i]);
		}
	}
	
	public void emptyChipTable(){
		((BaseAdaptor) ca).truncateTable(((BaseAdaptor) ca).getPrimaryTableName());
	}
	
	public Chip[] createChipData(){
		Chip chip1, chip2, chip3;
		Chip[] chips = new Chip[3];
		
		chip1 = new Chip(1, "mapping250k_sty", "snp");
		chip2 = new Chip(2, "GenomeWideSNP_6", "snp");
		chip3 = new Chip(3, "hg-u133a_2", "expression");
		
		chips[0] = chip1;
		chips[1] = chip2;
		chips[2] = chip3;
		
		return chips;
	}

	public FODriver getDriver() {
		return driver;
	}

	public void setDriver(FODriver driver) {
		this.driver = driver;
	}

	public ChipAdaptor getCa() {
		return ca;
	}

	public void setCa(ChipAdaptor ca) {
		this.ca = ca;
	}
}