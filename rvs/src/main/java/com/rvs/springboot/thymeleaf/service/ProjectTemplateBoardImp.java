package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.ProjectTemplateBoardRepository;
import com.rvs.springboot.thymeleaf.entity.ProjectTemplateBoard;

@Service
public class ProjectTemplateBoardImp implements ProjectTemplateBoardService {

	@Autowired
	ProjectTemplateBoardRepository ProjectTemplateBoardRepo;
	@Autowired
	private JdbcTemplate JdbcTemplate;
	
	
	@Override
	public ProjectTemplateBoard save(ProjectTemplateBoard obj) {
		return ProjectTemplateBoardRepo.save(obj);
	}

	@Override
	public ProjectTemplateBoard findById(Integer id) {
		Optional<ProjectTemplateBoard> obj=ProjectTemplateBoardRepo.findById(id);
		
		ProjectTemplateBoard bm=null;
		
		if(obj.isPresent())
		{	
			bm=obj.get();
		}else
		{
			throw new RuntimeException("Did find any records of ProjectTemplateBoard id "+ id);
		}
		return bm;
	}

	@Override
	public List<ProjectTemplateBoard> findAll() {
		
		return ProjectTemplateBoardRepo.findAll();
	}

	@Override
	public List<ProjectTemplateBoard> saveall(List<ProjectTemplateBoard> objList) {
		return ProjectTemplateBoardRepo.saveAll(objList);
	}

	@Override
	public void deletenullrows() {

		String deletequery = "DELETE FROM `projecttemplateactivitymaster` WHERE `activityorder` IS NULL and `activitytitle`  IS NULL and `activitytype` IS NULL";
		JdbcTemplate.update(deletequery);
	}

	
		//DELETE FROM `projecttemplateactivitymaster` WHERE `activityorder` IS NULL and `activitytitle`  IS NULL and `activitytype` IS NULL
	
}
