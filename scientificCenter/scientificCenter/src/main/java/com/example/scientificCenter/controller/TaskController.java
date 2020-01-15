package com.example.scientificCenter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
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

import com.example.scientificCenter.domain.Journal;
import com.example.scientificCenter.dto.FormFieldsDTO;
import com.example.scientificCenter.dto.FormSubmissionDTO;
import com.example.scientificCenter.dto.TaskDTO;
import com.example.scientificCenter.service.JournalService;
import com.example.scientificCenter.service.ScientificAreaService;
import com.example.scientificCenter.service.UserService;

@RestController
@RequestMapping("task")
@CrossOrigin(origins = "http://localhost:4200")
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
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	@GetMapping(path = "/getTaskForm/{taskId}", produces = "application/json")
    public @ResponseBody FormFieldsDTO get(@PathVariable String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}
		System.out.println("*****************d*****");
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
			boolean needDelete= false;
			String issn = "";
			for(int i = 0; i < fieldsDTO.size(); i++) {
				if(fieldsDTO.get(i).getFieldId().equals("goodData") ) {
					if(fieldsDTO.get(i).getFieldValue().equals("")) {
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
			runtimeService.setVariable(processInstanceId, "editorsAndRecenzents", fieldsDTO);
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

}
