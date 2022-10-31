package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.DealMasterRepository;
import com.rvs.springboot.thymeleaf.entity.DealMaster;

@Service
public class DealMasterImp implements DealMasterService {

	@Autowired
	DealMasterRepository dealMasterRepo;
	
	@Override
	public DealMaster save(DealMaster obj) {
		return dealMasterRepo.save(obj);
	}

	@Override
	public DealMaster findById(Integer id) {
		Optional<DealMaster> obj=dealMasterRepo.findById(id);
		
		DealMaster bm=null;
		
		if(obj.isPresent())
		{	
			bm=obj.get();
		}else
		{
			throw new RuntimeException("Did find any records of dealMaster id "+ id);
		}
		return bm;
	}

	@Override
	public List<DealMaster> findAll() {
		
		return dealMasterRepo.findAll();
	}

	@Override
	public List<DealMaster> saveall(List<DealMaster> objList) {
		return dealMasterRepo.saveAll(objList);
	}
		
	
}
