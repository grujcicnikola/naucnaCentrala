package com.example.scientificCenter.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.example.scientificCenter.domain.UserRole;

public class JwtResponse {

	private String token;
    private String type = "Bearer";
   // private Collection<UserRole> authorities;
    
    
    
    public JwtResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    public JwtResponse(String token) {
		super();
		this.token = token;
	}

	/*public JwtResponse(String token,  Collection<UserRole> authorities) {
		super();
		this.token = token;
		this.authorities = authorities;
	}*/

	

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



	/*public Collection<UserRole> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<UserRole> authorities) {
		this.authorities = authorities;
	}*/

}
