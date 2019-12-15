package com.example.scientificCenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.repository.PaperRepository;

@Service
public class PaperService {
	
	@Autowired
    private PaperRepository paperRepository;
}
