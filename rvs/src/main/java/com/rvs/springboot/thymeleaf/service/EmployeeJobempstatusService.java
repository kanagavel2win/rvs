package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.EmployeeJobempstatus;

public interface EmployeeJobempstatusService {
	
	public EmployeeJobempstatus save(EmployeeJobempstatus obj);
	public EmployeeJobempstatus findById(Integer id);
	public List<EmployeeJobempstatus> findAll();
	public List<EmployeeJobempstatus> findByEmployeeid(Integer id);
	public void deleteById(int theId);
}
