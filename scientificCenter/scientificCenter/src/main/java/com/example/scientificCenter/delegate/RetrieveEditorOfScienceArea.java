package com.example.scientificCenter.delegate;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.domain.Author;
import com.example.scientificCenter.domain.Editor;
import com.example.scientificCenter.domain.Journal;
import com.example.scientificCenter.domain.Paper;
import com.example.scientificCenter.domain.ScientificArea;
import com.example.scientificCenter.domain.User;
import com.example.scientificCenter.dto.FormSubmissionDTO;
import com.example.scientificCenter.service.JournalService;
import com.example.scientificCenter.service.MethodOfPaymentService;
import com.example.scientificCenter.service.PaperService;
import com.example.scientificCenter.service.ScientificAreaService;
import com.example.scientificCenter.service.UserService;


@Service
public class RetrieveEditorOfScienceArea implements JavaDelegate{

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
	
	@Autowired
	private PaperService paperService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Paper paper = this.paperService.findByTitle(execution.getVariable("title").toString());
		Journal journal = this.journalService.findByIssn(execution.getVariable("issn").toString());
		Optional<Editor> editor = this.journalService.findEditorByJournal(journal);
		if(editor.isPresent()) {
			execution.setVariable("editorOfScienceArea", editor.get().getEmail());
			//sendConfirmationEmail(execution, editor.get().getEmail(),journal.getTitle());
		}else {
			execution.setVariable("editorOfScienceArea", journal.getEditorInChief().getEmail());
			//sendConfirmationEmail(execution, journal.getEditorInChief().getEmail(),journal.getTitle());
		}
		
		;
	}
	
	public void sendConfirmationEmail(DelegateExecution execution ,String email, String title)throws MailException, InterruptedException{
		System.out.println("mejl za "+email);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom("petarperic23252@gmail.com");
		mail.setSubject("Scientific central - confirm registration");
		mail.setText("Hello,\n Your paper of magazine "+title+" is send for reformatting!");
		
		javaMailSender.send(mail);
		System.out.println("Email poslat!");
		
		
	}
}
