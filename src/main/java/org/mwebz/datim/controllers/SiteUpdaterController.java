package org.mwebz.datim.controllers;

import javax.annotation.Resource;

import org.mwebz.datim.service.DatimServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SiteUpdaterController {

	@Resource
	private DatimServices datimServices;
	
	@RequestMapping(value="/orgunit")
	public @ResponseBody String getOrgUnit(){
		datimServices.getSites();

		return "Done";
	}
	@RequestMapping(value="/updateOrgUnit")
	public @ResponseBody String updateOrganisationUnit(){
		datimServices.updateSite();
		
		return "Done";
	}
}

