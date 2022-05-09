package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.Holiday;


public interface HolidayService {

	public Holiday save(Holiday obj);
	public Holiday findById(Integer id);
	public List<Holiday> findAll();
	public void deleteByid(int id);
}
