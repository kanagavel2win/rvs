package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.EmployeeJobinfo;

public interface EmployeeJobinfoService {
	
	public EmployeeJobinfo save(EmployeeJobinfo obj);
	public EmployeeJobinfo findById(Integer id);
	public List<EmployeeJobinfo> findAll();
	public List<EmployeeJobinfo> findByEmployeeid(Integer id); 
	public void deleteById(int theId);
}
