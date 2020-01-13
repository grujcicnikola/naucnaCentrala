package com.example.scientificCenter.service;

import java.util.ArrayList;
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

import com.example.scientificCenter.domain.Editor;
import com.example.scientificCenter.domain.Journal;
import com.example.scientificCenter.domain.MethodOfPayment;
import com.example.scientificCenter.domain.Recenzent;
import com.example.scientificCenter.domain.ScientificArea;
import com.example.scientificCenter.dto.FormSubmissionDTO;


@Service
public class SavingRecenzentsAndEditorsService implements JavaDelegate{

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
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Start save recenzents, journal "+execution.getVariable("name").toString());
		Journal journal = this.journalService.findByIssn(execution.getVariable("issnNumber").toString());
		@SuppressWarnings("unchecked")
		List<FormSubmissionDTO> fieldsDTO = (List<FormSubmissionDTO>)execution.getVariable("editorsAndRecenzents");
		Editor editor= new Editor();
		List<Recenzent> recenzents= new ArrayList<Recenzent>();
		for(int i = 0; i < fieldsDTO.size(); i++) {
			if(fieldsDTO.get(i).getFieldId().equals("editors") ) {
				editor = this.journalService.findEditorById(Long.parseLong(fieldsDTO.get(i).getFieldValue())).get();
				
			}
			if(fieldsDTO.get(i).getFieldId().equals("recenzents")) {
				for(int j=0; j<fieldsDTO.get(i).getAreas().size();j++) {
					recenzents.add(this.journalService.findRecenzentById(Long.parseLong(fieldsDTO.get(i).getAreas().get(j))).get());
				}
			}
		}
		
		editor.setJournal(journal);
		this.journalService.saveEditor(editor);
		
		for(int i = 0; i < recenzents.size(); i++) {
			//System.out.println(recenzents.get(i).getUsername());
			List<Journal> journals= recenzents.get(i).getJournal();
			journals.add(journal);
			recenzents.get(i).setJournal(journals);
			this.journalService.saveRecenzent(recenzents.get(i));
		}

	}
}
