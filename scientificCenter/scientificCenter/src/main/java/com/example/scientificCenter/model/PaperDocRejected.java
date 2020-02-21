
package com.example.scientificCenter.model;

import java.util.ArrayList;
import java.util.List;

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


@Document(indexName = PaperDocRejected.INDEX_NAME_REJECTED, type = PaperDocRejected.TYPE_NAME_REJECTED, shards = 1, replicas = 0)
public class PaperDocRejected {

	public static final String INDEX_NAME_REJECTED = "paperlibraryrejected";
	public static final String TYPE_NAME_REJECTED = "paperrejected";
	
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	
	
	@Id
	@Field(type = FieldType.Text, store = true)
    private Long id;

	@Field(type = FieldType.Text, store = true)
    private Long idPaper;

	@Field(type = FieldType.Text, analyzer="serbian",store = true)
    private String title;

	@Field(type = FieldType.Text, analyzer="serbian", store = true)
    private String journaltitle;
	
	@Field(type = FieldType.Text, analyzer="serbian", store = true)
	private String author;
	
	@Field(type = FieldType.Text, analyzer="serbian", store = true)
	private List<String> coauthors = new ArrayList<String>();
	
	
	@Field(type = FieldType.Text, analyzer="serbian",store = true)
    private String keywords;
	
	@Field(type = FieldType.Text,analyzer="serbian", store = true)
	private String area;
	
	@Field(type = FieldType.Boolean, store = true)
    private Boolean status;
	
	@Field(type = FieldType.Text, analyzer="serbian", store = true)
    private String content;
	
	@Field(type = FieldType.Long,  store = true)
    private List<Long> recenzentsId= new ArrayList<Long>();
	
	

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

	
	
	public Long getIdPaper() {
		return idPaper;
	}

	public void setIdPaper(Long idPaper) {
		this.idPaper = idPaper;
	}

	
	

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	

	public List<String> getCoauthors() {
		return coauthors;
	}

	public void setCoauthors(List<String> coauthors) {
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


	public static String getIndexNameRejected() {
		return INDEX_NAME_REJECTED;
	}

	public static String getTypeNameRejected() {
		return TYPE_NAME_REJECTED;
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

	
	
	public List<Long> getRecenzentsId() {
		return recenzentsId;
	}

	public void setRecenzentsId(List<Long> recenzentsId) {
		this.recenzentsId = recenzentsId;
	}

	@Override
	public String toString() {
		return "PaperDoc [id=" + id + ", idPaper=" + idPaper + ", title=" + title + ", journaltitle=" + journaltitle
				+ ", author=" + author + ", keywords=" + keywords + ", area=" + area
				+ ", status=" + status + ", content=" + content + "]";
	}

	
	
	
	
}
