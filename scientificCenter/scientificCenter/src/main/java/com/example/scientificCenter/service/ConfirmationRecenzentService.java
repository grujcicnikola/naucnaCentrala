package com.example.scientificCenter.service;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.domain.User;
import com.example.scientificCenter.domain.UserRole;
import com.example.scientificCenter.domain.UserRoleName;


@Service
public class ConfirmationRecenzentService implements JavaDelegate{

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
		
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		User user = this.userService.findByUsername(execution.getVariable("username").toString());
		//System.out.println("***promenljiva "+execution.getVariable("recenzent").toString());
		if(execution.getVariable("recenzent").toString().equals("true")) {
			UserRole role = this.userRoleService.findRoleByName(UserRoleName.ROLE_RECENZENT);
			user.setIsRecenzent(true);
			user.getRoles().add(role);
			this.userService.save(user);
		}
	}
	
}
