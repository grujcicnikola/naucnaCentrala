package com.example.scientificCenter.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.example.scientificCenter.domain.Author;
import com.example.scientificCenter.domain.ScientificArea;
import com.fasterxml.jackson.annotation.JsonFormat;


@Document(indexName = PaperDoc.INDEX_NAME, type = PaperDoc.TYPE_NAME, shards = 1, replicas = 0)
public class PaperDoc {

	public static final String INDEX_NAME = "paperlibrary";
	public static final String TYPE_NAME = "paper";
	
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	
	
	@Id
	@Field(type = FieldType.Text, store = true)
    private Long id;

	@Field(type = FieldType.Text, analyzer="serbian",store = true)
    private String title;

	@Field(type = FieldType.Text, analyzer="serbian", store = true)
    private String journaltitle;
	
	@Field(type = FieldType.Text, analyzer="serbian", store = true)
	private String author;
	
	@Field(type = FieldType.Text,analyzer="serbian", store = true)
    private String coauthors;
	
	@Field(type = FieldType.Text, analyzer="serbian",store = true)
    private String keywords;
	
	@Field(type = FieldType.Text,analyzer="serbian", store = true)
	private String area;
	
	@Field(type = FieldType.Boolean, store = true)
    private Boolean status;
	
	@Field(type = FieldType.Text, analyzer="serbian", store = true)
    private String content;
	
	@Field(type = FieldType.Text, store = true)
    private String recenzents;

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

	

	public String getJournaltitle() {
		return journaltitle;
	}

	public void setJournaltitle(String journaltitle) {
		this.journaltitle = journaltitle;
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

	public static String getIndexName() {
		return INDEX_NAME;
	}

	public static String getTypeName() {
		return TYPE_NAME;
	}

	public static String getDatePattern() {
		return DATE_PATTERN;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRecenzents() {
		return recenzents;
	}

	public void setRecenzents(String recenzents) {
		this.recenzents = recenzents;
	}
	
	
	
}
