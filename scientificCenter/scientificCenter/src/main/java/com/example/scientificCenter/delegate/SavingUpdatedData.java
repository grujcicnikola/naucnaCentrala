package com.example.scientificCenter.delegate;

import java.util.HashSet;
import java.util.List;
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
import com.example.scientificCenter.domain.ScientificArea;
import com.example.scientificCenter.dto.FormSubmissionDTO;
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
		
		@SuppressWarnings("unchecked")
		String pdf="";
		List<FormSubmissionDTO> fieldsDTO = (List<FormSubmissionDTO>)execution.getVariable("updatedData");
		Set<ScientificArea> areas= new HashSet<ScientificArea>();
		for(int i = 0; i < fieldsDTO.size(); i++) {
			if(fieldsDTO.get(i).getFieldId().equals("pdf")){
				System.out.println("updated pdf "+fieldsDTO.get(i).getFieldValue());
				pdf = fieldsDTO.get(i).getFieldValue();
			}
		}
		String title = execution.getVariable("title").toString();
		Paper paper = this.paperService.findByTitle(title);
		paper.setPdf(pdf);
		paper.setTitle(execution.getVariable("title3").toString());
		execution.setVariable("pdf",pdf);
		execution.setVariable("title", execution.getVariable("title3").toString());
		
		execution.setVariable("pdf1",pdf);
		execution.setVariable("title1", execution.getVariable("title3").toString());
		this.paperService.save(paper);
		
	}
}