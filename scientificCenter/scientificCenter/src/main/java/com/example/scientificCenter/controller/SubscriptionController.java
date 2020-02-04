package com.example.scientificCenter.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.scientificCenter.domain.Journal;
import com.example.scientificCenter.domain.Subscription;
import com.example.scientificCenter.domain.User;
import com.example.scientificCenter.dto.SubscriptionDTO;
import com.example.scientificCenter.security.JwtProvider;
import com.example.scientificCenter.service.JournalService;
import com.example.scientificCenter.service.SubscriptionService;
import com.example.scientificCenter.service.UserService;

@RestController
@RequestMapping("subscription")
@CrossOrigin(origins = "https://localhost:4202")
public class SubscriptionController {

	@Autowired
	private SubscriptionService subService;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JournalService journalService;
	
	@GetMapping(path = "/proba")
	public String test() {
		
		return "CAOOOOO";
	}
	@PreAuthorize("hasRole('USER')")
	@GetMapping(path = "/proba2")
	public String test2() {
		
		return "CAOOOOO useru";
	}
	
	@PostMapping(path = "/createSubscription")
	public ResponseEntity createSubscription(@RequestBody SubscriptionDTO subDTO) {
		
		Optional<User> user = userService.getByEmail(subDTO.getUserEmail());
		
		if(!user.isPresent())
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		}
		
		Journal journal = journalService.findByIssn(subDTO.getJournalIssn());
		if(journal == null)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		}
		
		Subscription sub = new Subscription(subDTO.getType(), subDTO.getFrequency(), subDTO.getPrice(), subDTO.isActive(), user.get(), journal);
		Subscription ret = subService.saveSubscription(sub);
		
		if(ret == null)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(path = "/changeInfo/{id1}/{id2}")
	public ResponseEntity changeInfo(@PathVariable("id1")String issn, @PathVariable("id2") String email) {
		
		Optional<User> user = userService.getByEmail(email);
		
		if(!user.isPresent())
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		}
		
		Journal journal = journalService.findByIssn(issn);
		if(journal == null)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		}
		
		Subscription sub = subService.getActiveSubByJournalAndUser(journal.getId(), user.get().getId(),false);
		
		if(sub == null)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		sub.setActive(true);
		subService.saveSubscription(sub);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(
			value="unsubscribe/{id1}/{id2}",
			method = RequestMethod.GET)
	public ResponseEntity unsubscribeJournal(@PathVariable("id1")String issn,@PathVariable("id2")String email) {
		
		Journal journal = journalService.findByIssn(issn);
		if(journal == null)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Optional<User> user = userService.getByEmail(email);
		if(!user.isPresent())
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Subscription sub = subService.getActiveSubByJournalAndUser(journal.getId(), user.get().getId(), true);
		if(sub == null)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	
		sub.setActive(false);
		subService.saveSubscription(sub);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(
			value="/changeActivity/{id1}/{id2}",
			method = RequestMethod.GET)
	public ResponseEntity changeActivity(@PathVariable("id1") String issn, @PathVariable("id2")String email) {
		
		Journal j = journalService.findByIssn(issn);
		if(j == null)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Optional<User> user = userService.getByEmail(email);
		if(!user.isPresent()) {
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
				
		Subscription sub = subService.getActiveSubByJournalAndUser(j.getId(), user.get().getId(),true);
		if(sub == null)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		sub.setActive(false);
		subService.saveSubscription(sub);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

