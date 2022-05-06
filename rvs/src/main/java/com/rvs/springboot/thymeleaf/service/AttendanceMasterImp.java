package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.AttendanceMasterRepository;
import com.rvs.springboot.thymeleaf.entity.AttendanceMaster;

@Service
public class AttendanceMasterImp implements AttendanceMasterService {
	
	@Autowired
	private AttendanceMasterRepository attendanceMasterRepository;
	
	
	@Override
	public void save(AttendanceMaster obj) {
		attendanceMasterRepository.save(obj);

	}

	@Override
	public AttendanceMaster findById(Integer id) {
		Optional<AttendanceMaster> obj=attendanceMasterRepository.findById(id);
		
		AttendanceMaster bm=null;
		
		if(obj.isPresent())
		{	
			bm=obj.get();
		}else
		{
			throw new RuntimeException("Did find any records of Branch id "+ id);
		}
		return bm;
		
		
	}

	@Override
	public List<AttendanceMaster> findAll() {
		return attendanceMasterRepository.findAll();
	}

	@Override
	public List<AttendanceMaster> findByattendanceDate(String date) {
		return attendanceMasterRepository.findByattendanceDate(date);
	}

}
