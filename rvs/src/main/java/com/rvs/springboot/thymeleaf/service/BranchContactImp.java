package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.BranchContactRepository;
import com.rvs.springboot.thymeleaf.entity.BranchContact;

@Service
public class BranchContactImp implements BranchContactService {

	@Autowired
	BranchContactRepository branchRepo;
	
	@Override
	public BranchContact save(BranchContact obj) {
	 return	branchRepo.save(obj);
	}

	@Override
	public BranchContact findById(Integer id) {
		Optional<BranchContact> obj=branchRepo.findById(id);
		
		BranchContact bm=null;
		
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
	public List<BranchContact> findAll() {
		
		return branchRepo.findAll();
	}
		
	
}
