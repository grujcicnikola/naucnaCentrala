package com.example.scientificCenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.repository.JournalRepository;
import com.example.scientificCenter.repository.PaperRepository;

@Service
public class JournalService {
	
	@Autowired
    private JournalRepository journalRepository;

}
