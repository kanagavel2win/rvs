package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.HireMasterRepository;
import com.rvs.springboot.thymeleaf.entity.HireMaster;

@Service
public class HireMasterImp implements HireMasterService {

	@Autowired
	HireMasterRepository HireMasterrespository;
	
	
	@Override
	public HireMaster save(HireMaster obj) {
		return HireMasterrespository.save(obj);
	}

	@Override
	public HireMaster findById(Integer id) {
		
		Optional<HireMaster> obj=HireMasterrespository.findById(id);
		
		HireMaster HireMasterobj=null;
		
		if(obj.isPresent())
		{
			HireMasterobj= obj.get();
			
		}else
		{
			throw new RuntimeException("Did find any records of Branch id "+ id);
		}
		
		
		return HireMasterobj;
		
	}

	@Override
	public List<HireMaster> findAll() {
		return HireMasterrespository.findAll();
	}

	@Override
	public void deleteByid(int id) {
		 HireMasterrespository.deleteById(id);
	}

}
