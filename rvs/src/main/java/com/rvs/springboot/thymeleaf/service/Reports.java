package com.rvs.springboot.thymeleaf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class Reports {
	@Autowired
	private JdbcTemplate Jdbc;
	
	public List<String> getData() {
	    String Query = "SELECT count(*) FROM accountsheads"; 
	    List<String> result = Jdbc.queryForList(Query, String.class);
	    return result;
	}
	
	public List<Map<String,Object>> getallData(){
		String Query = "SELECT a.emp_fileid, b.aadhar_no, b.address_state FROM employeefiles a LEFT JOIN employeemaster b ON a.emp_masterid = b.emp_masterid";
	    List<Map<String, Object>> result = Jdbc.queryForList(Query);
	    return result;
		
	}


	 
}
