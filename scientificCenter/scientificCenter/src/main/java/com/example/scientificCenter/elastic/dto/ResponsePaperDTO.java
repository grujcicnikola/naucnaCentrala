package com.example.scientificCenter.elastic.dto;

import com.example.scientificCenter.model.PaperDoc;

public class ResponsePaperDTO {
	
	PaperDoc paper;
    
	String highlights;

	

	public PaperDoc getPaper() {
		return paper;
	}

	public void setPaper(PaperDoc paper) {
		this.paper = paper;
	}

	public String getHighlights() {
		return highlights;
	}

	public void setHighlights(String highlights) {
		this.highlights = highlights;
	}

	public ResponsePaperDTO(PaperDoc paper, String highlights) {
		super();
		this.paper = paper;
		this.highlights = highlights;
	}

	public ResponsePaperDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ResponsePaperDTO [paper=" + paper + ", highlights=" + highlights + "]";
	}
    
	
}
