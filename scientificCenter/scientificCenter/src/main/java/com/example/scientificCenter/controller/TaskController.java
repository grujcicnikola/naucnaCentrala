package com.example.scientificCenter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidationException;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.scientificCenter.domain.Coauthor;
import com.example.scientificCenter.domain.Editor;
import com.example.scientificCenter.domain.Journal;
import com.example.scientificCenter.domain.Paper;
import com.example.scientificCenter.dto.FormFieldsDTO;
import com.example.scientificCenter.dto.FormSubmissionDTO;
import com.example.scientificCenter.dto.TaskDTO;
import com.example.scientificCenter.service.CoauthorService;
import com.example.scientificCenter.service.JournalService;
import com.example.scientificCenter.service.PaperService;
import com.example.scientificCenter.service.ScientificAreaService;
import com.example.scientificCenter.service.UserService;

@RestController
@RequestMapping("task")
@CrossOrigin(origins = "https://localhost:4202")
public class TaskController {
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
	private CoauthorService coauthorService;
	
	@Autowired
	private PaperService paperService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	
	private final static String RECENZENT_GROUP_ID = "recenzent";
	
	private final static String AUTHOR_GROUP_ID = "author";
	
	private final static String EDITOR_GROUP_ID = "editor";
	
	@GetMapping(path = "/getTaskForm/{taskId}", produces = "application/json")
    public @ResponseBody FormFieldsDTO get(@PathVariable String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		
        return new FormFieldsDTO(task.getId(), properties,processInstanceId);
		
        //return new ResponseEntity(dtos,  HttpStatus.OK);
    }
	
	@RequestMapping(value = "/userInput/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity finishInputData(@RequestBody List<FormSubmissionDTO> fieldsDTO, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(fieldsDTO);
		System.out.println("**********************");
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		System.out.println("naziv taska "+task.getName());
		if(task.getName().equals("Confirmation of recenzent status")) {
			runtimeService.setVariable(processInstanceId, "confirmation", fieldsDTO);
		}else if(task.getName().equals("Review data and activate journal")) {
			boolean authorized = authorize(this.EDITOR_GROUP_ID);
			if(!authorized) {
				return new ResponseEntity(HttpStatus.BAD_REQUEST);
			}
			boolean needDelete= false;
			String issn = "";
			for(int i = 0; i < fieldsDTO.size(); i++) {
				if(fieldsDTO.get(i).getFieldId().equals("goodData") ) {
					if(!fieldsDTO.get(i).getFieldValue().equals("true")) {
						needDelete = true;
					}
				}
				if(fieldsDTO.get(i).getFieldId().equals("issnNumber1") ) {
					issn =fieldsDTO.get(i).getFieldValue();
				}
				
			}
			if(needDelete) {
				System.out.println("brise se casopis sa issn "+issn);
				Journal journal =this.journalService.findByIssn(issn);
				Optional<Editor> editor =this.journalService.findEditorByJournal(journal);
				if(editor.isPresent()) {
					editor.get().setJournal(null);
					this.journalService.saveEditor(editor.get());
				}
				this.journalService.delete(journal);
			}
			runtimeService.setVariable(processInstanceId, "activationOfJournal", fieldsDTO);
			
		}else if(task.getName().equals("Input data for journal")) {
			
			for(int i = 0; i < fieldsDTO.size(); i++) {
				if(fieldsDTO.get(i).getFieldId().equals("name") ||fieldsDTO.get(i).getFieldId().equals("issnNumber") ||
						fieldsDTO.get(i).getFieldId().equals("wayOfPaying")) {
					if(fieldsDTO.get(i).getFieldValue() == null ||fieldsDTO.get(i).getFieldValue().isEmpty() ) {
						return new ResponseEntity(HttpStatus.BAD_REQUEST);
					}
				}
			}
			runtimeService.setVariable(processInstanceId, "journal", fieldsDTO);
				
		}else if(task.getName().equals("Add editors and recenzents")) {
			Boolean valid = true;
			for(int i = 0; i < fieldsDTO.size(); i++) {
				if(fieldsDTO.get(i).getFieldId().equals("editors")) {
					if(fieldsDTO.get(i).getFieldValue().isEmpty()) {
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
			runtimeService.setVariable(processInstanceId, "editorsAndRecenzents", fieldsDTO);
		}else if(task.getName().equals("Membership payment")) {
			runtimeService.setVariable(processInstanceId, "confirmation", fieldsDTO);
		}else if(task.getName().equals("Input data about paper")) {
			
			runtimeService.setVariable(processInstanceId, "paper", fieldsDTO);
		}else if(task.getName().equals("Input coauthors")) {
			String title =runtimeService.getVariable(processInstanceId, "title").toString();
			System.out.println("dodajem koatore za paper "+title);
			Coauthor coauthor = new Coauthor();
			for(int i = 0; i < fieldsDTO.size(); i++) {
				if(fieldsDTO.get(i).getFieldId().equals("nameCoauthor")) {
					coauthor.setName(fieldsDTO.get(i).getFieldValue());
					fieldsDTO.get(i).setFieldValue("");
				}else if(fieldsDTO.get(i).getFieldId().equals("emailCoauthor")) {
					coauthor.setEmail(fieldsDTO.get(i).getFieldValue());
					fieldsDTO.get(i).setFieldValue("");
				}else if(fieldsDTO.get(i).getFieldId().equals("cityCoauthor")) {
					coauthor.setCity(fieldsDTO.get(i).getFieldValue());
					fieldsDTO.get(i).setFieldValue("");
				}else if(fieldsDTO.get(i).getFieldId().equals("stateCoauthor")) {
					coauthor.setState(fieldsDTO.get(i).getFieldValue());
					fieldsDTO.get(i).setFieldValue("");
				}
			}
			Paper paper = this.paperService.findByTitle(title);
			Set<Paper> papers = new HashSet<Paper>();
			papers.add(paper);
			coauthor.setPapers(papers);
			this.coauthorService.save(coauthor);
			map = this.mapListToDto(fieldsDTO);
		}else if(task.getName().equals("Selecting recenzents")) {
			runtimeService.setVariable(processInstanceId, "recenzentsSelected", fieldsDTO);
		}else if(task.getName().equals("Add new recenzents")) {
			runtimeService.setVariable(processInstanceId, "recenzentNewSelected", fieldsDTO);
		}else if(task.getName().equals("Correction of paper")) {
			runtimeService.setVariable(processInstanceId, "updatedData", fieldsDTO);
		}else if(task.getName().equals("Entering corrections")){
			runtimeService.setVariable(processInstanceId, "correctedData", fieldsDTO);
		}
		try {
			formService.submitTaskForm(taskId, map);
			List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
			for (Task t : tasks) {
				System.out.println(t.getId() + " " + t.getName() + " " + t.getAssignee());
			}
		}catch(FormFieldValidationException  e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		/*try {
			runtimeService.setVariable(processInstanceId, "registration", fieldsDTO);
			formService.submitTaskForm(taskId, map);
			List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
			for (Task t : tasks) {
				System.out.println(t.getId() + " " + t.getName() + " " + t.getAssignee());
			}
		}catch(FormFieldValidationException  e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}*/
		return new ResponseEntity(HttpStatus.OK);
	}
	
	private HashMap<String, Object> mapListToDto(List<FormSubmissionDTO> list)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(FormSubmissionDTO temp : list){
			map.put(temp.getFieldId(), temp.getFieldValue());
		}
		
		return map;
	}
	
	private boolean authorize(String requestedGroupId) {
		String username = "";
		try {
		   username = identityService.getCurrentAuthentication().getUserId();
		} catch (Exception e) {
			return false;
		}
		
		Group group = identityService.createGroupQuery().groupMember(username).groupId(requestedGroupId).singleResult();
		
		if(group != null) {
			return true;
		} else {
			return false;
		}
	}

}
