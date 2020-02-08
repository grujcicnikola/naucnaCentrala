package com.example.scientificCenter.delegate;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.domain.Journal;
import com.example.scientificCenter.service.JournalService;
import com.example.scientificCenter.service.ScientificAreaService;
import com.example.scientificCenter.service.UserService;


@Service
public class SendingRejectionEmail implements JavaDelegate{

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
	private JournalService journalService;
	
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Journal journal = this.journalService.findByIssn(execution.getVariable("issn").toString());
		//sendConfirmationEmail(execution, execution.getVariable("initiator").toString(),journal.getTitle());
		
	}

	public void sendConfirmationEmail(DelegateExecution execution ,String email, String title)throws MailException, InterruptedException{
		System.out.println("mejl za "+email);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom("petarperic23252@gmail.com");
		mail.setSubject("Scientific central - confirm registration");
		mail.setText("Hello,\n Your paper submitted to magazine "+title+" is rejected!");
		
		javaMailSender.send(mail);
		System.out.println("Email poslat!");
		
		
	}
}
