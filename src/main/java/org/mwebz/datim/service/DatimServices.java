package org.mwebz.datim.service;

public interface DatimServices {
	
	public abstract void getSites(String file);
	public abstract void updateSite(String file, int beginAtRow, int...columns);
	public abstract String getOrgUnitLevels();
}
