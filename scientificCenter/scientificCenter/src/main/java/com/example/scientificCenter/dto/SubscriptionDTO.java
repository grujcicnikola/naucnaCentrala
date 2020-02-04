package com.example.scientificCenter.dto;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.example.scientificCenter.domain.Journal;
import com.example.scientificCenter.domain.Subscription;
import com.example.scientificCenter.domain.SubscriptionType;
import com.example.scientificCenter.domain.User;

public class SubscriptionDTO {

	private Long id;
	
	private SubscriptionType type;
	
	private int frequency;
	
	private double price;
	
	private boolean active;
	
	private String userEmail;
	
	private String  journalIssn;
	
	public SubscriptionDTO() {}

	public SubscriptionDTO(Subscription subscription) {
		this.id = subscription.getId();
		this.type = subscription.getType();
		this.frequency = subscription.getFrequency();
		this.price = subscription.getPrice();
		this.active = subscription.isActive();
		this.userEmail = subscription.getUser().getEmail();
		this.journalIssn = subscription.getJournal().getIssn();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SubscriptionType getType() {
		return type;
	}

	public void setType(SubscriptionType type) {
		this.type = type;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getJournalIssn() {
		return journalIssn;
	}

	public void setJournalIssn(String journalIssn) {
		this.journalIssn = journalIssn;
	}
	
}
