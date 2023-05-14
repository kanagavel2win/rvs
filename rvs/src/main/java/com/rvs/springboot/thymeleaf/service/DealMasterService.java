package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.DealMaster;

public interface DealMasterService {
	
	public DealMaster save(DealMaster obj);
	public DealMaster findById(Integer id);
	public List<DealMaster> findAll();
	public List<DealMaster> saveall(List<DealMaster> objList);
	public void updatepipeline(String str,String pipeline, String notes);
	public List<String> getStateAll();
	public List<String> getDistrictAll(String state);
	
	public int insertFiles(String DocumentType,String DocumentNo , String FilePath, int id, String createddate);
	public int deleteFiles(int id);
	public int insertContact(int contactpersonid, int dealid);
	
}
