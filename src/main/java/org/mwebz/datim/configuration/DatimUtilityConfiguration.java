package org.mwebz.datim.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DatimUtilityConfiguration {
	@Autowired
	private Environment env;

	public String getWorkingFolder(){

		return env.getProperty("working.folder");
	}
	public String getBaseUrl(){

		return env.getProperty("base.url");
	}
	public String getTestUrl(){

		return env.getProperty("test.url");
	}
	public String getUsername(){

		return env.getProperty("uname");
	}
	public String getPassword(){

		return env.getProperty("pword");
	}
}
