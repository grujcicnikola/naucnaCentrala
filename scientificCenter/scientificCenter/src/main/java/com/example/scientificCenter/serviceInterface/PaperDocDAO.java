package com.example.scientificCenter.serviceInterface;

import com.example.scientificCenter.model.PaperDoc;

public interface PaperDocDAO 
{
	
	public boolean add(PaperDoc r);
	public Iterable<PaperDoc> findAll();
	public boolean update(PaperDoc r);
    public boolean delete(Long id);


}
