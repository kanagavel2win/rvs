package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.DealMaster;

public interface DealMasterService {
	
	public DealMaster save(DealMaster obj);
	public DealMaster findById(Integer id);
	public List<DealMaster> findAll();
	public List<DealMaster> saveall(List<DealMaster> objList);
	public void updatepipeline(String str,String pipeline, String notes);
	
}
