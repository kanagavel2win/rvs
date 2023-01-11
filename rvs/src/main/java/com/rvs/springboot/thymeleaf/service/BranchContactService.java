package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.BranchContact;

public interface BranchContactService {
	
	public BranchContact save(BranchContact obj);
	public BranchContact findById(Integer id);
	public List<BranchContact> findAll();
	
}
