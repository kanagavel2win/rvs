package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.InsuranceMasterRepository;
import com.rvs.springboot.thymeleaf.entity.InsuranceMaster;

@Service
public class InsuranceMasterImp implements InsuranceMasterService {

	@Autowired
	InsuranceMasterRepository insuranceRepo;
	
	@Override
	public InsuranceMaster save(InsuranceMaster obj) {
		return insuranceRepo.save(obj);
	}

	@Override
	public InsuranceMaster findById(Integer id) {
		Optional<InsuranceMaster> obj=insuranceRepo.findById(id);
		
		InsuranceMaster bm=null;
		
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
	public List<InsuranceMaster> findAll() {
		
		return insuranceRepo.findAll();
	}
		
	
}
