package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.EmployeePrivillageRepository;
import com.rvs.springboot.thymeleaf.entity.EmployeePrivillage;

@Service
@Transactional
public class EmployeePrivillageImp implements EmployeePrivillageService {

	@Autowired
	EmployeePrivillageRepository EmployeePrivillageRepo;
	
	@Override
	public EmployeePrivillage save(EmployeePrivillage obj) {
		return EmployeePrivillageRepo.save(obj);
	}

	@Override
	public EmployeePrivillage findById(Integer id) {
		Optional<EmployeePrivillage> obj=EmployeePrivillageRepo.findById(id);
		
		EmployeePrivillage bm=null;
		
		if(obj.isPresent())
		{	
			bm=obj.get();
		}else
		{
			throw new RuntimeException("Did find any records of EmployeePrivillage id "+ id);
		}
		return bm;
	}

	@Override
	public List<EmployeePrivillage> findAll() {
		
		return EmployeePrivillageRepo.findAll();
	}

	@Override
	public List<EmployeePrivillage> saveall(List<EmployeePrivillage> objList) {
		return EmployeePrivillageRepo.saveAll(objList);
	}

	@Override
	public List<EmployeePrivillage> findByEmployeeid(int empid) {
		return EmployeePrivillageRepo.findByEmployeeid(empid);
	}

	@Override
	public void deleteByEmployeeid(int empid) {
		
		EmployeePrivillageRepo.deleteByEmployeeid(empid);
	}
		
	
}
