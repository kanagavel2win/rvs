package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.ProjectTemplateMaster;

public interface ProjectTemplateMasterService {
	
	public ProjectTemplateMaster save(ProjectTemplateMaster obj);
	public ProjectTemplateMaster findById(Integer id);
	public List<ProjectTemplateMaster> findAll();
	public List<ProjectTemplateMaster> saveall(List<ProjectTemplateMaster> objList);
	public void deletenullrows();
}
