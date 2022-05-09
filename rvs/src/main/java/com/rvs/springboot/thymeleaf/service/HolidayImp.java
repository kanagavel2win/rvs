package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.HolidayRepository;
import com.rvs.springboot.thymeleaf.entity.Holiday;

@Service
@Transactional
public class HolidayImp implements HolidayService {

	@Autowired
	HolidayRepository holidayrespository;
	
	
	@Override
	public Holiday save(Holiday obj) {
		return holidayrespository.save(obj);
	}

	@Override
	public Holiday findById(Integer id) {
		
		Optional<Holiday> obj=holidayrespository.findById(id);
		
		Holiday holidayobj=null;
		
		if(obj.isPresent())
		{
			holidayobj= obj.get();
			
		}else
		{
			throw new RuntimeException("Did find any records of Branch id "+ id);
		}
		
		
		return holidayobj;
		
	}

	@Override
	public List<Holiday> findAll() {
		return holidayrespository.findAll();
	}

	@Override
	public void deleteByid(int id) {
		 holidayrespository.deleteById(id);
	}

}
