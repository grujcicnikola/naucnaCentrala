package com.example.scientificCenter.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.scientificCenter.domain.ScientificArea;
import com.example.scientificCenter.domain.User;
import com.example.scientificCenter.domain.UserRole;
import com.example.scientificCenter.domain.UserRoleName;
import com.example.scientificCenter.dto.ScientificAreaDTO;
import com.example.scientificCenter.dto.TaskDTO;
import com.example.scientificCenter.dto.UserDTO;
import com.example.scientificCenter.service.ScientificAreaService;
import com.example.scientificCenter.service.UserRoleService;
import com.example.scientificCenter.service.UserService;


@RestController
@RequestMapping("user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	
	@Autowired
	private UserService service;
	
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
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Value("${camunda.registrationProcessKey}")
	private String registrationProcessKey;
	
	@Value("${camunda.createJournalProcessKey}")
	private String createJournalProcessKey;
	
	@RequestMapping(value = "/email/{id}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> getUserByEmail(@PathVariable("id") String email) {

		System.out.println("Pogodio get po mailu");
		System.out.println("Stigao mejl: " + email);
		Optional<User> user = service.getByEmail(email);
		if (!user.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(new UserDTO(user.get()), HttpStatus.OK);
		}
	}
	
	
	
	@GetMapping(path = "/get/tasks/{email}/", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDTO>> getTaskForCurrentUser(@PathVariable String email) {
		System.out.println("set assigne" +email);
		Optional<User> user = this.userService.getByEmail(email);
		if(user.get()!= null) {
			List<Task> tasks = new ArrayList<Task>();
			//Set<UserRole> roles =user.get().getRoles();
			//UserRole roleOfAdmin = this.userRoleService.findRoleByName(UserRoleName.ROLE_ADMIN);
			tasks.addAll(taskService.createTaskQuery().processDefinitionKey(this.createJournalProcessKey).taskAssignee(email).list());
			tasks.addAll(taskService.createTaskQuery().processDefinitionKey(this.registrationProcessKey).taskAssignee(email).list());
			
			List<TaskDTO> dtos = new ArrayList<TaskDTO>();
			for (Task task : tasks) {
				TaskDTO t = new TaskDTO(task.getId(), task.getName(), task.getAssignee());
				dtos.add(t);
			}
			return new ResponseEntity(dtos,  HttpStatus.OK);
		}
		
        return new ResponseEntity(null,  HttpStatus.OK);
    }
}
