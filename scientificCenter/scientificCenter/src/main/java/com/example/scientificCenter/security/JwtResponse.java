package com.example.scientificCenter.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class JwtResponse {

	private String token;
    private String type = "Bearer";
    
    public JwtResponse(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
