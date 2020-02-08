package com.example.scientificCenter.delegate;

import java.util.HashSet;
import java.util.Set;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.domain.Comment;
import com.example.scientificCenter.domain.Paper;
import com.example.scientificCenter.domain.ReviewData;
import com.example.scientificCenter.repository.CommentRepository;
import com.example.scientificCenter.repository.ReviewDataRepository;
import com.example.scientificCenter.service.JournalService;
import com.example.scientificCenter.service.MethodOfPaymentService;
import com.example.scientificCenter.service.PaperService;
import com.example.scientificCenter.service.ScientificAreaService;
import com.example.scientificCenter.service.UserService;


@Service
public class SavingUpdatedData implements JavaDelegate{

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
	
	@Autowired
	private ReviewDataRepository reviewRep;
	
	@Autowired
	private CommentRepository commentRep;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Saving review data");
		
		
		String title = execution.getVariable("title").toString();
		Paper paper = this.paperService.findByTitle(title);
		paper.setPdf(execution.getVariable("pdf").toString());
		System.out.println("pdf" +execution.getVariable("pdf").toString());
		System.out.println("pdf3" +execution.getVariable("pdf3").toString());
		ReviewData  review = new ReviewData();
		
		this.paperService.save(paper);
		
	}
}