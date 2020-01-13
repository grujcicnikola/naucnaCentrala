package com.example.scientificCenter.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.scientificCenter.domain.Permission;
import com.example.scientificCenter.domain.UserRole;
import com.example.scientificCenter.domain.UserRoleName;

public class UserRoleDTO {
	
	private Long id;
	
	private UserRoleName name;
	
	private List<PermissionDTO> permissions;
	
	UserRoleDTO(){
		
	}
	
	UserRoleDTO(UserRole role){
		this.id = role.getId();
		this.name = role.getName();
		this.permissions = new ArrayList<>();
		for(Permission p : role.getPermissions()){
			this.permissions.add(new PermissionDTO(p));
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserRoleName getName() {
		return name;
	}

	public void setName(UserRoleName name) {
		this.name = name;
	}

	public List<PermissionDTO> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionDTO> permissions) {
		this.permissions = permissions;
	}
	
	
}
