package com.example.scientificCenter.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class CommentDTO {
	
	
	private String comment;


	private String decision;


	public CommentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CommentDTO(String comment, String decision) {
		super();
		this.comment = comment;
		this.decision = decision;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public String getDecision() {
		return decision;
	}


	public void setDecision(String decision) {
		this.decision = decision;
	}

	
	
}