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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
public class Recenzent extends User{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Journal> journal = new ArrayList<Journal>();
	
	
	public Recenzent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Recenzent(User user) {
		// TODO Auto-generated constructor stub
		super(user);
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
