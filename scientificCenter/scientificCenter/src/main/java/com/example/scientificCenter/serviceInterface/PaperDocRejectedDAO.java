package com.example.scientificCenter.serviceInterface;

import com.example.scientificCenter.model.PaperDoc;
import com.example.scientificCenter.model.PaperDocRejected;



public interface PaperDocRejectedDAO 
{
	
	public boolean add(PaperDocRejected r);
	public Iterable<PaperDocRejected> findAll();
	public boolean update(PaperDocRejected r);
    public boolean delete(Long id);


}
