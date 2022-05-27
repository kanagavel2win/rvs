package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.LeaveMasterRepository;
import com.rvs.springboot.thymeleaf.entity.LeaveMaster;

@Service
@Transactional
public class LeaveMasterImp implements LeaveMasterService {

	@Autowired
	LeaveMasterRepository LeaveMasterrespository;
	
	
	@Override
	public LeaveMaster save(LeaveMaster obj) {
		return LeaveMasterrespository.save(obj);
	}

	@Override
	public LeaveMaster findById(Integer id) {
		
		Optional<LeaveMaster> obj=LeaveMasterrespository.findById(id);
		
		LeaveMaster LeaveMasterobj=null;
		
		if(obj.isPresent())
		{
			LeaveMasterobj= obj.get();
			
		}else
		{
			throw new RuntimeException("Did find any records of Branch id "+ id);
		}
		
		
		return LeaveMasterobj;
		
	}

	@Override
	public List<LeaveMaster> findAll() {
		return LeaveMasterrespository.findAll();
	}

	@Override
	public void deleteByid(int id) {
		 LeaveMasterrespository.deleteById(id);
	}

	@Override
	public List<LeaveMaster> findByEmpid(Integer id) {
		return LeaveMasterrespository.findByEmpid(id);
	}

}
