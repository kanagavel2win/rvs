package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Map;

import com.rvs.springboot.thymeleaf.entity.ActivityMaster;

public interface ActivityMasterService {
	
	public ActivityMaster save(ActivityMaster obj);
	public List<ActivityMaster> saveall(List<ActivityMaster> obj);
	public ActivityMaster findById(Integer id);
	public List<ActivityMaster> findAll();
	public  List<Map<String, Object>> gettimelinelist(String mastercategory,String mastercategoryid) ;
	public  List<Map<String, Object>> nextactivity(String mastercategory,String mastercategoryid) ;
	public  List<Map<String, Object>> historypendingactivity(String mastercategory,String mastercategoryid) ;
	public void deletebyid(int id);
	public List<ActivityMaster> findByMastercategoryAndMastercategoryid(String mastercategory,String mastercategoryid);
}
