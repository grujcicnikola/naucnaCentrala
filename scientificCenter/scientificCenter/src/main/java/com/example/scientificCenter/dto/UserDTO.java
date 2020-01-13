package com.example.scientificCenter.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.scientificCenter.domain.User;
import com.example.scientificCenter.domain.UserRole;

public class UserDTO {
	
	private Long id;
	
	@NotNull
	@Size(min=4, max=20)
	private String name;
	
	@NotNull
	@Size(min=4, max=20)
	private String surname;
	
	@NotNull
	@Size(min=10, max=30)
	private String password;


	
	@NotNull 
	@Size(min=5, max=20)
	private String email;
	
	private List<UserRoleDTO> roles;
	
	private boolean activated;
	
	public UserDTO(){
		
	}
	
	public UserDTO(User user){
		this.id = user.getId();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.roles = new ArrayList<>();
		for(UserRole role : user.getRoles()){
			this.roles.add(new UserRoleDTO(role));
		}
		this.activated = user.isActivated();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<UserRoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRoleDTO> roles) {
		this.roles = roles;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
	
	

}
