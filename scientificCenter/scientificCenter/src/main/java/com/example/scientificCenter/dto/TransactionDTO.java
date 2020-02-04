package com.example.scientificCenter.dto;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.example.scientificCenter.domain.Transaction;
import com.example.scientificCenter.domain.TransactionStatus;

public class TransactionDTO {

	private String buyerEmail;
	
	private Double amount;
	
	private String orderId;
	
	private Long journalId;
	
	private TransactionStatus status;
	
	private String successURL;
	
	private String errorURL;
	
	private String failedURL;
	
	private String merchantIssn;
	
	private JournalDTO journal;
	
	public TransactionDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	


	public TransactionDTO(String buyerEmail, Double amount, String orderId, Long journalId, TransactionStatus status,
			String successURL, String errorURL, String failedURL, String merchantIssn) {
		super();
		this.buyerEmail = buyerEmail;
		this.amount = amount;
		this.orderId = orderId;
		this.journalId = journalId;
		this.status = status;
		this.successURL = successURL;
		this.errorURL = errorURL;
		this.failedURL = failedURL;
		this.merchantIssn=merchantIssn;
	}




	public TransactionDTO(Transaction transaction) {
		// TODO Auto-generated constructor stub
		this.buyerEmail = transaction.getBuyerEmail();
		this.amount = transaction.getAmount();
		this.orderId = transaction.getOrderId();
		this.journalId = transaction.getJournalId();
		this.status = transaction.getStatus();
		this.successURL = transaction.getSuccessURL();
		this.errorURL = transaction.getErrorURL();
		this.failedURL = transaction.getFailedURL();
		this.merchantIssn=transaction.getMerchantIssn();
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




	public String getMerchantIssn() {
		return merchantIssn;
	}




	public void setMerchantIssn(String merchantIssn) {
		this.merchantIssn = merchantIssn;
	}




	public String getFailedURL() {
		return failedURL;
	}




	public void setFailedURL(String failedURL) {
		this.failedURL = failedURL;
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




	public Long getJournalId() {
		return journalId;
	}




	public void setJournalId(Long journalId) {
		this.journalId = journalId;
	}




	public JournalDTO getJournal() {
		return journal;
	}




	public void setJournal(JournalDTO journal) {
		this.journal = journal;
	}

	
	
	

}
