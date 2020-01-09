package com.example.scientificCenter.controller;

import java.io.IOException;
import java.util.List;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.util.json.JSONException;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.scientificCenter.dto.FormFieldsDTO;

@RestController
@RequestMapping("registation")
@CrossOrigin(origins = "http://localhost:4200")
public class RegistrationController {
	@Autowired
	IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	private String registrationProcessKey="Registration";
	
	
	@RequestMapping(value = "/register", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRegistrationForm() throws JSONException, UnsupportedOperationException, IOException, org.apache.tomcat.util.json.ParseException {
		System.out.println("ZAPOCINJE PROCES REGISTRACIJE");
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(registrationProcessKey);
		System.out.println("ZAPOCET PROCES: " + registrationProcessKey);
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		System.out.println("ZAPOCET TASK: " + task.getName());
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}
		
		return new ResponseEntity<>(new FormFieldsDTO(task.getId(),  properties,pi.getId()),HttpStatus.OK);
	}
	

}
