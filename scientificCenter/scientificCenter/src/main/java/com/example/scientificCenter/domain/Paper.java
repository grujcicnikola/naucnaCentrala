package com.example.scientificCenter.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;




@Entity
public class Paper {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(nullable = false)
    private String title;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Author author;
	
	@Column
    private String coauthors;
	
	@Column
    private String _abstract;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private ScientificArea area;
	
    @Column(nullable = false)
    private Boolean status;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Journal journal;

	public Paper() {
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

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getCoauthors() {
		return coauthors;
	}

	public void setCoauthors(String coauthors) {
		this.coauthors = coauthors;
	}

	public String get_abstract() {
		return _abstract;
	}

	public void set_abstract(String _abstract) {
		this._abstract = _abstract;
	}

	public ScientificArea getArea() {
		return area;
	}

	public void setArea(ScientificArea area) {
		this.area = area;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Journal getJournal() {
		return journal;
	}

	public void setJournal(Journal journal) {
		this.journal = journal;
	}

	
    
}
