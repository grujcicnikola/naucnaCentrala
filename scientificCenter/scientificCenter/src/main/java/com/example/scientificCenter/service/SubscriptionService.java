package com.example.scientificCenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.domain.Subscription;
import com.example.scientificCenter.repository.SubscriptionRepository;

@Service
public class SubscriptionService {

	@Autowired
	private SubscriptionRepository subRepository;
	
	public Subscription saveSubscription(Subscription sub) {
		
		return subRepository.save(sub);
	}
	
	public Subscription getActiveSubByJournalAndUser(Long jId,Long uId, boolean active) {
		
		return subRepository.findByJournalIdAndUserIdAndActive(jId, uId, active);
	}
	
	public List<Subscription> getByJournalId(Long id){
		
		return subRepository.findAllByJournalId(id);
	}
}
