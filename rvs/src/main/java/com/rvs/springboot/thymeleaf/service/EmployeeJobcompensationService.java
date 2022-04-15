package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.EmployeeJobcompensation;

public interface EmployeeJobcompensationService {
	
	public EmployeeJobcompensation save(EmployeeJobcompensation obj);
	public EmployeeJobcompensation findById(Integer id);
	public List<EmployeeJobcompensation> findAll();
	public List<EmployeeJobcompensation> findByEmployeeid(Integer id);
	public void deleteById(int theId);
}
