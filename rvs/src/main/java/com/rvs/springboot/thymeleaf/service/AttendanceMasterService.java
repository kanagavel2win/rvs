package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.AttendanceMaster;

public interface AttendanceMasterService {

	public void save(AttendanceMaster obj);
	public AttendanceMaster findById(Integer id);
	public List<AttendanceMaster> findAll();
	public List<AttendanceMaster>  findByattendanceDate(String date);
	
	
	
}
