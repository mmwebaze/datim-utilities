package org.mwebz.datim.service;

import org.springframework.http.HttpHeaders;

public interface LoginService {

	public abstract HttpHeaders login(String username, String password);

}
