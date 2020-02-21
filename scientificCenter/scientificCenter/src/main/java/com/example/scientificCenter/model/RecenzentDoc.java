package com.example.scientificCenter.model;

import javax.persistence.Id;

import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
@Document(indexName = RecenzentDoc.INDEX_NAME, type = RecenzentDoc.TYPE_NAME, shards = 1, replicas = 0)
public class RecenzentDoc {
	public static final String INDEX_NAME = "recenzentlibrary";
	public static final String TYPE_NAME = "recenzent";
	
	public static final String DATE_PATTERN = "yyyy-MM-dd";

	public RecenzentDoc(Long id, String username, GeoPoint location) {
		super();
		this.id = id;
		this.username = username;
		this.location = location;
	}

	public RecenzentDoc() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@Field(type = FieldType.Text, store = true)
    private Long id;
	
	@Field(type = FieldType.Text, store = true)
	private String username;
	
	@GeoPointField
	private GeoPoint location;
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	


	public GeoPoint getLocation() {
		return location;
	}

	public void setLocation(GeoPoint location) {
		this.location = location;
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



