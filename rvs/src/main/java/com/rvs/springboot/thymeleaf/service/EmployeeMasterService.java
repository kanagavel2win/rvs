package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.EmployeeEducation;
import com.rvs.springboot.thymeleaf.entity.EmployeeEmgContact;
import com.rvs.springboot.thymeleaf.entity.EmployeeExperience;
import com.rvs.springboot.thymeleaf.entity.EmployeeLanguage;
import com.rvs.springboot.thymeleaf.entity.EmployeeMaster;

public interface EmployeeMasterService {
	
	public EmployeeMaster save(EmployeeMaster obj);
	public EmployeeMaster findById(Integer id);
	public EmployeeMaster findByLoginId(Integer id);
	public List<EmployeeMaster> findAll();
	
	public int insertemployeeContact(String dep,String phonenumber , String email, int employeeid, boolean primarycontact);
	public int updateemployeeContact(int id, String dep,String phonenumber , String email, boolean primarycontact);
	public int deleteemployeeContact(int id);
	
	public int insertemployeeAccountdetails(int acid, String acno,String acname , String bankname,String employeename,String ifsccode, int employeeid);
	public int insertemployeeFiles(String DocumentType,String DocumentNo , String FilePath, int employeeid);
	public int deleteemployeeFiles(int id);
	
	public int insertemployeeEmgContact(int id,EmployeeEmgContact emg);
	public int updateemployeeEmgContact(EmployeeEmgContact emg);
	
	
	public int insertemployeeLanguag(int id,EmployeeLanguage langu);
	public int updateemployeeLanguag(EmployeeLanguage langu);
	
	public int insertemployeeQualification(int id,EmployeeEducation edu);
	public int updateemployeeQualification(EmployeeEducation edu);
	
	public int insertemployeeExperience(int id,EmployeeExperience exp);
	public int updateemployeeExperience(EmployeeExperience exp);
	
}
