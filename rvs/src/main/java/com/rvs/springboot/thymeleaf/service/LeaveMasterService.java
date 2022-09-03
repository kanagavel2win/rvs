package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Map;

import com.rvs.springboot.thymeleaf.entity.LeaveMaster;


public interface LeaveMasterService {

	public LeaveMaster save(LeaveMaster obj);
	public LeaveMaster findById(Integer id);
	public List<LeaveMaster> findByEmpid(Integer id);
	public List<LeaveMaster> findAll();
	public void deleteByid(int id);
	public List<Map<String, Object>> findByDates(String startdate, String enddate);
	public List<Map<String, Object>> findByDatesEmpid(int empid,String startdate, String enddate);
}
