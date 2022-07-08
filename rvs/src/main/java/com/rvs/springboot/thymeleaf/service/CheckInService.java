package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.CheckIn;

public interface CheckInService {
	
	public CheckIn save(CheckIn obj);
	public CheckIn findById(Integer id);
	public List<CheckIn> findAll();
	public List<CheckIn> saveall(List<CheckIn> objList);
	
}
