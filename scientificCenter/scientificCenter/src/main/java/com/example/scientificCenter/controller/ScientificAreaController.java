package com.example.scientificCenter.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.camunda.bpm.engine.RepositoryService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.scientificCenter.domain.ScientificArea;
import com.example.scientificCenter.dto.FormFieldsDTO;
import com.example.scientificCenter.dto.ScientificAreaDTO;
import com.example.scientificCenter.service.ScientificAreaService;

@RestController
@RequestMapping("scientificArea")
@CrossOrigin(origins = "https://localhost:4202")
public class ScientificAreaController {
	
	@Autowired
	private ScientificAreaService service;
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll(){
		List<ScientificArea> areas = service.findAll();
		List<ScientificAreaDTO> areasDTO = new ArrayList<ScientificAreaDTO>();
		for(int i = 0; i< areas.size(); i++) {
			areasDTO.add(new ScientificAreaDTO(areas.get(i)));
		}
		
		return new ResponseEntity<>(areasDTO,HttpStatus.OK);
	}

}
