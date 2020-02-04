package com.example.scientificCenter.schedule;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.scientificCenter.domain.Transaction;
import com.example.scientificCenter.domain.TransactionStatus;
import com.example.scientificCenter.service.TransactionService;
import com.fasterxml.jackson.databind.JsonNode;

@EnableScheduling
@Component
public class TransactionSchedule {
	
	@Autowired
	private TransactionService transactionService;
	
	@Scheduled(fixedRate = 60000)
	public void refreshOrderStatus() {
		List<Transaction> trans = transactionService.findAll();
		for (Transaction t : trans) {
					RestTemplate toKP = new RestTemplate();
					HttpHeaders headersToKP = new HttpHeaders();
					Map<String, Object> mapToKP = new HashMap<String, Object>();
					HttpEntity<Map<String,Object>> requesttoKP = new HttpEntity<>(mapToKP, headersToKP);
					
					
					ResponseEntity<JsonNode> response = toKP.exchange("https://localhost:8086/"+ "kpService/paymentinfo" + "/getOne/"+t.getOrderId(),HttpMethod.GET, requesttoKP, JsonNode.class);
					JsonNode map = response.getBody();
					//System.out.println("odgovor " + map);
			
					String compare = map.get("isPaid").asText();
					//System.out.println("compare " + compare);
					TransactionStatus set = transactionService.findStatus(compare);
					if (set!=null) {
						t.setStatus(set);
						transactionService.save(t);
					}
		}
	}
}
