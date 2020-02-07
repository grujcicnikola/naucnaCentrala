package com.example.scientificCenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.domain.Paper;
import com.example.scientificCenter.repository.PaperRepository;

@Service
public class PaperService {
	
	@Autowired
    private PaperRepository paperRepository;

	public void save(Paper paper) {
		this.paperRepository.save(paper);
		
	}

	public Paper findByTitle(String title) {
		// TODO Auto-generated method stub
		return this.paperRepository.findByTitle(title);
	}
}
