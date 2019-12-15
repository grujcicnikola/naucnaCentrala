package com.example.scientificCenter.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;




@Entity
public class Journal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String issn;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<ScientificArea> areas = new HashSet<ScientificArea>();
	
	@Column(nullable = false)
	private Boolean isOpenAccess;
	
	@OneToOne
	private Editor editorInChief;
	
	@OneToMany(fetch = FetchType.EAGER)
	private Set<Editor> editors = new HashSet<Editor>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Recenzent> recenzents = new HashSet<Recenzent>();

	@OneToMany(fetch = FetchType.EAGER)
	private Set<Paper> papers = new HashSet<Paper>();
	
	public Journal() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public Set<ScientificArea> getAreas() {
		return areas;
	}

	public void setAreas(Set<ScientificArea> areas) {
		this.areas = areas;
	}

	public Boolean getIsOpenAccess() {
		return isOpenAccess;
	}

	public void setIsOpenAccess(Boolean isOpenAccess) {
		this.isOpenAccess = isOpenAccess;
	}

	public Editor getEditorInChief() {
		return editorInChief;
	}

	public void setEditorInChief(Editor editorInChief) {
		this.editorInChief = editorInChief;
	}

	public Set<Editor> getEditors() {
		return editors;
	}

	public void setEditors(Set<Editor> editors) {
		this.editors = editors;
	}

	public Set<Recenzent> getRecenzents() {
		return recenzents;
	}

	public void setRecenzents(Set<Recenzent> recenzents) {
		this.recenzents = recenzents;
	}

	public Set<Paper> getPapers() {
		return papers;
	}

	public void setPapers(Set<Paper> papers) {
		this.papers = papers;
	}
	
	
	

}
