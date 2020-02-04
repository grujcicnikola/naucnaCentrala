package com.example.scientificCenter.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.domain.Editor;
import com.example.scientificCenter.domain.Journal;
import com.example.scientificCenter.domain.MethodOfPayment;
import com.example.scientificCenter.domain.ScientificArea;
import com.example.scientificCenter.dto.FormSubmissionDTO;


@Service
public class NewJournalService implements JavaDelegate{

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
	private MethodOfPaymentService methodService;
	
	@Autowired
	private JournalService journalService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Start save data, journal "+execution.getVariable("name").toString());
		Journal journal = new Journal();
		journal.setIssn(execution.getVariable("issnNumber").toString());
		journal.setTitle(execution.getVariable("name").toString());
		journal.setIsActivated(false);
		if(execution.getVariable("isOpenAccess").toString().equals("true")) {
			journal.setIsOpenAccess(true);
		}else {
			journal.setIsOpenAccess(false);
		}
		journal.setIsActivated(false);
		@SuppressWarnings("unchecked")
		List<FormSubmissionDTO> fieldsDTO = (List<FormSubmissionDTO>)execution.getVariable("journal");
		Set<ScientificArea> areas= new HashSet<ScientificArea>();
		for(int i = 0; i < fieldsDTO.size(); i++) {
			if(fieldsDTO.get(i).getFieldId().equals("scientificAreas")){
				for(int j =0; j< fieldsDTO.get(i).getAreas().size(); j++) {
					//System.out.println(fieldsDTO.get(i).getAreas().get(j));
					areas.add(areaService.findById(Long.parseLong(fieldsDTO.get(i).getAreas().get(j))).get());
				}
			}
		}
		journal.setAreas(areas);
		Optional<MethodOfPayment> method =this.methodService.findById(Long.parseLong(execution.getVariable("wayOfPaying").toString()));
		//journal.setMethodOfPayment(method.get());
		Optional<com.example.scientificCenter.domain.User> editor = this.userService.getByEmail(execution.getVariable("initiator").toString());
		journal.setEditorInChief((Editor)editor.get());
		this.journalService.save(journal);
		
	}
}
