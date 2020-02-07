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
import com.example.scientificCenter.domain.UserRole;
import com.example.scientificCenter.domain.UserRoleName;
import com.example.scientificCenter.service.JournalService;
import com.example.scientificCenter.service.MembershipService;
import com.example.scientificCenter.service.ScientificAreaService;
import com.example.scientificCenter.service.UserRoleService;
import com.example.scientificCenter.service.UserService;


@Service
public class SavingPaymentDetailsService implements JavaDelegate{

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
	
	@Autowired
	private MembershipService memService;
	
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Optional<User> user = this.userService.getByEmail(execution.getVariable("initiator").toString());
		Journal journal = this.journalService.findByIssn(execution.getVariable("issn").toString());
		if(user.isPresent()) {
			if(execution.getVariable("confirm").toString().equals("true")){
				Membership mem = new Membership();
				mem.setJournal(journal);
				mem.setAuthor(user.get());
				mem.setActive(true);
				this.memService.saveMembership(mem);
				execution.setVariable("paid", true);
			}else {
				execution.setVariable("paid", false);
			}
		}else {
			execution.setVariable("paid", false);
		}
		
	}
}
	