package com.example.scientificCenter.domain;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;

import org.hibernate.annotations.NaturalId;

import com.example.scientificCenter.dto.TransactionDTO;

@Entity
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long journalId;
	
	@Column(nullable = false)
	private String merchantIssn;
	
	@Column(nullable = false)
	private String buyerEmail;
	
	@Column(nullable = false)
	private Double amount;
	
	@Column(nullable = false)
	private String orderId;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TransactionStatus status;
	
	@Column(nullable = false)
	private String successURL;
	
	@Column(nullable = false)
	private String errorURL;
	
	@Column(nullable = false)
	private String failedURL;

	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJournalId() {
		return journalId;
	}

	public void setJournalId(Long journalId) {
		this.journalId = journalId;
	}

	
	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}


	public String getBuyerEmail() {
		return buyerEmail;
	}


	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}




	public String getMerchantIssn() {
		return merchantIssn;
	}



	public void setMerchantIssn(String merchantIssn) {
		this.merchantIssn = merchantIssn;
	}



	public Double getAmount() {
		return amount;
	}



	public void setAmount(Double amount) {
		this.amount = amount;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public String getSuccessURL() {
		return successURL;
	}



	public void setSuccessURL(String successURL) {
		this.successURL = successURL;
	}



	public String getErrorURL() {
		return errorURL;
	}



	public void setErrorURL(String errorURL) {
		this.errorURL = errorURL;
	}



	public String getFailedURL() {
		return failedURL;
	}



	public void setFailedURL(String failedURL) {
		this.failedURL = failedURL;
	}

	


}
