package com.example.scientificCenter.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.NaturalId;

@Entity
public class ReviewData {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    
    @Column
    private Long paperId;
	
    @ManyToMany(fetch = FetchType.EAGER)
	private Set<Comment> commentsFromEditor = new HashSet<Comment>();


    @ManyToMany(fetch = FetchType.EAGER)
	private Set<Comment> commentsFromReviewer = new HashSet<Comment>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPaperId() {
		return paperId;
	}

	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}

	

	public Set<Comment> getCommentsFromEditor() {
		return commentsFromEditor;
	}

	public void setCommentsFromEditor(Set<Comment> commentsFromEditor) {
		this.commentsFromEditor = commentsFromEditor;
	}

	public Set<Comment> getCommentsFromReviewer() {
		return commentsFromReviewer;
	}

	public void setCommentsFromReviewer(Set<Comment> commentsFromReviewer) {
		this.commentsFromReviewer = commentsFromReviewer;
	}

	public ReviewData() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
}
