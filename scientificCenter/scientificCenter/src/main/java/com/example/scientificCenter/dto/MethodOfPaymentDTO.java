package com.example.scientificCenter.dto;

import com.example.scientificCenter.domain.MethodOfPayment;

public class MethodOfPaymentDTO {

	private Long id;
	
	private String name;

	public MethodOfPaymentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public MethodOfPaymentDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public MethodOfPaymentDTO(MethodOfPayment m) {
		super();
		this.id = m.getId();
		this.name = m.getName();
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
	
	

}
