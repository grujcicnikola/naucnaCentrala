package com.example.scientificCenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.domain.Membership;
import com.example.scientificCenter.domain.Subscription;
import com.example.scientificCenter.repository.MembershipRepository;
import com.example.scientificCenter.repository.SubscriptionRepository;


@Service
public class MembershipService {

	@Autowired
	private MembershipRepository repository;
	
	public Membership saveMembership(Membership sub) {
		
		return repository.save(sub);
	}
	
	public Membership findByJournalIdAndAuthorId(Long journalId,Long authorId) {
		
		return repository.findByJournalIdAndAuthorId(journalId, authorId);
	}
	
	
}
