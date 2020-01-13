package com.example.scientificCenter.domain;

import javax.persistence.*;

import org.hibernate.annotations.NaturalId;

@Entity
public class Permission {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @NaturalId
    @Column(length = 60, name="name",nullable = false)
	private String name;
	
	public Permission(){}

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