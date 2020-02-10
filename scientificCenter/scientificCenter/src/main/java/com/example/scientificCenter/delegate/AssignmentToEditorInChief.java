package com.example.scientificCenter.delegate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.domain.Journal;
import com.example.scientificCenter.domain.Membership;
import com.example.scientificCenter.domain.User;
import com.example.scientificCenter.dto.FormSubmissionDTO;
import com.example.scientificCenter.service.JournalService;
import com.example.scientificCenter.service.MembershipService;
import com.example.scientificCenter.service.ScientificAreaService;
import com.example.scientificCenter.service.UserService;


@Service
public class AssignmentToEditorInChief implements JavaDelegate{

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
	private MembershipService memberService;
	
	@Autowired
	private JournalService journalService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Start save data, SavingDataSelectedRecenzents ");
		System.out.println("membership check");
		System.out.println(execution.getVariable("initiator").toString());
		System.out.println(execution.getVariable("issn").toString());
		Journal journal = this.journalService.findByIssn(execution.getVariable("issn").toString());
		List<String> field=new ArrayList<String>();
		field.add(journal.getEditorInChief().getEmail());
		execution.setVariable("recenzentList", field);
	}
	
}