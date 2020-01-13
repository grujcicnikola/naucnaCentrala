package com.example.scientificCenter.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.*;



@Entity
@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
public class Editor extends User{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Journal> journal = new ArrayList<Journal>();

	public Editor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Journal> getJournal() {
		return journal;
	}

	public void setJournal(List<Journal> journal) {
		this.journal = journal;
	}

	

	
	
	
	
	
	

}
