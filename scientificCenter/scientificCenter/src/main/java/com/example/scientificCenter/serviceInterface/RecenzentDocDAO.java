package com.example.scientificCenter.serviceInterface;

import com.example.scientificCenter.model.PaperDoc;
import com.example.scientificCenter.model.RecenzentDoc;

public interface RecenzentDocDAO {
	public boolean add(RecenzentDoc r);
	public Iterable<RecenzentDoc> findAll();
	public boolean update(RecenzentDoc r);
    public boolean delete(Long id);

}
