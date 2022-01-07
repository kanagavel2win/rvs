package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.BranchMasterRepository;
import com.rvs.springboot.thymeleaf.entity.BranchMaster;

@Service
public class BranchMasterServiceImp  implements BranchMasterService{

	@Autowired
	private BranchMasterRepository branchMasterRepository;
	
	@Override
	public List<BranchMaster> findAll() {
		return branchMasterRepository.findAll();
	}

	@Override
	public BranchMaster findById(int theId) {
		return branchMasterRepository.findById(theId).get();
	}

	@Override
	public void save(BranchMaster theBranchMaster) {
		branchMasterRepository.save(theBranchMaster);	
	}

	@Override
	public void deleteById(int theId) {
		branchMasterRepository.deleteById(theId);
		
	}

}
