package com.example.scientificCenter.controller;

import java.io.IOException;

import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.scientificCenter.model.PaperDoc;
import com.example.scientificCenter.serviceInterface.PaperDocDAO;



@RestController
@RequestMapping("paperdoc")
@CrossOrigin(origins = "https://localhost:4202")
public class PaperDocController {
	@Autowired
	private PaperDocDAO resultRetriever;

	
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
