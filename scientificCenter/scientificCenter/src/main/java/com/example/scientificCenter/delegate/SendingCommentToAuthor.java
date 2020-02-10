package com.example.scientificCenter.delegate;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.scientificCenter.domain.Comment;
import com.example.scientificCenter.domain.Journal;
import com.example.scientificCenter.domain.Paper;
import com.example.scientificCenter.dto.CommentDTO;
import com.example.scientificCenter.repository.CommentRepository;
import com.example.scientificCenter.service.JournalService;
import com.example.scientificCenter.service.PaperService;
import com.example.scientificCenter.service.ScientificAreaService;
import com.example.scientificCenter.service.UserService;


@Service
public class SendingCommentToAuthor implements JavaDelegate{

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
	
	@Autowired
	private PaperService paperService;
	
	@Autowired
	private CommentRepository commRep;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Journal journal = this.journalService.findByIssn(execution.getVariable("issn").toString());
		String title = execution.getVariable("title").toString();
		Paper paper = this.paperService.findByTitle(title);
		String message="";
		if(paper!=null) {
			List<Comment> comments = this.commRep.findAllByPaperId(paper.getId());
			List<CommentDTO> commentsDTO = new ArrayList<CommentDTO>();
			
			for(int i =0; i<comments.size();i++) {
				if(comments.get(i).isRecenzentCommentToAuthor()) {
					message +="\nMessage: "+comments.get(i).getComment();
				}
			}
			
		}
		//sendConfirmationEmail(execution, execution.getVariable("initiator").toString(),journal.getTitle(), message);
		
	}

	
	
	public void sendConfirmationEmail(DelegateExecution execution ,String email, String title, String message)throws MailException, InterruptedException{
		System.out.println("mejl za "+email);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom("petarperic23252@gmail.com");
		mail.setSubject("Scientific central - confirm registration");
		mail.setText("Hello,\n Your paper of magazine "+title+" is send for reformatting!\n"
				+"Here are messages from recenzents: "+message);
		
		
		javaMailSender.send(mail);
		System.out.println("Email poslat!");
		
		
	}
}
