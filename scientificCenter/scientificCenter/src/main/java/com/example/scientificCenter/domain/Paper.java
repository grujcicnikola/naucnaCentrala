package com.example.scientificCenter.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NaturalId;




@Entity
public class Paper {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	
	@Column(nullable = false)
    private String title;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Author author;
	
	@Column
    private String _abstract;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private ScientificArea area;
	
    @Column
    private Boolean status;
    
    @Column(nullable = false)
    private String pdf;
    
    @Column(nullable = false)
    private String keywords;
    
    @ManyToOne(fetch = FetchType.EAGER)
	private Journal journal;
    
    @Column
	private String doi;

	public Paper() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public Paper(String title, Author author, String _abstract, ScientificArea area, Boolean status,
			String pdf, String keywords, Journal journal, String doi) {
		super();
		this.title = title;
		this.author = author;
		this._abstract = _abstract;
		this.area = area;
		this.status = status;
		this.pdf = pdf;
		this.keywords = keywords;
		this.journal = journal;
		this.doi = doi;
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

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	
    
}
