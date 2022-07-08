package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.CheckInRepository;
import com.rvs.springboot.thymeleaf.entity.CheckIn;

@Service
public class CheckInImp implements CheckInService {

	@Autowired
	CheckInRepository checinRepo;
	
	@Override
	public CheckIn save(CheckIn obj) {
		return checinRepo.save(obj);
	}

	@Override
	public CheckIn findById(Integer id) {
		Optional<CheckIn> obj=checinRepo.findById(id);
		
		CheckIn bm=null;
		
		if(obj.isPresent())
		{	
			bm=obj.get();
		}else
		{
			throw new RuntimeException("Did find any records of checin id "+ id);
		}
		return bm;
	}

	@Override
	public List<CheckIn> findAll() {
		
		return checinRepo.findAll();
	}

	@Override
	public List<CheckIn> saveall(List<CheckIn> objList) {
		return checinRepo.saveAll(objList);
	}
		
	
}
