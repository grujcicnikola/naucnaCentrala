package com.example.scientificCenter.domain;

import javax.persistence.*;

@Entity
public class PDF {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;


	@Lob
	@Column(name = "data")
	private byte[] data;


	public PDF() {
		super();
		// TODO Auto-generated constructor stub
	}


	public PDF(String originalFilename, byte[] arrayPic) {
		// TODO Auto-generated constructor stub
		this.data=arrayPic;
		this.name=originalFilename;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public byte[] getData() {
		return data;
	}


	public void setData(byte[] data) {
		this.data = data;
	}

	
	
}