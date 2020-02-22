package com.example.scientificCenter.elastic.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BooleanQueryDTO {

	@NotNull
	@Size(min=1, max=20)
    private String area;
    
	@NotNull
	@Size(min=2, max=100)
    private String query;
    
    private String operator;
    
    private Boolean isPhraze;

	public BooleanQueryDTO(String area, String query, String operator, Boolean isPhraze) {
		super();
		this.area = area;
		this.query = query;
		this.operator = operator;
		this.isPhraze = isPhraze;
	}

	public BooleanQueryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Boolean getIsPhraze() {
		return isPhraze;
	}

	public void setIsPhraze(Boolean isPhraze) {
		this.isPhraze = isPhraze;
	}

	@Override
	public String toString() {
		return "BooleanQueryDTO [area=" + area + ", query=" + query + ", operator=" + operator + ", isPhraze="
				+ isPhraze + "]";
	}
    
    
}
