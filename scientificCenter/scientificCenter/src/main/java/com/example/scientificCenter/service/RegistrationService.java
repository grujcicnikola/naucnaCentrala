package com.example.scientificCenter.service;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.*;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.dto.FormSubmissionDTO;

@Service
public class RegistrationService implements JavaDelegate{

	@Autowired
	IdentityService identityService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		@SuppressWarnings("unchecked")
		List<FormSubmissionDTO> fieldsDTO = (List<FormSubmissionDTO>)execution.getVariable("registration");
		System.out.println(execution.getVariable("email").toString());
		boolean valid = true;
		for(int i = 0; i < fieldsDTO.size(); i++) {
			if(fieldsDTO.get(i).getFieldId().equals("name") ||fieldsDTO.get(i).getFieldId().equals("surname") ||
					fieldsDTO.get(i).getFieldId().equals("city")||fieldsDTO.get(i).getFieldId().equals("state") ||
					fieldsDTO.get(i).getFieldId().equals("email")||fieldsDTO.get(i).getFieldId().equals("username") ||
					fieldsDTO.get(i).getFieldId().equals("password") ) {
				if(fieldsDTO.get(i).getFieldValue().equals("")) {
					valid = false;
				}
			}
			if(fieldsDTO.get(i).getFieldId().equals("scientificAreas")){
				if(fieldsDTO.get(i).getAreas().size()==0) {
					valid = false;
				}
			}
		}
		identityService = execution.getProcessEngine().getIdentityService();
		User userWithSameUsername = identityService.createUserQuery().userId(execution.getVariable("username").toString()) .singleResult();
		
		if(valid && userWithSameUsername == null) {
			execution.setVariable("correctData", true);
			System.out.println("validno");
		}else {
			execution.setVariable("correctData", false);
			System.out.println("nevalidno");
		}
	    
	}

}
