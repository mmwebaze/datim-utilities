package org.mwebz.datim.controllers;

import org.mwebz.datim.forms.data.UpdateOrgUnit;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FormController {

	@RequestMapping(value="/validateOrgUnitsForm")
	public @ResponseBody String validateOrgUnitForm(Model model){
		
		return "validateOrgUnits";
	}
	@RequestMapping(value="/updateOrgUnitForm")
	public String updateOrganisationUnitForm(Model model){
		model.addAttribute("updateOrgUnits", new UpdateOrgUnit());
		return "updateOrgUnits";
	}
}
