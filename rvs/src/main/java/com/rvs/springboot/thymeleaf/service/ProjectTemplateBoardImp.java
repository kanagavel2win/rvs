package com.rvs.springboot.thymeleaf.service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.ProjectTemplateBoardRepository;
import com.rvs.springboot.thymeleaf.entity.ProjectTemplateActivityMaster;
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

	@Override
	public void ProjectTemplateActivityMastersave(ProjectTemplateActivityMaster obj, int projecttemplatephaseid) {
		
		
		final String sql="INSERT INTO `projecttemplateactivitymaster`(`projectactivityid`,`activityorder`,`activitytitle`,`activitytype`,`daysfromprojectstartdate`,`notes`,`projecttemplatephaseid`,activityfollowers) VALUES "
		+ "("+ obj.getProjectactivityid() +","+obj.getActivityorder() +",'"+obj.getActivitytitle() +"','"+obj.getActivitytype() +"',"+obj.getDaysfromprojectstartdate() +",'"+obj.getNotes() +"',"+ projecttemplatephaseid +","+ obj.getActivityfollowers() +")";
		KeyHolder keyHolder =new GeneratedKeyHolder();
		
		JdbcTemplate.update(connection -> {
		    PreparedStatement ps = connection.prepareStatement(sql, 
		                           Statement.RETURN_GENERATED_KEYS);

		    return ps;
		}, keyHolder);

		
	}

	
		
	
}
