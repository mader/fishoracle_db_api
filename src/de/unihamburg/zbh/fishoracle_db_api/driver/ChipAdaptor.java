package de.unihamburg.zbh.fishoracle_db_api.driver;

import de.unihamburg.zbh.fishoracle_db_api.data.Chip;

public interface ChipAdaptor {

	public int storeChip(Chip chip);
	public Chip fetchChipById(int id);
	public Chip[] fetchAllChips();
	public void deleteChip(Chip chip);
	
	final static String TYPE = "ChipAdaptor";
}
