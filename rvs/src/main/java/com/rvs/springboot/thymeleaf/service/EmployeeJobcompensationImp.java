package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.EmployeeJobcompensationRepository;
import com.rvs.springboot.thymeleaf.entity.EmployeeJobcompensation;

@Service
public class EmployeeJobcompensationImp implements EmployeeJobcompensationService {

	@Autowired
	EmployeeJobcompensationRepository employeeRepo;
	
	@Override
	public EmployeeJobcompensation save(EmployeeJobcompensation obj) {
		return employeeRepo.save(obj);
	}

	@Override
	public EmployeeJobcompensation findById(Integer id) {
		Optional<EmployeeJobcompensation> obj=employeeRepo.findById(id);
		
		EmployeeJobcompensation bm=null;
		
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
	public List<EmployeeJobcompensation> findAll() {
		
		return employeeRepo.findAll();
	}

	@Override
	public List<EmployeeJobcompensation> findByEmployeeid(Integer id) {
		// TODO Auto-generated method stub
		return employeeRepo.findByEmployeeid(id);
	}

	
	@Override
	public void deleteById(int theId) {
		 employeeRepo.deleteById(theId);
		
	}
}
