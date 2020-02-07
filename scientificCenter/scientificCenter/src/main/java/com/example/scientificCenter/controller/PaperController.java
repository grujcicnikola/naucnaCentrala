package com.example.scientificCenter.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.scientificCenter.domain.Journal;
import com.example.scientificCenter.dto.FormSubmissionDTO;
import com.example.scientificCenter.dto.JournalDTO;
import com.example.scientificCenter.dto.TaskDTO;
import com.example.scientificCenter.service.JournalService;
import com.example.scientificCenter.service.ScientificAreaService;
import com.example.scientificCenter.service.UserService;


@RestController
@RequestMapping("paper")
@CrossOrigin(origins = "https://localhost:4202")
public class PaperController {
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
	
	@Value("${camunda.submittingPaperProcessKey}")
	private String submittingPaperProcessKey;
	
	@RequestMapping(value = "/create/{email}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRegistrationForm(@PathVariable String email,@RequestBody JournalDTO journalDTO)  {
		System.out.println("ZAPOCINJE PROCES Submitting paper");
		Map<String, Object> variables = new HashMap<String, Object>();
		String openAccess = journalDTO.getIsOpenAccess().toString();
		variables.put("openAccess",openAccess);
		variables.put("initiator", email);
		variables.put("issn", journalDTO.getIssn());
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(submittingPaperProcessKey,variables);
		
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		this.taskService.saveTask(task);
		System.out.println("ZAPOCET TASK: " + task.getName()+ task.getAssignee() );
		
		return new ResponseEntity<>(new TaskDTO(task.getId(), task.getName(),task.getAssignee()), HttpStatus.OK);
	}
	
	/* @RequestMapping(value = "/getImage/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<ImageDTO>> getFile(@PathVariable @Min(1) Long id) {
		System.out.print("pogodio image get");
		List<Image> slike = new ArrayList<>();
		List<ImageDTO> listaSvihDTO = new ArrayList<ImageDTO>();
		slike = imageRepository.findAllByAccommodationId(id);
		for (int i = 0; i < slike.size(); i++) {
			System.out.print("slika "+i);
			listaSvihDTO.add(new ImageDTO(slike.get(i)));
		}
		return new ResponseEntity<>(listaSvihDTO, HttpStatus.OK);

	}*/



	@RequestMapping(value = "/uploadPDF/", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> uploadFile(@RequestParam("File") MultipartFile request) {
		System.out.print("pogodio pdf");
		String returnValue ="";
		try {
			returnValue = saveImage(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(returnValue, HttpStatus.OK);

	}

	

	public String saveImage(MultipartFile file) throws IOException {
		String folder = "files/";
		byte[] bytes = file.getBytes();
		Path path = Paths.get(folder + file.getOriginalFilename());
		System.out.println(path.toAbsolutePath());
		Files.write(path, bytes);
		return path.toAbsolutePath().toString();
	}
	
}