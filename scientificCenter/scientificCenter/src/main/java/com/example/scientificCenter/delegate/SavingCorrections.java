package com.example.scientificCenter.delegate;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.domain.Author;
import com.example.scientificCenter.domain.Journal;
import com.example.scientificCenter.domain.Paper;
import com.example.scientificCenter.domain.ScientificArea;
import com.example.scientificCenter.dto.FormSubmissionDTO;
import com.example.scientificCenter.service.JournalService;
import com.example.scientificCenter.service.MethodOfPaymentService;
import com.example.scientificCenter.service.PaperService;
import com.example.scientificCenter.service.ScientificAreaService;
import com.example.scientificCenter.service.UserService;


@Service
public class SavingCorrections implements JavaDelegate{

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
		@SuppressWarnings("unchecked")
		String pdf="";
		List<FormSubmissionDTO> fieldsDTO = (List<FormSubmissionDTO>)execution.getVariable("correctedData");
		Set<ScientificArea> areas= new HashSet<ScientificArea>();
		for(int i = 0; i < fieldsDTO.size(); i++) {
			if(fieldsDTO.get(i).getFieldId().equals("pdf")){
				System.out.println("updated pdf "+fieldsDTO.get(i).getFieldValue());
				pdf = fieldsDTO.get(i).getFieldValue();
			}
		}
		System.out.println("Start corrections "+execution.getVariable("title").toString());
		Paper paper = this.paperService.findByTitle(execution.getVariable("title").toString());
		paper.setTitle(execution.getVariable("title5").toString());
		paper.set_abstract(execution.getVariable("abstract5").toString());
		paper.setPdf(pdf);
		paper.setKeywords(execution.getVariable("keywords5").toString());
		
		execution.setVariable("title",paper.getTitle());
		execution.setVariable("abstract",paper.get_abstract());
		execution.setVariable("pdf",pdf);
		execution.setVariable("keywords",paper.getKeywords());
		
		execution.setVariable("title4",paper.getTitle());
		execution.setVariable("pdf4",pdf);
		execution.setVariable("keywords4",paper.getKeywords());
		execution.setVariable("commentToAuthor4","");
		execution.setVariable("comentToEditors4","");
		this.paperService.save(paper);
		
	}
}
