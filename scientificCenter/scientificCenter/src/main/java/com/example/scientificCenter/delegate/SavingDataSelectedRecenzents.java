package com.example.scientificCenter.delegate;

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
import com.example.scientificCenter.service.JournalService;
import com.example.scientificCenter.service.MethodOfPaymentService;
import com.example.scientificCenter.service.ScientificAreaService;
import com.example.scientificCenter.service.UserService;


@Service
public class SavingDataSelectedRecenzents implements JavaDelegate{

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
		System.out.println("Start save data, SavingDataSelectedRecenzents ");
		
		@SuppressWarnings("unchecked")
		List<FormSubmissionDTO> fieldsDTO = (List<FormSubmissionDTO>)execution.getVariable("recenzentsSelected");
		List<String> field=new ArrayList<String>();
		for(int i = 0; i < fieldsDTO.size(); i++) {
			if(fieldsDTO.get(i).getFieldId().equals("recenzent")){
				for(int j =0; j< fieldsDTO.get(i).getAreas().size(); j++) {
					//System.out.println(fieldsDTO.get(i).getAreas().get(j));
					field.add(fieldsDTO.get(i).getAreas().get(j));
					System.out.println("dodat recenzent u listu "+fieldsDTO.get(i).getAreas().get(j));
				}
			}
		}
		
		execution.setVariable("recenzentList", field);
	}
}