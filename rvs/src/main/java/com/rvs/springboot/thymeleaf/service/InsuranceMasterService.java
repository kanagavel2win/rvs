package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.InsuranceMaster;

public interface InsuranceMasterService {
	
	public InsuranceMaster save(InsuranceMaster obj);
	public InsuranceMaster findById(Integer id);
	public List<InsuranceMaster> findByStaffID(String id);
	public List<InsuranceMaster> findByAssetNameID(String id);
	public List<InsuranceMaster> findAll();
	
}
