package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.EmployeePrivillage;

public interface EmployeePrivillageService {
	
	public EmployeePrivillage save(EmployeePrivillage obj);
	public EmployeePrivillage findById(Integer id);
	public List<EmployeePrivillage> findAll();
	public List<EmployeePrivillage> findByEmployeeid(int empid);
	public List<EmployeePrivillage> saveall(List<EmployeePrivillage> objList);
	public void deleteByEmployeeid(int empid);	
}
