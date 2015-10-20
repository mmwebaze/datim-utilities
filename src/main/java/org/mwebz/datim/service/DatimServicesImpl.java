package org.mwebz.datim.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.mwebz.datim.configuration.DatimUtilityConfiguration;
import org.mwebz.datim.domain.OrganisationUnit;
import org.mwebz.datim.util.ExcelScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class DatimServicesImpl implements DatimServices{

	@Resource
	private ExcelScan excelReading;
	@Resource
	DatimUtilityConfiguration datimUtilityConfiguration;
	@Resource
	private LoginService datimLoginService;
	@Override
	public List<String> validateSites(String file, int beginAtRow, int column) {
		HttpEntity<String> request = new HttpEntity<String>(datimLoginService.login(datimUtilityConfiguration.getUsername(), datimUtilityConfiguration.getPassword()));
		RestTemplate restTemplate = new RestTemplate();
		List<String> validityList = new ArrayList<String>();
		List<String> uIdList = excelReading.readSingleColumn(datimUtilityConfiguration.getWorkingFolder()+file, beginAtRow, column);
		for(String uId : uIdList){
			try{
				ResponseEntity<String> response = restTemplate.exchange(datimUtilityConfiguration.getBaseUrl()+"organisationUnits/"+uId+".json", HttpMethod.GET, request, String.class);
				validityList.add(uId+" --> "+response.getStatusCode());
				System.out.println(uId+" --> "+response.getStatusCode());
			}
			catch(HttpClientErrorException e){
				validityList.add(uId+" --> "+e.getStatusCode());
				System.out.println(uId+" # --> "+e.getStatusCode());
			}
		}
		
		return validityList;
	}

	@Override
	public void updateSite(String file, int updateReason, int beginAtRow, int...columns) {
		HttpHeaders headers = datimLoginService.login(datimUtilityConfiguration.getUsername(), datimUtilityConfiguration.getPassword());
		headers.add("Content-Type", "application/json");
		RestTemplate restTemplate = new RestTemplate();
		HttpClient httpClient = HttpClients.createDefault();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

		StringBuilder builder = new StringBuilder();

		switch(updateReason){
		case 1:
			builder.append("/name");
			break;
		case 2:
			builder.append("/");
			break;
		default:
			break;
		}

		Map<String, String> idNameMap = excelReading.readMultipleColumns(datimUtilityConfiguration.getWorkingFolder()+file, beginAtRow, columns[0], columns[1]);
		for (Map.Entry<String, String> orgUnitMap : idNameMap.entrySet()){
			OrganisationUnit obj = new OrganisationUnit();
			if (updateReason == 1)
				obj.setName(orgUnitMap.getValue());
			if (updateReason == 2){
				OrganisationUnit obj2 = new OrganisationUnit();
				obj2.setId(orgUnitMap.getValue());
				obj.setParent(obj2);
			}
			//System.out.println(JsonConverter.createJson(obj));
			HttpEntity<?> request = new HttpEntity<OrganisationUnit>(obj, headers);
			restTemplate.put(datimUtilityConfiguration.getBaseUrl()+"organisationUnits/"+orgUnitMap.getKey()+builder.toString(), request,  String.class);
		}
	}

	@Override
	public String getOrgUnitLevels() {
		HttpEntity<String> request = new HttpEntity<String>(datimLoginService.login(datimUtilityConfiguration.getUsername(), datimUtilityConfiguration.getPassword()));
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(datimUtilityConfiguration.getBaseUrl()+"organisationUnitLevels.json", HttpMethod.GET, request, String.class);

		return response.getBody();
	}

}
