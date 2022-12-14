package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.ProjectTemplateMasterRepository;
import com.rvs.springboot.thymeleaf.entity.ProjectTemplateMaster;

@Service
public class ProjectTemplateMasterImp implements ProjectTemplateMasterService {

	@Autowired
	ProjectTemplateMasterRepository ProjectTemplateMasterRepo;
	@Autowired
	private JdbcTemplate JdbcTemplate;
	
	
	@Override
	public ProjectTemplateMaster save(ProjectTemplateMaster obj) {
		return ProjectTemplateMasterRepo.save(obj);
	}

	@Override
	public ProjectTemplateMaster findById(Integer id) {
		Optional<ProjectTemplateMaster> obj=ProjectTemplateMasterRepo.findById(id);
		
		ProjectTemplateMaster bm=null;
		
		if(obj.isPresent())
		{	
			bm=obj.get();
		}else
		{
			throw new RuntimeException("Did find any records of ProjectTemplateMaster id "+ id);
		}
		return bm;
	}

	@Override
	public List<ProjectTemplateMaster> findAll() {
		
		return ProjectTemplateMasterRepo.findAll();
	}

	@Override
	public List<ProjectTemplateMaster> saveall(List<ProjectTemplateMaster> objList) {
		return ProjectTemplateMasterRepo.saveAll(objList);
	}

	@Override
	public void deletenullrows() {

		String deletequery = "DELETE FROM `projecttemplateactivitymaster` WHERE `activityorder` IS NULL and `activitytitle`  IS NULL and `activitytype` IS NULL";
		JdbcTemplate.update(deletequery);
	}

	
		//DELETE FROM `projecttemplateactivitymaster` WHERE `activityorder` IS NULL and `activitytitle`  IS NULL and `activitytype` IS NULL
	
}
