package com.example.scientificCenter.dto;

import com.example.scientificCenter.domain.ScientificArea;

public class ScientificAreaDTO {
	
	private Long id;
	
	private String name;

	public ScientificAreaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public ScientificAreaDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}



	public ScientificAreaDTO(ScientificArea scientificArea) {
		// TODO Auto-generated constructor stub
		this.id = scientificArea.getId();
		this.name = scientificArea.getName();
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
