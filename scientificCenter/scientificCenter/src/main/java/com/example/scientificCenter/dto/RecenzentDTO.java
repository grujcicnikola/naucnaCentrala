package com.example.scientificCenter.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.scientificCenter.domain.Recenzent;

public class RecenzentDTO {

	private Long id;
	
	private String name;
	
	private String surname;
	
	private String city;
	
	private String country;

	public RecenzentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RecenzentDTO(Long id, String name, String surname, String city, String country) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.city = city;
		this.country = country;
	}

	public RecenzentDTO(Recenzent recenzent) {
		// TODO Auto-generated constructor stub
		this.id = recenzent.getId();
		this.name = recenzent.getName();
		this.surname = recenzent.getSurname();
		this.city = recenzent.getCity();
		this.country = recenzent.getCountry();
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	
	
	
}
