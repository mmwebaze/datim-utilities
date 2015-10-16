package org.mwebz.datim.domain;

import java.util.List;

public class OrganisationUnitLevels {
	private Pager pager;
	private List<OrganisationUnitLevel> organisationUnitLevels;
	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	public List<OrganisationUnitLevel> getOrganisationUnitLevels() {
		return organisationUnitLevels;
	}
	public void setOrganisationUnitLevels(
			List<OrganisationUnitLevel> organisationUnitLevels) {
		this.organisationUnitLevels = organisationUnitLevels;
	}
}
