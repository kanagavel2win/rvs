package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.LeadMaster;

public interface LeadMasterService {
	
	public LeadMaster save(LeadMaster obj);
	public LeadMaster findById(Integer id);
	public List<LeadMaster> findAll();
	public List<LeadMaster> saveall(List<LeadMaster> objList);
	public int insertContact(int contactpersonid, int leadid);
	
	public int insertFiles(String DocumentType,String DocumentNo , String FilePath, int id, String createddate);
	public int deleteFiles(int id);
}
