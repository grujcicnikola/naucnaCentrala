package com.example.scientificCenter.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.model.PaperDoc;
import com.example.scientificCenter.repository.PaperDocRepository;
import com.example.scientificCenter.repository.PaperRepository;
import com.example.scientificCenter.serviceInterface.PaperDocDAO;


@Service
public class PaperDocService implements PaperDocDAO {
	
	@Autowired
    private PaperDocRepository paperRepository;
	
	public PaperDocService() {
	}
	
	public Iterable<PaperDoc> findAll() {
		// TODO Auto-generated method stub
		return paperRepository.findAll();
	}
	
	
	
	public boolean add(PaperDoc unit){
		unit = paperRepository.index(unit);
		if(unit!=null)
			return true;
		else
			return false;
	}

	public boolean update(PaperDoc unit){
		unit = paperRepository.save(unit);
		if(unit!=null)
			return true;
		else
			return false;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}


	

}
