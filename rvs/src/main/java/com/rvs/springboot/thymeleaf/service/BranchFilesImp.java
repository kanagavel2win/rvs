package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.BranchMasterRepository;
import com.rvs.springboot.thymeleaf.entity.BranchMaster;

@Service
public class BranchFilesImp implements BranchMasterService {

	@Autowired
	BranchMasterRepository branchRepo;
	
	@Override
	public BranchMaster save(BranchMaster obj) {
	 return	branchRepo.save(obj);
	}

	@Override
	public BranchMaster findById(Integer id) {
		Optional<BranchMaster> obj=branchRepo.findById(id);
		
		BranchMaster bm=null;
		
		if(obj.isPresent())
		{	
			bm=obj.get();
		}else
		{
			throw new RuntimeException("Did find any records of Branch id "+ id);
		}
		return bm;
	}

	@Override
	public List<BranchMaster> findAll() {
		
		return branchRepo.findAll();
	}
		
	
}
