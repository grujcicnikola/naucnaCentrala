package com.example.scientificCenter.service;

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
		User user = this.userService.findByUsername(execution.getVariable("username").toString());
		if(execution.getVariable("confirm").toString().equals("true")){
			if(execution.getVariable("isRecenzent").toString().equals("false")) {
				UserRole role = this.userRoleService.findRoleByName(UserRoleName.ROLE_USER);
				user.setActivated(true);
				user.getRoles().add(role);
				userService.save(user);
			}	
		}
		
	}
}
	

