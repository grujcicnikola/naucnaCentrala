package com.example.scientificCenter.dto;

import java.util.List;

import org.camunda.bpm.engine.form.FormField;

public class FormFieldsDTO {
	
	String taskId;
	List<FormField> formFields;
	String processInstanceId;
	
	public FormFieldsDTO(String taskId, List<FormField> formFields, String processInstanceId) {
		super();
		this.taskId = taskId;
		this.formFields = formFields;
		this.processInstanceId = processInstanceId;
	}

	public FormFieldsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<FormField> getFormFields() {
		return formFields;
	}

	public void setFormFields(List<FormField> formFields) {
		this.formFields = formFields;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	
}
