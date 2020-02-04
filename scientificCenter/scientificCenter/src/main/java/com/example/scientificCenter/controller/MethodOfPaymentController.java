package com.example.scientificCenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.scientificCenter.domain.MethodOfPayment;
import com.example.scientificCenter.domain.ScientificArea;
import com.example.scientificCenter.dto.MethodOfPaymentDTO;
import com.example.scientificCenter.dto.ScientificAreaDTO;
import com.example.scientificCenter.service.MethodOfPaymentService;
import com.example.scientificCenter.service.ScientificAreaService;


@RestController
@RequestMapping("methodOfPayment")
@CrossOrigin(origins = "https://localhost:4202")
public class MethodOfPaymentController {
	
	@Autowired
	private MethodOfPaymentService service;
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll(){
		List<MethodOfPayment> areas = service.findAll();
		List<MethodOfPaymentDTO> areasDTO = new ArrayList<MethodOfPaymentDTO>();
		for(int i = 0; i< areas.size(); i++) {
			areasDTO.add(new MethodOfPaymentDTO(areas.get(i)));
		}
		
		return new ResponseEntity<>(areasDTO,HttpStatus.OK);
	}

}