package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.EmployeeJobHireRepository;
import com.rvs.springboot.thymeleaf.entity.EmployeeJobHire;

@Service
public class EmployeeJobHireImp implements EmployeeJobHireService {

	@Autowired
	EmployeeJobHireRepository employeeRepo;
	
	@Override
	public EmployeeJobHire save(EmployeeJobHire obj) {
		return employeeRepo.save(obj);
	}

	@Override
	public EmployeeJobHire findById(Integer id) {
		Optional<EmployeeJobHire> obj=employeeRepo.findById(id);
		
		EmployeeJobHire bm=null;
		
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
	public List<EmployeeJobHire> findAll() {
		
		return employeeRepo.findAll();
	}

	@Override
	public List<EmployeeJobHire> findByEmployeeid(Integer id) {
		return employeeRepo.findByEmployeeid(id);
	}

	
	
}
