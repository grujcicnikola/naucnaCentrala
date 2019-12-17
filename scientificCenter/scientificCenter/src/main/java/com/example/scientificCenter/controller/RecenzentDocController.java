package com.example.scientificCenter.controller;

import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.scientificCenter.model.PaperDoc;
import com.example.scientificCenter.model.RecenzentDoc;
import com.example.scientificCenter.serviceInterface.PaperDocDAO;
import com.example.scientificCenter.serviceInterface.RecenzentDocDAO;


@RestController
public class RecenzentDocController {
	@Autowired
	private RecenzentDocDAO resultRetriever;

	
	 @PostMapping("/saveRecenzent")
	    public ResponseEntity<String> savePaper(@RequestBody RecenzentDoc model) {

		 	System.out.println("dodavanje Recenzent");
	        resultRetriever.add(model);
	        return new ResponseEntity<String>("Successfully uploaded!", HttpStatus.OK);

	    }
	 @GetMapping("/getRecenzents")
	    public Iterable<RecenzentDoc> getPaper() {

		 	System.out.println("get Recenzent");
	        return resultRetriever.findAll();

	    }

	 
}
