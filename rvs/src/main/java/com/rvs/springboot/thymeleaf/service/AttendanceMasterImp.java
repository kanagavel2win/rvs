package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.rvs.springboot.thymeleaf.entity.AttendanceMaster;

public class AttendanceMasterImp implements AttendanceMasterService {
	
	@Autowired
	private AttendanceMasterService attendanceMasterService;
	
	
	@Override
	public void save(AttendanceMaster obj) {
		attendanceMasterService.save(obj);

	}

	@Override
	public AttendanceMaster findById(Integer id) {
		return attendanceMasterService.findById(id);
	}

	@Override
	public List<AttendanceMaster> findAll() {
		return attendanceMasterService.findAll();
	}

}
