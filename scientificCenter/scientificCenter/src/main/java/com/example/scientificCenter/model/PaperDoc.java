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

	@Field(type = FieldType.Text, store = true)
    private String title;
	
	@Field(type = FieldType.Text, store = true)
	private String author;
	
	@Field(type = FieldType.Text, store = true)
    private String coauthors;
	
	@Field(type = FieldType.Text, store = true)
    private String _abstract;
	
	@Field(type = FieldType.Text, store = true)
	private String area;
	
	@Field(type = FieldType.Boolean, store = true)
    private Boolean status;

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

	public String get_abstract() {
		return _abstract;
	}

	public void set_abstract(String _abstract) {
		this._abstract = _abstract;
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
	
	
	
}
