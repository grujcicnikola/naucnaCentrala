package com.example.scientificCenter.service;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.domain.Journal;
import com.example.scientificCenter.dto.FormSubmissionDTO;


@Service
public class JournalCheckingDataService implements JavaDelegate{

	@Autowired
	IdentityService identityService;
	
	@Autowired
	private JournalService journalService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		@SuppressWarnings("unchecked")
		List<FormSubmissionDTO> fieldsDTO = (List<FormSubmissionDTO>)execution.getVariable("journal");
		boolean valid = true;
		for(int i = 0; i < fieldsDTO.size(); i++) {
			if(fieldsDTO.get(i).getFieldId().equals("name") ||fieldsDTO.get(i).getFieldId().equals("issnNumber") ||
					fieldsDTO.get(i).getFieldId().equals("wayOfPaying")) {
				if(fieldsDTO.get(i).getFieldValue().equals("")) {
					valid = false;
				}
			}
			
			if(fieldsDTO.get(i).getFieldId().equals("scientificAreas")){
				if(fieldsDTO.get(i).getAreas().size()==0) {
					valid = false;
				}
			}
			if(fieldsDTO.get(i).getFieldId().equals("issnNumber")) {
				Journal journalWithSameIssn =journalService.findByIssn(fieldsDTO.get(i).getFieldValue());
				if(journalWithSameIssn !=null) {
					valid = false;
				}
			}
		}
		
		if(valid ) {
			execution.setVariable("correctData", true);
			System.out.println("validno");
		}else {
			execution.setVariable("correctData", false);
			System.out.println("nevalidno");
		}
	    
	}

}
