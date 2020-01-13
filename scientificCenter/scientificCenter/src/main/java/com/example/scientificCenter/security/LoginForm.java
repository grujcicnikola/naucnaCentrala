package com.example.scientificCenter.security;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class LoginForm {
	
    private String email;

    private String password;

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
