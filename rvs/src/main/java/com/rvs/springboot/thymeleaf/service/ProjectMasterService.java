package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Map;

import com.rvs.springboot.thymeleaf.entity.ProjectMaster;

public interface ProjectMasterService {
	
	public ProjectMaster save(ProjectMaster obj);
	public ProjectMaster findById(Integer id);
	public List<ProjectMaster> findAll();
	public List<ProjectMaster> saveall(List<ProjectMaster> objList);
	public void updatepipeline(String str,String pipeline, String notes);
	public List<String> getStateAll();
	public List<String> getDistrictAll(String state);
	public int addnewtask(int projectdetailid,String taskname);
	
	public int insertFiles(String DocumentType,String DocumentNo , String FilePath, int id, String createddate);
	public int deleteFiles(int id);
	public int insertContact(int contactpersonid, int projectid);
	public int deleteContact(int contactpersonid, int projectid);
	
	public List<Map<String, Object>> getdatainvoicereceipt_graph();
	public List<Map<String, Object>> getdataexpense_graph();
}
