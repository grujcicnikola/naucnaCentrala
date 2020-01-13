package com.example.scientificCenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.example.scientificCenter.domain.UserRole;
import com.example.scientificCenter.domain.UserRoleName;
import com.example.scientificCenter.repository.UserRoleRepository;

@Service
public class UserRoleService {
	@Autowired
	private UserRoleRepository roleRep;
	
	public UserRole findRoleByName(UserRoleName name){
		
		return roleRep.findRoleByName(name);
	}

}
