package com.example.scientificCenter.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class FormSubmissionDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String fieldId;
	String fieldValue;
	ArrayList<String> areas = new ArrayList<String>();
	
	
	public ArrayList<String> getAreas() {
		return areas;
	}

	public void setAreas(ArrayList<String> areas) {
		this.areas = areas;
	}

	public FormSubmissionDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public FormSubmissionDTO(String fieldId, String fieldValue, ArrayList<String> areas) {
		super();
		this.fieldId = fieldId;
		this.fieldValue = fieldValue;
		this.areas = areas;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	
}
