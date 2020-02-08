package com.example.scientificCenter.dto;

import com.example.scientificCenter.domain.PDF;

public class PDFDTO {

	private byte[] data;
	
	private String url;

	public PDFDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public PDFDTO(byte[] data) {
		super();
		this.data = data;
	}


	public PDFDTO(PDF pdf) {
		// TODO Auto-generated constructor stub
		this.data=pdf.getData();
		this.url=pdf.getName();
	}


	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	
	
}
