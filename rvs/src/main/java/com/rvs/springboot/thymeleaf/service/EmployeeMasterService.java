package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.EmployeeMaster;

public interface EmployeeMasterService {
	
	public EmployeeMaster save(EmployeeMaster obj);
	public EmployeeMaster findById(Integer id);
	public List<EmployeeMaster> findAll();
	
	public int insertemployeeContact(String dep,String phonenumber , String email, int employeeid);
	public int updateemployeeContact(int id, String dep,String phonenumber , String email);
	public int deleteemployeeContact(int id);
	
	public int insertemployeeAccountdetails(int acid, String acno,String acname , String bankname,String employeename,String ifsccode, int employeeid);
	public int insertemployeeFiles(String DocumentType,String DocumentNo , String FilePath, int employeeid);
	public int deleteemployeeFiles(int id);
	
}
