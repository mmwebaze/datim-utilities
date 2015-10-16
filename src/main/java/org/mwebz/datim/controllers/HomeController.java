package org.mwebz.datim.controllers;

import javax.annotation.Resource;

import org.mwebz.datim.configuration.DatimUtilityConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	@Resource
	DatimUtilityConfiguration datimUtilityConfiguration;
	
	@RequestMapping(value={"/", "/index", "home"}, method = RequestMethod.GET)
	public String defaultPage(Model model){
		 model.addAttribute("working_dir",datimUtilityConfiguration.getWorkingFolder());
		 model.addAttribute("base_url",datimUtilityConfiguration.getBaseUrl());
		return "index";
	}

}
