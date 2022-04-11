package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.EmployeeMasterRepository;
import com.rvs.springboot.thymeleaf.entity.EmployeeMaster;

@Service
public class EmployeeMasterImp implements EmployeeMasterService {

	@Autowired
	EmployeeMasterRepository employeeRepo;
	
	@Override
	public void save(EmployeeMaster obj) {
		employeeRepo.save(obj);
	}

	@Override
	public EmployeeMaster findById(Integer id) {
		Optional<EmployeeMaster> obj=employeeRepo.findById(id);
		
		EmployeeMaster bm=null;
		
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
	public List<EmployeeMaster> findAll() {
		
		return employeeRepo.findAll();
	}

	
	
}
