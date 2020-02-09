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
public class SavingDataAboutReview implements JavaDelegate{

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
		ReviewData review = this.reviewRep.findByPaperId(paper.getId());
		//Set<Comment> comments = new HashSet<Comment>();
		Comment comm1 = new Comment(execution.getVariable("commentToAuthor4").toString());
		comm1.setDecision(execution.getVariable("decision").toString());
		comm1.setRecenzentCommentToAuthor(true);
		comm1.setPaperId(paper.getId());
		Comment comm2 =new Comment(execution.getVariable("comentToEditors4").toString());
		//comm2.setDecision(execution.getVariable("decision").toString());
		comm2.setRecenzentCommentToEditor(true);
		comm2.setPaperId(paper.getId());
		
		Comment savedComm1 = this.commentRep.save(comm1);
		Comment savedComm2 = this.commentRep.save(comm2);
		//Set<Comment> commentsToAuthors =review.getCommentsFromReviewer();
		//commentsToAuthors.add(savedComm1);
		//review.setCommentsFromReviewer(commentsToAuthors);
		
		//Set<Comment> comentToEditors =review.getCommentsToEditor();
		//comentToEditors.add(savedComm2);
		//review.setCommentsFromReviewer(comentToEditors);

		//review.setPaperId(paper.getId());
		//this.reviewRep.save(review);
		
	}
}