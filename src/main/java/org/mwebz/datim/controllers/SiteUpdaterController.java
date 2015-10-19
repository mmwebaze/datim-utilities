package org.mwebz.datim.controllers;

import javax.annotation.Resource;

import org.mwebz.datim.forms.data.UpdateOrgUnit;
import org.mwebz.datim.service.DatimServices;
import org.mwebz.datim.util.JsonConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SiteUpdaterController {

	@Resource
	private DatimServices datimServices;

	@RequestMapping(value="/test")
	public String getOrgUnitLevels(Model model){
		model.addAttribute("levels", JsonConverter.organisationUnitLevels(datimServices.getOrgUnitLevels()).getOrganisationUnitLevels());
		return "orgLevels";
	}
	@RequestMapping(value="/updateOrgUnits")
	public @ResponseBody String updateOrganisationUnit(@ModelAttribute UpdateOrgUnit updateOrgUnits, Model model){
		datimServices.updateSite(updateOrgUnits.getFile(), updateOrgUnits.getUpdateReason(),updateOrgUnits.getBeginAtRowNumber(), updateOrgUnits.getIdColumnNumber(), updateOrgUnits.getNameColumnNumber());
		return "done updating";
	}

	/*@RequestMapping(value="/validateOrgUnits", method=RequestMethod.POST)
	public @ResponseBody String validateOrgUnits(@ModelAttribute ValideOrgUnit validateOrgUnits, Model model){

		return "Under Construction";
	}*/
}

