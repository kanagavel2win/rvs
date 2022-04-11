package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.entity.EmployeeMaster;

public interface EmployeeMasterService {
	
	public void save(EmployeeMaster obj);
	public EmployeeMaster findById(Integer id);
	public List<EmployeeMaster> findAll();
	
	
}
