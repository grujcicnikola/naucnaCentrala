package com.example.scientificCenter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidationException;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.scientificCenter.domain.Editor;
import com.example.scientificCenter.domain.Journal;
import com.example.scientificCenter.domain.Recenzent;
import com.example.scientificCenter.domain.ScientificArea;
import com.example.scientificCenter.domain.User;
import com.example.scientificCenter.dto.FormFieldsDTO;
import com.example.scientificCenter.dto.FormSubmissionDTO;
import com.example.scientificCenter.dto.JournalDTO;
import com.example.scientificCenter.dto.TaskDTO;
import com.example.scientificCenter.dto.UserEditorDTO;
import com.example.scientificCenter.security.JwtProvider;
import com.example.scientificCenter.service.JournalService;
import com.example.scientificCenter.service.ScientificAreaService;
import com.example.scientificCenter.service.UserService;

@RestController
@RequestMapping("journal")
@CrossOrigin(origins = "https://localhost:4202")
public class JournalController {
	@Autowired
	IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ScientificAreaService areaService;
	
	@Autowired
	private JournalService journalService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	@Value("${camunda.createJournalProcessKey}")
	private String createJournalProcessKey;
	
	@RequestMapping(value = "/create/{email}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRegistrationForm(@PathVariable String email)  {
		System.out.println("ZAPOCINJE PROCES Create journla");
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(createJournalProcessKey);
		System.out.println("ZAPOCET PROCES: " + pi.getId());
		
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		System.out.println("set assigne" +email);
		//task.set(email);
		runtimeService.setVariable(pi.getId(), "initiator", email);
		this.taskService.saveTask(task);
		System.out.println("ZAPOCET TASK: " + task.getName());
		/*TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		
		for(FormField fp : properties) {
			//System.out.println(fp.getId() + fp.getType());
		}*/
		//runtimeService.setVariable(processInstanceId, "registracija", FSDto);
		return new ResponseEntity<>(new TaskDTO(task.getId(), task.getName(),task.getAssignee()), HttpStatus.OK);//(task.getId(),  properties,pi.getId()),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/userInput/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity finishInputData(@RequestBody List<FormSubmissionDTO> fieldsDTO, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(fieldsDTO);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		for(int i = 0; i < fieldsDTO.size(); i++) {
			if(fieldsDTO.get(i).getFieldId().equals("name") ||fieldsDTO.get(i).getFieldId().equals("issnNumber") ||
					fieldsDTO.get(i).getFieldId().equals("wayOfPaying")) {
				if(fieldsDTO.get(i).getFieldValue() == null ||fieldsDTO.get(i).getFieldValue().isEmpty() ) {
					return new ResponseEntity(HttpStatus.BAD_REQUEST);
				}
				if(fieldsDTO.get(i).getFieldId().equals("issnNumber")) {
					Journal journalWithSameIssn =journalService.findByIssn(fieldsDTO.get(i).getFieldValue());
					if(journalWithSameIssn !=null) {
						return new ResponseEntity(HttpStatus.BAD_REQUEST);
					}
				}
			}
		}
		try {
			runtimeService.setVariable(processInstanceId, "journal", fieldsDTO);
			formService.submitTaskForm(taskId, map);
			List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
			for (Task t : tasks) {
				System.out.println(t.getId() + " " + t.getName() + " " + t.getAssignee());
			}
		}catch(FormFieldValidationException  e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(HttpStatus.OK);
	}
	
	//editorsAndRecenzent
	@RequestMapping(value = "/editorsAndRecenzent/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity editorsAndRecenzent(@RequestBody List<FormSubmissionDTO> fieldsDTO, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(fieldsDTO);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		Boolean valid = true;
		for(int i = 0; i < fieldsDTO.size(); i++) {
			if(fieldsDTO.get(i).getFieldId().equals("editors")) {
				if(fieldsDTO.get(i).getFieldValue() == null) {
					System.out.println("jedno od polja niju dobro");
					valid = false;
				}
			}
			if(fieldsDTO.get(i).getFieldId().equals("recenzents")) {
				if(fieldsDTO.get(i).getAreas().size()<2) {
					System.out.println("recenzent nije dobar");
					valid = false;
				}
			}
		}
		if(valid==false) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		try {
			runtimeService.setVariable(processInstanceId, "editorsAndRecenzents", fieldsDTO);
			formService.submitTaskForm(taskId, map);
			List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
			for (Task t : tasks) {
				System.out.println(t.getId() + " " + t.getName() + " " + t.getAssignee());
			}
		}catch(FormFieldValidationException  e) {
			System.out.println("druga greska");
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@GetMapping(path = "/getNextTaskForm/{processInstanceId}", produces = "application/json")
    public @ResponseBody FormFieldsDTO get(@PathVariable String processInstanceId) {
		
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		List<TaskDTO> dtos = new ArrayList<TaskDTO>();
		for (Task task : tasks) {
			TaskDTO t = new TaskDTO(task.getId(), task.getName(), task.getAssignee());
			dtos.add(t);
		}
		TaskFormData tfd = formService.getTaskFormData(tasks.get(0).getId());
		List<FormField> properties = tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}
		
        return new FormFieldsDTO(tasks.get(0).getId(), properties,processInstanceId);
		
        //return new ResponseEntity(dtos,  HttpStatus.OK);
    }
	
	@GetMapping(path = "/editors/{processInstanceId}", produces = "application/json")
    public ResponseEntity<?> getEditors(@PathVariable String processInstanceId) {
		
		@SuppressWarnings("unchecked")
		List<FormSubmissionDTO> fieldsDTO = (List<FormSubmissionDTO>)runtimeService.getVariable(processInstanceId, "journal");
		List<ScientificArea> areas = new ArrayList<ScientificArea>();
		if(fieldsDTO !=null) {
			for(int i = 0; i < fieldsDTO.size(); i++) { //ovo je null
				if(fieldsDTO.get(i).getFieldId().equals("scientificAreas")){
					for(int j =0; j<fieldsDTO.get(i).getAreas().size(); j++) {
						System.out.println("naucna oblast "+fieldsDTO.get(i).getAreas().get(j));
						areas.add(this.areaService.findById(Long.parseLong(fieldsDTO.get(i).getAreas().get(j))).get());
					}
				}
			}
			String email = (String) runtimeService.getVariable(processInstanceId, "initiator");
			List<Editor> editors = this.journalService.findAllEditors();
			Set<Editor> editorsOfInterest = new HashSet<Editor>();
			for(int i =0; i < editors.size(); i++) {
				for(int j =0; j < editors.get(i).getAreas().size(); j++) {
					for(int k =0; k< areas.size(); k++) {
						if(editors.get(i).getAreas().get(j).equals(areas.get(k))) {
							if(!editors.get(i).getEmail().equals(email)) {
								editorsOfInterest.add(editors.get(i));
							}
						}
					}
				}
			}
			
			List<UserEditorDTO> dtos = new ArrayList<UserEditorDTO>();
			for (Editor task : editorsOfInterest) {
				if(task.getJournal() ==null) {
					UserEditorDTO t = new UserEditorDTO(task.getId(), task.getName());
					dtos.add(t);
				}
			}
			
			return new ResponseEntity<>(dtos,HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.OK);
    }
	
	
	@GetMapping(path = "/recenzents/{processInstanceId}", produces = "application/json")
    public ResponseEntity<?> getRecenzents(@PathVariable String processInstanceId) {
		
		@SuppressWarnings("unchecked")
		List<FormSubmissionDTO> fieldsDTO = (List<FormSubmissionDTO>)runtimeService.getVariable(processInstanceId, "journal");
		List<ScientificArea> areas = new ArrayList<ScientificArea>();
		if(fieldsDTO !=null) {
			for(int i = 0; i < fieldsDTO.size(); i++) {//ovo je nulll
				if(fieldsDTO.get(i).getFieldId().equals("scientificAreas")){
					for(int j =0; j<fieldsDTO.get(i).getAreas().size(); j++) {
						System.out.println("naucna oblast "+fieldsDTO.get(i).getAreas().get(j));
						areas.add(this.areaService.findById(Long.parseLong(fieldsDTO.get(i).getAreas().get(j))).get());
					}
				}
			}
			
			List<Recenzent> recenzents = this.journalService.findAllRecenzents();
			Set<Recenzent> recenzentsOfInterest = new HashSet<Recenzent>();
			for(int i =0; i < recenzents.size(); i++) {
				for(int j =0; j <recenzents.get(i).getAreas().size(); j++) {
					for(int k =0; k< areas.size(); k++) {
						if(recenzents.get(i).getAreas().get(j).equals(areas.get(k))) {
							System.out.println("---dodaati recenzent u listi "+recenzents.get(i).getEmail());
							recenzentsOfInterest.add(recenzents.get(i));
							
						}
					}
				}
			}
		
			List<UserEditorDTO> dtos = new ArrayList<UserEditorDTO>();
			for (Recenzent task : recenzentsOfInterest) {
				UserEditorDTO t = new UserEditorDTO(task.getId(), task.getName());
				dtos.add(t);
			}
			
			return new ResponseEntity<>(dtos,HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.OK);
		
    }
	
	
	
	@GetMapping(path = "/journals", produces = "application/json")
    public ResponseEntity<?> getAllActivatedJournals() {
		
		List<JournalDTO> journalDTO = new ArrayList<JournalDTO>();
		List<Journal> journal = this.journalService.findAll();
		for(int i =0; i< journal.size(); i++) {
			if(journal.get(i).getIsActivated()) {
				journalDTO.add(new JournalDTO(journal.get(i)));
			}
		}
		
		return new ResponseEntity<>(journalDTO,HttpStatus.OK);
		
    }
	
	
	
	private HashMap<String, Object> mapListToDto(List<FormSubmissionDTO> list)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(FormSubmissionDTO temp : list){
			map.put(temp.getFieldId(), temp.getFieldValue());
		}
		
		return map;
	}
	
}
