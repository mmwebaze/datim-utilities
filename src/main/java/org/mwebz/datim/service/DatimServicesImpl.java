package org.mwebz.datim.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.mwebz.datim.configuration.DatimUtilityConfiguration;
import org.mwebz.datim.test.DataObject;
import org.mwebz.datim.util.ExcelScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
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
	public void getSites(String file) {
		HttpEntity<String> request = new HttpEntity<String>(datimLoginService.login(datimUtilityConfiguration.getUsername(), datimUtilityConfiguration.getPassword()));
		RestTemplate restTemplate = new RestTemplate();

		List<String> idList = excelReading.readSingleColumn(datimUtilityConfiguration.getWorkingFolder()+file, 7, 1);
		int startAtRow = 7;
		for(String id : idList){

			ResponseEntity<String> response = restTemplate.exchange(datimUtilityConfiguration.getBaseUrl()+"organisationUnits/"+id+".json", HttpMethod.GET, request, String.class);
			startAtRow++;
		}
	}

	@Override
	public void updateSite(String file, int beginAtRow, int...columns) {
		HttpHeaders headers = datimLoginService.login(datimUtilityConfiguration.getUsername(), datimUtilityConfiguration.getPassword());
		headers.add("Content-Type", "application/json");
		RestTemplate restTemplate = new RestTemplate();
		HttpClient httpClient = HttpClients.createDefault();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

		Map<String, String> idNameMap = excelReading.readMultipleColumns(datimUtilityConfiguration.getWorkingFolder()+file, beginAtRow, columns[0], columns[1]);
		for (Map.Entry<String, String> uid : idNameMap.entrySet()){
			DataObject obj = new DataObject();
			obj.setName(uid.getValue());
			HttpEntity<?> request = new HttpEntity<DataObject>(obj, headers);
			restTemplate.put(datimUtilityConfiguration.getBaseUrl()+"organisationUnits/"+uid.getKey()+"/name", request,  String.class);
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
