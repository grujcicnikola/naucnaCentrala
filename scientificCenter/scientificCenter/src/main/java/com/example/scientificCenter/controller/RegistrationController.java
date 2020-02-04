package com.example.scientificCenter.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidationException;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorException;
import org.camunda.bpm.engine.impl.util.json.JSONException;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.example.scientificCenter.domain.Editor;
import com.example.scientificCenter.domain.User;
import com.example.scientificCenter.dto.FormFieldsDTO;
import com.example.scientificCenter.dto.FormSubmissionDTO;
import com.example.scientificCenter.dto.TaskDTO;
import com.example.scientificCenter.service.UserService;

@RestController
@RequestMapping("registation")
@CrossOrigin(origins = "https://localhost:4202")
public class RegistrationController {
	@Autowired
	IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	@Value("${camunda.registrationProcessKey}")
	private String registrationProcessKey;
	
	
	@RequestMapping(value = "/register", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRegistrationForm()  {
		System.out.println("ZAPOCINJE PROCES REGISTRACIJE");
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(registrationProcessKey);
		System.out.println("ZAPOCET PROCES: " + registrationProcessKey);
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		System.out.println("ZAPOCET TASK: " + task.getName());
		//System.out.println("set assigne" +email);
		//task.setAssignee(email);
		//this.taskService.saveTask(task);
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}
		//runtimeService.setVariable(processInstanceId, "registracija", FSDto);
		return new ResponseEntity<>(new FormFieldsDTO(task.getId(),  properties,pi.getId()), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/userInput/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity finishInputData(@RequestBody List<FormSubmissionDTO> fieldsDTO, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(fieldsDTO);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		System.out.println("++++++++++++++++"+processInstanceId);
		for(int i = 0; i < fieldsDTO.size(); i++) {
			if(fieldsDTO.get(i).getFieldId().equals("name") ||fieldsDTO.get(i).getFieldId().equals("surname") ||
					fieldsDTO.get(i).getFieldId().equals("city")||fieldsDTO.get(i).getFieldId().equals("state") ||
					fieldsDTO.get(i).getFieldId().equals("email")||fieldsDTO.get(i).getFieldId().equals("username") ||
					fieldsDTO.get(i).getFieldId().equals("password")) {
				if(fieldsDTO.get(i).getFieldValue() == null ||fieldsDTO.get(i).getFieldValue().isEmpty()) {
					return new ResponseEntity(HttpStatus.BAD_REQUEST);
				}
			}
			if(fieldsDTO.get(i).getFieldId().equals("scientificAreas")){
				if(fieldsDTO.get(i).getAreas().size()==0) {
					return new ResponseEntity(HttpStatus.BAD_REQUEST);
				}
			}
			if(fieldsDTO.get(i).getFieldId().equals("username")){
				org.camunda.bpm.engine.identity.User userWithSameUsername = identityService.createUserQuery().userId(fieldsDTO.get(i).getFieldValue()) .singleResult();
				User userUsername= this.userService.findByUsername(fieldsDTO.get(i).getFieldValue());
				if(userUsername!=null || userWithSameUsername !=null) {
					return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
				}
				
			}
			if(fieldsDTO.get(i).getFieldId().equals("email")){
				Optional<User> userWithSameEmail = this.userService.getByEmail(fieldsDTO.get(i).getFieldValue());
				if(userWithSameEmail.isPresent()) {
					return new ResponseEntity(HttpStatus.CONFLICT);
				}
				
			}
		}
		
		try {
			runtimeService.setVariable(processInstanceId, "registration", fieldsDTO);
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
	
	@RequestMapping(value = "/confirmForm/{processInstanceId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getConfirmForm( @PathVariable String processInstanceId)  {
		
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
		System.out.println("ZAPOCET TASK: " + task.getName());
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}
		
		return new ResponseEntity<>(new FormFieldsDTO(task.getId(),  properties,processInstanceId),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/confirmUserInput/{username}/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity activateUser(@PathVariable String username,@RequestBody List<FormSubmissionDTO> fieldsDTO, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(fieldsDTO);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		
		try {
			runtimeService.setVariable(processInstanceId, "confirmation", fieldsDTO);
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
	
	private HashMap<String, Object> mapListToDto(List<FormSubmissionDTO> list)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(FormSubmissionDTO temp : list){
			map.put(temp.getFieldId(), temp.getFieldValue());
		}
		
		return map;
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> test()  {
		Editor novi = new Editor();
		novi.setActivated(false);
		novi.setCity("novi sad");
		novi.setCountry("srb");
		novi.setEmail("bla");
		novi.setIsRecenzent(false);
		novi.setName("name");
		novi.setUsername("username");
		novi.setPassword("sifra");
		novi.setSurname("surname");
		
		this.userService.save(novi);
		return new ResponseEntity<>(null,HttpStatus.OK);
	}
}
