package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.BranchMaster;

public interface BranchMasterService {
	
	public BranchMaster save(BranchMaster obj);
	public BranchMaster findById(Integer id);
	public List<BranchMaster> findAll();
	
}
