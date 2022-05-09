package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.EmployeeMaster;

public interface EmployeeMasterService {
	
	public EmployeeMaster save(EmployeeMaster obj);
	public EmployeeMaster findById(Integer id);
	public List<EmployeeMaster> findAll();
	
	
}
