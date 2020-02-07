package com.example.scientificCenter.delegate;

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
import com.example.scientificCenter.service.JournalService;
import com.example.scientificCenter.service.MembershipService;
import com.example.scientificCenter.service.ScientificAreaService;
import com.example.scientificCenter.service.UserService;


@Service
public class MembershipCheckService implements JavaDelegate{

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
		System.out.println("membership check");
		System.out.println(execution.getVariable("initiator").toString());
		System.out.println(execution.getVariable("issn").toString());
		Journal journal = this.journalService.findByIssn(execution.getVariable("issn").toString());
		Optional<User> user = this.userService.getByEmail(execution.getVariable("initiator").toString());
		if(user.isPresent()) {
			Membership membership = this.memberService.findByJournalIdAndAuthorId(journal.getId(), user.get().getId());
			if(membership == null || membership.getActive()==false) {
				execution.setVariable("membership", false);
			}else {
				execution.setVariable("membership", true);
			}
		}
	}
	
}
