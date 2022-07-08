package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.VendorMaster;

public interface VendorMasterService {
	
	public VendorMaster save(VendorMaster obj);
	public VendorMaster findById(Integer id);
	public List<VendorMaster> findAll();
	
	
}
