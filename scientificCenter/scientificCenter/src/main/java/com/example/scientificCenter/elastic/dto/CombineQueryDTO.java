package com.example.scientificCenter.elastic.dto;

import java.util.ArrayList;
import java.util.List;

public class CombineQueryDTO {
	
	List<BooleanQueryDTO> booleanQueryies = new ArrayList<BooleanQueryDTO>();

	
	
	public CombineQueryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<BooleanQueryDTO> getBooleanQueryies() {
		return booleanQueryies;
	}

	public void setBooleanQueryies(List<BooleanQueryDTO> booleanQueryies) {
		this.booleanQueryies = booleanQueryies;
	}

	@Override
	public String toString() {
		return "CombineQueryDTO [booleanQueryies=" + booleanQueryies + "]";
	}
	
	

}
