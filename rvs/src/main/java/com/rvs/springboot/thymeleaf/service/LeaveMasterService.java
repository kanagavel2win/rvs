package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.LeaveMaster;


public interface LeaveMasterService {

	public LeaveMaster save(LeaveMaster obj);
	public LeaveMaster findById(Integer id);
	public List<LeaveMaster> findAll();
	public void deleteByid(int id);
}
