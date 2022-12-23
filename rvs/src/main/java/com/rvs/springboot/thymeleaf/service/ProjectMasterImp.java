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

import com.rvs.springboot.thymeleaf.dao.ProjectMasterRepository;
import com.rvs.springboot.thymeleaf.entity.ProjectMaster;

@Service
public class ProjectMasterImp implements ProjectMasterService {

	@Autowired
	ProjectMasterRepository projectMasterRepo;
	@Autowired
	private JdbcTemplate JdbcTemplate;
	
	
	@Override
	public ProjectMaster save(ProjectMaster obj) {
		return projectMasterRepo.save(obj);
	}

	@Override
	public ProjectMaster findById(Integer id) {
		Optional<ProjectMaster> obj=projectMasterRepo.findById(id);
		
		ProjectMaster bm=null;
		
		if(obj.isPresent())
		{	
			bm=obj.get();
		}else
		{
			throw new RuntimeException("Did find any records of projectMaster id "+ id);
		}
		return bm;
	}

	@Override
	public List<ProjectMaster> findAll() {
		
		return projectMasterRepo.findAll();
	}

	@Override
	public List<ProjectMaster> saveall(List<ProjectMaster> objList) {
		return projectMasterRepo.saveAll(objList);
	}

	@Override
	public void updatepipeline(String str,String pipeline, String notes) {
		
		String sql = "";
		if(notes.equalsIgnoreCase(""))
		{
			sql = "update projectmaster set pipeline ='" + pipeline +"'  where id in ("+ str +")";
		}else
		{
			sql = "update projectmaster set pipeline ='" + pipeline +"', lossbacktoleadreason= '"+ notes.trim() + "'  where id in ("+ str +")";
		}
		
		JdbcTemplate.execute(sql);
		
	}

	@Override
	public List<String> getStateAll() {
		
		String sql="select distinct(State) from statedistrict";
		return JdbcTemplate.queryForList(sql,String.class);
	}

	@Override
	public List<String> getDistrictAll(String state) {
		String sql="select distinct(District) from statedistrict where State='"+ state+ "'";
			
		return JdbcTemplate.queryForList(sql,String.class);
	}

	@Override
	public int addnewtask(int projectdetailid, String taskname) {
		String sql="INSERT INTO projecttaskmaster(projecttasktitle, projectdetailid) VALUES ('"+ taskname + "','"+ projectdetailid +"')";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		JdbcTemplate.update(connection -> {
		    PreparedStatement ps = connection.prepareStatement(sql, 
		                           Statement.RETURN_GENERATED_KEYS);

		    return ps;
		}, keyHolder);

		return keyHolder.getKey().intValue();
		
		
	}
		
	
}