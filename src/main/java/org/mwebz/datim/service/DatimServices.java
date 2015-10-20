package org.mwebz.datim.service;

import java.util.List;

public interface DatimServices {
	
	public abstract List<String> validateSites(String file, int beginAtRow, int column);
	public abstract void updateSite(String file, int updateReason, int beginAtRow, int...columns);
	public abstract String getOrgUnitLevels();
}
