package com.example.scientificCenter.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.scientificCenter.model.PaperDoc;
import com.example.scientificCenter.service.PaperDocService;



@RestController
public class PaperDocController {
	@Autowired
	private PaperDocService resultRetriever;

	
	 @PostMapping("/savePaper")
	    public ResponseEntity<String> savePaper(@RequestBody PaperDoc model) {

		 	System.out.println("dodavanje paper");
	        resultRetriever.add(model);

	        return new ResponseEntity<String>("Successfully uploaded!", HttpStatus.OK);

	    }
	 @GetMapping("/getPapers")
	    public Iterable<PaperDoc> getPaper() {

		 	System.out.println("get paper");
	        return resultRetriever.findAll();

	    }

}
