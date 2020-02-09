package com.example.scientificCenter.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String comment;

	
	@Column
	private boolean recenzentCommentToAuthor;
	
	@Column
	private boolean recenzentCommentToEditor;
	
	@Column
	private String decision;
	
	@Column
	private Long paperId;
	
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Comment(String com) {
		// TODO Auto-generated constructor stub
		this.comment=com;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isRecenzentCommentToAuthor() {
		return recenzentCommentToAuthor;
	}

	public void setRecenzentCommentToAuthor(boolean recenzentCommentToAuthor) {
		this.recenzentCommentToAuthor = recenzentCommentToAuthor;
	}

	public boolean isRecenzentCommentToEditor() {
		return recenzentCommentToEditor;
	}

	public void setRecenzentCommentToEditor(boolean recenzentCommentToEditor) {
		this.recenzentCommentToEditor = recenzentCommentToEditor;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public Long getPaperId() {
		return paperId;
	}

	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}
	
	

}
