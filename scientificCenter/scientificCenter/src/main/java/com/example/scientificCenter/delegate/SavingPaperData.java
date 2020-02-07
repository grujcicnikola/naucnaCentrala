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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.domain.Author;
import com.example.scientificCenter.domain.Editor;
import com.example.scientificCenter.domain.Journal;
import com.example.scientificCenter.domain.MethodOfPayment;
import com.example.scientificCenter.domain.Paper;
import com.example.scientificCenter.domain.ScientificArea;
import com.example.scientificCenter.dto.FormSubmissionDTO;
import com.example.scientificCenter.service.JournalService;
import com.example.scientificCenter.service.MethodOfPaymentService;
import com.example.scientificCenter.service.PaperService;
import com.example.scientificCenter.service.ScientificAreaService;
import com.example.scientificCenter.service.UserService;


@Service
public class SavingPaperData implements JavaDelegate{

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
		System.out.println("Start paper data, journal "+execution.getVariable("issn").toString());
		Paper paper = new Paper();
		paper.setTitle(execution.getVariable("title").toString());
		paper.set_abstract(execution.getVariable("abstract").toString());
		paper.setPdf(execution.getVariable("pdf").toString());
		paper.setKeywords(execution.getVariable("keywords").toString());
		
		@SuppressWarnings("unchecked")
		List<FormSubmissionDTO> fieldsDTO = (List<FormSubmissionDTO>)execution.getVariable("paper");
		Set<ScientificArea> areas= new HashSet<ScientificArea>();
		for(int i = 0; i < fieldsDTO.size(); i++) {
			if(fieldsDTO.get(i).getFieldId().equals("scientificAreas")){
				for(int j =0; j< fieldsDTO.get(i).getAreas().size(); j++) {
					//System.out.println(fieldsDTO.get(i).getAreas().get(j));
					areas.add(areaService.findById(Long.parseLong(fieldsDTO.get(i).getAreas().get(j))).get());
				}
			}
		}
		paper.setArea(areas.stream().findFirst().get());
		Optional<com.example.scientificCenter.domain.User> editor = this.userService.getByEmail(execution.getVariable("initiator").toString());
		paper.setAuthor((Author)editor.get());
		
		Journal journal = this.journalService.findByIssn(execution.getVariable("issn").toString());
		paper.setJournal(journal);
		this.paperService.save(paper);
		
	}
}
