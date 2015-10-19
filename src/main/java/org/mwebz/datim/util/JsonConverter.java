package org.mwebz.datim.util;


import org.mwebz.datim.domain.OrganisationUnit;
import org.mwebz.datim.domain.OrganisationUnitLevels;
import org.mwebz.datim.test.DataObject;

import com.google.gson.Gson;

public class JsonConverter {
	
	public static OrganisationUnit organisationUnit(String jsonString){
		Gson gson = new Gson();
		OrganisationUnit orgUnit = gson.fromJson(jsonString, OrganisationUnit.class);
		
		return orgUnit;
	}
	public static OrganisationUnitLevels organisationUnitLevels(String jsonString){
		System.out.println(jsonString);
		Gson gson = new Gson();
		OrganisationUnitLevels orgUnitLevels = gson.fromJson(jsonString, OrganisationUnitLevels.class);
		
		return orgUnitLevels;
	}
	public static String createJson(OrganisationUnit obj){
		
		Gson gson = new Gson();
		/*DataObject obj = new DataObject();
		obj.setName("Aba North");*/
		
		return gson.toJson(obj);
	}
}
