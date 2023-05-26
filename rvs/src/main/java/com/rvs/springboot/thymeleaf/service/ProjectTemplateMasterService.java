package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.ProjectTemplatePhase;

public interface ProjectTemplateMasterService {
	
	public ProjectTemplatePhase save(ProjectTemplatePhase obj);
	public ProjectTemplatePhase findById(Integer id);
	public List<ProjectTemplatePhase> findAll();
	public List<ProjectTemplatePhase> saveall(List<ProjectTemplatePhase> objList);
	public void deletenullrows();
}
