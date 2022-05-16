package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.HireMaster;


public interface HireMasterService {

	public HireMaster save(HireMaster obj);
	public HireMaster findById(Integer id);
	public List<HireMaster> findAll();
	public void deleteByid(int id);
}
