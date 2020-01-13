package com.example.scientificCenter.dto;

import com.example.scientificCenter.domain.Permission;

public class PermissionDTO {
private Long id;
	
	private String name;
	
	public PermissionDTO(){
		
	}
	
	public PermissionDTO(Permission p){
		this.id = p.getId();
		this.name = p.getName();
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
	
	
}
