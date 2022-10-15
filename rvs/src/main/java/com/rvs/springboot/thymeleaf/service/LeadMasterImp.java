package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.LeadMasterRepository;
import com.rvs.springboot.thymeleaf.entity.LeadMaster;

@Service
public class LeadMasterImp implements LeadMasterService {

	@Autowired
	LeadMasterRepository leadMasterRepo;
	
	@Override
	public LeadMaster save(LeadMaster obj) {
		return leadMasterRepo.save(obj);
	}

	@Override
	public LeadMaster findById(Integer id) {
		Optional<LeadMaster> obj=leadMasterRepo.findById(id);
		
		LeadMaster bm=null;
		
		if(obj.isPresent())
		{	
			bm=obj.get();
		}else
		{
			throw new RuntimeException("Did find any records of leadMaster id "+ id);
		}
		return bm;
	}

	@Override
	public List<LeadMaster> findAll() {
		
		return leadMasterRepo.findAll();
	}

	@Override
	public List<LeadMaster> saveall(List<LeadMaster> objList) {
		return leadMasterRepo.saveAll(objList);
	}
		
	
}
