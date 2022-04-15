package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.EmployeeJobHire;
import com.rvs.springboot.thymeleaf.entity.EmployeeJobempstatus;

public interface EmployeeJobHireService {
	
	public EmployeeJobHire save(EmployeeJobHire obj);
	public EmployeeJobHire findById(Integer id);
	public List<EmployeeJobHire> findAll();
	public List<EmployeeJobHire> findByEmployeeid(Integer id);
	
}
