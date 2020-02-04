package com.example.scientificCenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.domain.Editor;
import com.example.scientificCenter.domain.Journal;
import com.example.scientificCenter.domain.Recenzent;
import com.example.scientificCenter.repository.EditorRepository;
import com.example.scientificCenter.repository.JournalRepository;
import com.example.scientificCenter.repository.PaperRepository;
import com.example.scientificCenter.repository.RecenzentRepository;

@Service
public class JournalService {
	
	@Autowired
    private JournalRepository journalRepository;
	
	@Autowired
    private EditorRepository editorRepository;
	
	@Autowired
    private RecenzentRepository recenzentRepository;

	public void save(Journal journal) {
		// TODO Auto-generated method stub
		this.journalRepository.save(journal);
	}
	
	public void delete(Journal journal) {
		this.journalRepository.delete(journal);;
	}
	
	public List<Journal> findAll(){
		return this.journalRepository.findAll();
	}
	
	public List<Editor> findAllEditors(){
		return this.editorRepository.findAll();
	}
	
	public List<Recenzent> findAllRecenzents(){
		return this.recenzentRepository.findAll();
	}

	public Journal findByIssn(String string) {
		// TODO Auto-generated method stub
		return this.journalRepository.findByIssn(string);
	}

	public Optional<Editor> findEditorById(Long id) {
		// TODO Auto-generated method stub
		return this.editorRepository.findById(id);
	}
	

	public Optional<Editor> findEditorByJournal(Journal id) {
		// TODO Auto-generated method stub
		return this.editorRepository.findEditorByJournal(id);
	}
	
	public Optional<Recenzent> findRecenzentById(Long id) {
		// TODO Auto-generated method stub
		return this.recenzentRepository.findById(id);
	}

	public void saveEditor(Editor editor) {
		// TODO Auto-generated method stub
		this.editorRepository.save(editor);
	}
	
	public void saveRecenzent(Recenzent recenzent) {
		// TODO Auto-generated method stub
		this.recenzentRepository.save(recenzent);
	}
	
	public Journal getJournalById(Long id) {
		
		return this.journalRepository.findOneById(id);
	}

	public Optional<Journal> findOneById(Long journalId) {
		// TODO Auto-generated method stub
		return journalRepository.findById(journalId);
	}

}

