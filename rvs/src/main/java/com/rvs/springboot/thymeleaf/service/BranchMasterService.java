package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.BranchMaster;



public interface BranchMasterService {

	public List<BranchMaster> findAll();
	
	public BranchMaster findById(int theId);
	
	public void save(BranchMaster theBranchMaster);
	
	public void deleteById(int theId);
	
}
