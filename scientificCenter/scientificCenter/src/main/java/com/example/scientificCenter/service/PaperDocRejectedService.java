
package com.example.scientificCenter.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.model.PaperDoc;
import com.example.scientificCenter.model.PaperDocRejected;
import com.example.scientificCenter.repository.PaperDocRejectedRepository;
import com.example.scientificCenter.repository.PaperDocRepository;
import com.example.scientificCenter.repository.PaperRepository;
import com.example.scientificCenter.serviceInterface.PaperDocDAO;
import com.example.scientificCenter.serviceInterface.PaperDocRejectedDAO;


@Service
public class PaperDocRejectedService implements PaperDocRejectedDAO {
	
	@Autowired
    private PaperDocRejectedRepository paperRepository;
	
	public PaperDocRejectedService() {
	}
	
	public Iterable<PaperDocRejected> findAll() {
		// TODO Auto-generated method stub
		return paperRepository.findAll();
	}
	
	
	
	public boolean add(PaperDocRejected unit){
		unit = paperRepository.index(unit);
		if(unit!=null)
			return true;
		else
			return false;
	}

	public boolean update(PaperDocRejected unit){
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
