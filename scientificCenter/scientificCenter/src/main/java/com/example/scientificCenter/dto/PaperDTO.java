package com.example.scientificCenter.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.example.scientificCenter.domain.Paper;


public class PaperDTO {

    private Long id;

    private Long idPaper;

    private String title;

    private String journaltitle;
	
	private String author;
	
	private String coauthors;
	
    private String keywords;
	
	private String area;
	
    private Boolean status;
	
    private String content;

	public PaperDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public PaperDTO(Long id, Long idPaper, String title, String journaltitle, String author, String coauthors,
			String keywords, String area, Boolean status, String content) {
		super();
		this.id = id;
		this.idPaper = idPaper;
		this.title = title;
		this.journaltitle = journaltitle;
		this.author = author;
		this.coauthors = coauthors;
		this.keywords = keywords;
		this.area = area;
		this.status = status;
		this.content = content;
	}


	public PaperDTO(Paper paper) {
		// TODO Auto-generated constructor stub
		this.id = paper.getId();
		this.title = paper.getTitle();
		this.author = paper.getAuthor().getName()+" "+paper.getAuthor().getSurname();
		this.area = paper.getArea().getName();
		this.status = paper.getStatus();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPaper() {
		return idPaper;
	}

	public void setIdPaper(Long idPaper) {
		this.idPaper = idPaper;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getJournaltitle() {
		return journaltitle;
	}

	public void setJournaltitle(String journaltitle) {
		this.journaltitle = journaltitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCoauthors() {
		return coauthors;
	}

	public void setCoauthors(String coauthors) {
		this.coauthors = coauthors;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
    
    
}
