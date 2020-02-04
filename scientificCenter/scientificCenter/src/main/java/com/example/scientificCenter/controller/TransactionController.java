package com.example.scientificCenter.controller;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.example.scientificCenter.domain.Transaction;
import com.example.scientificCenter.domain.TransactionStatus;
import com.example.scientificCenter.dto.JournalDTO;
import com.example.scientificCenter.dto.TaskDTO;
import com.example.scientificCenter.dto.TransactionDTO;
import com.example.scientificCenter.security.JwtProvider;
import com.example.scientificCenter.service.JournalService;
import com.example.scientificCenter.service.ScientificAreaService;
import com.example.scientificCenter.service.TransactionService;
import com.example.scientificCenter.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;


@RestController
@RequestMapping("transaction")
@CrossOrigin(origins = "https://localhost:4202")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private JournalService journalService;
	
	@Autowired
    JwtProvider jwtProvider;
	
	private String address ="https://localhost:8086/kpService/paymentinfo";
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createTransaction(@RequestBody TransactionDTO tDTO)  {
		
		Transaction transaction= new Transaction();
		transaction.setAmount(tDTO.getAmount());
		transaction.setBuyerEmail(tDTO.getBuyerEmail());
		transaction.setJournalId(tDTO.getJournalId());
		transaction.setOrderId(UUID.randomUUID().toString());
		transaction.setErrorURL("https://localhost:4202/error");//+tDTO.getOrderId());
		transaction.setFailedURL("https://localhost:4202/failed");//+tDTO.getOrderId());
		transaction.setSuccessURL("https://localhost:4202/success");///"+tDTO.getOrderId());
		transaction.setStatus(TransactionStatus.CREATED);
		transaction.setMerchantIssn(tDTO.getMerchantIssn());
		
		
		RestTemplate temp = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        TransactionDTO dto = new TransactionDTO(transaction);
        HttpEntity<TransactionDTO> entity = new HttpEntity<>(dto, headers);
        
        try {
        	temp.postForObject(address+"/createFromNC", dto, Transaction.class);
            System.out.println("Successfull created payment!");
            this.transactionService.save(transaction);
            
        } catch (HttpStatusCodeException exception) {
            System.out.println("Error while creating payment - HttpStatusCodeException!");
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
		
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TransactionDTO>> getAllForBuyer(@PathVariable("email") String email)  {
		String userEmail = email;
		//String userEmail = jwtProvider.getUsernameLoggedUser();
		System.out.println("userEmail " + userEmail);
		
		List<Transaction> transactions = transactionService.findAllByBuyerEmailAndStatus(userEmail, TransactionStatus.CREATED);
		
		for (Transaction t : transactions) {
			System.out.println("t order id " + t.getOrderId());
				
			RestTemplate toKP = new RestTemplate();
			HttpHeaders headersToKP = new HttpHeaders();
			Map<String, Object> mapToKP = new HashMap<String, Object>();
			HttpEntity<Map<String,Object>> requesttoKP = new HttpEntity<>(mapToKP, headersToKP);
					
			ResponseEntity<JsonNode> response = toKP.exchange("https://localhost:8086/"+ "kpService/paymentinfo" + "/getOne/"+t.getOrderId(),HttpMethod.GET, requesttoKP, JsonNode.class);
			JsonNode map = response.getBody();
			System.out.println("odgovor " + map);
					
			String compare = map.get("isPaid").asText();
			System.out.println("compare " + compare);
			TransactionStatus set = transactionService.findStatus(compare);
			if (set!=null) {
				t.setStatus(set);
				transactionService.save(t);
			}
		}
				
		List<Transaction> transactionsBack = transactionService.findAllByBuyerEmail(userEmail);
		List<TransactionDTO> back = new ArrayList<>();
		for (Transaction t : transactionsBack) {
			TransactionDTO tdto = new TransactionDTO(t);
			JournalDTO jdto = new JournalDTO(journalService.findOneById(t.getJournalId()).get());
			tdto.setJournal(jdto);
			back.add(tdto);
		}
		
		return new ResponseEntity<>(back,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/updateStatus/{id}/{status}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateTransaction(@PathVariable("id") String id, @PathVariable("status") String status)  {
		
		Transaction transaction = transactionService.findOneByOrderId(id);
		TransactionStatus set = transactionService.findStatus(status);
		if (set!=null) {
			transaction.setStatus(set);
			transactionService.save(transaction);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
}