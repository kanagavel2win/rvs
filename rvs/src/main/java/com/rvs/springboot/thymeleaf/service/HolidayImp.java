package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.HolidayRepository;
import com.rvs.springboot.thymeleaf.entity.Holiday;

@Service
@Transactional
public class HolidayImp implements HolidayService {

	@Autowired
	HolidayRepository holidayrespository;
	
	@Autowired
	JdbcTemplate jdbctemplate;
	
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
	//SELECT `start`,end,DATEDIFF(STR_TO_DATE(end,'%Y-%m-%d'),STR_TO_DATE(start,'%Y-%m-%d'))    FROM `holiday` WHERE 1

	@Override
	public List<Map<String, Object>> getHolidaysforMonth(String startdate, String enddate) {
		
		String sql="SELECT `start`,end,DATEDIFF(STR_TO_DATE(end,'%Y-%m-%d'),STR_TO_DATE(start,'%Y-%m-%d')) as diff   FROM `holiday` WHERE (STR_TO_DATE(start,'%Y-%m-%d') >= '" + startdate + "' and STR_TO_DATE(start,'%Y-%m-%d') <= '" + enddate + "' ) or ( STR_TO_DATE(end,'%Y-%m-%d') > '" + startdate + "' and STR_TO_DATE(end,'%Y-%m-%d') < '" + enddate + "')";
		List<Map<String, Object>> result = jdbctemplate.queryForList(sql);
		return result;
	}
}
