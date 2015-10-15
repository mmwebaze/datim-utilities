package org.mwebz.datim.util;


import org.mwebz.datim.domain.OrganisationUnit;
import org.mwebz.datim.test.DataObject;

import com.google.gson.Gson;

public class JsonConverter {
	
	public static OrganisationUnit organisationUnit(String jsonString){
		Gson gson = new Gson();
		OrganisationUnit orgUnit = gson.fromJson(jsonString, OrganisationUnit.class);
		//System.out.println(orgUnit);
		
		return orgUnit;
	}
	public static String createJson(){
		
		Gson gson = new Gson();
		DataObject obj = new DataObject();
		obj.setName("Aba North");
		
		return gson.toJson(obj);
	}
}
