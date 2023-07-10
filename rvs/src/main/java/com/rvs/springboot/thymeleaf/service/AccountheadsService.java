package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.Accountsheads;

public interface AccountheadsService {
	
	public Accountsheads save(Accountsheads obj);
	public List<Accountsheads> saveall(List<Accountsheads> obj);
	public Accountsheads findById(Integer id);
	public List<Accountsheads> findAll();
	
}
