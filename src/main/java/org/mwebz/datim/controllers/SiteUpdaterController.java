package org.mwebz.datim.controllers;

import javax.annotation.Resource;

import org.mwebz.datim.forms.data.UpdateOrgUnit;
import org.mwebz.datim.service.DatimServices;
import org.mwebz.datim.util.JsonConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SiteUpdaterController {

	@Resource
	private DatimServices datimServices;
	private UpdateOrgUnit validateOrgUnits;

	@RequestMapping(value="/test")
	public String getOrgUnitLevels(Model model){
		model.addAttribute("levels", JsonConverter.organisationUnitLevels(datimServices.getOrgUnitLevels()).getOrganisationUnitLevels());
		return "orgLevels";
	}
	@RequestMapping(value="/updateOrgUnits")
	public  String updateOrganisationUnit(@ModelAttribute UpdateOrgUnit updateOrgUnits, Model model){
		int reason = updateOrgUnits.getUpdateReason();

		if (reason == 1 || reason == 2){
			datimServices.updateSite(updateOrgUnits.getFile(), reason,updateOrgUnits.getBeginAtRowNumber(), updateOrgUnits.getIdColumnNumber(), updateOrgUnits.getNameColumnNumber());
			return "redirect:/";
		}
		if (reason == 3 ){
			//model.addAttribute("updateOrgUnits",updateOrgUnits);
			validateOrgUnits = updateOrgUnits;
			return "redirect:/validateOrgUnits";
		}
		else {
			return "redirect:/updateOrgUnits";
		}
	}

	/*@RequestMapping(value="/validateOrgUnits", method=RequestMethod.POST)
	public @ResponseBody String validateOrgUnits(@ModelAttribute ValideOrgUnit validateOrgUnits, Model model){

		return "Under Construction";
	}*/
	@RequestMapping(value="/validateOrgUnits")
	public String validateOrgUnits(@ModelAttribute UpdateOrgUnit updateOrgUnits, Model model){
		if (validateOrgUnits == null)
			return "redirect:/updateOrgUnitForm";
		//model.addAttribute("validity", datimServices.validateSites("car.xlsx", 7, 1));
		model.addAttribute("validity", datimServices.validateSites(validateOrgUnits.getFile(), validateOrgUnits.getBeginAtRowNumber(), validateOrgUnits.getIdColumnNumber()));
		return "validityReport";
	}
}

