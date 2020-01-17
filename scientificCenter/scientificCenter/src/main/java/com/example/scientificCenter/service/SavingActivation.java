package com.example.scientificCenter.service;

import java.util.Optional;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.domain.Journal;
import com.example.scientificCenter.domain.User;
import com.example.scientificCenter.domain.UserRole;
import com.example.scientificCenter.domain.UserRoleName;


@Service
public class SavingActivation implements JavaDelegate{

	@Autowired
	IdentityService identityService;
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private JavaMailSender javaMailSender;
		
	@Autowired
	private UserService userService;
		
	@Autowired
	private ScientificAreaService areaService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private JournalService journalService;
	
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Optional<User> user = this.userService.getByEmail(execution.getVariable("email").toString());
		if(user.isPresent()) {
			if(execution.getVariable("confirm").toString().equals("true")){
			
				UserRole role = this.userRoleService.findRoleByName(UserRoleName.ROLE_USER);
				user.get().setActivated(true);
				user.get().getRoles().add(role);
				userService.save(user.get());
				org.camunda.bpm.engine.identity.User newUser = identityService.newUser(execution.getVariable("username").toString());
				newUser.setFirstName(execution.getVariable("name").toString());
				newUser.setLastName(execution.getVariable("surname").toString());
				newUser.setEmail(execution.getVariable("email").toString());
				newUser.setPassword(execution.getVariable("password").toString());
				identityService.saveUser(newUser);
				
			}
		}
		
	}
}
	

