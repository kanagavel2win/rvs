package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.DealMasterRepository;
import com.rvs.springboot.thymeleaf.entity.DealMaster;

@Service
public class DealMasterImp implements DealMasterService {

	@Autowired
	DealMasterRepository dealMasterRepo;
	@Autowired
	private JdbcTemplate JdbcTemplate;
	
	
	@Override
	public DealMaster save(DealMaster obj) {
		return dealMasterRepo.save(obj);
	}

	@Override
	public DealMaster findById(Integer id) {
		Optional<DealMaster> obj=dealMasterRepo.findById(id);
		
		DealMaster bm=null;
		
		if(obj.isPresent())
		{	
			bm=obj.get();
		}else
		{
			throw new RuntimeException("Did find any records of dealMaster id "+ id);
		}
		return bm;
	}

	@Override
	public List<DealMaster> findAll() {
		
		return dealMasterRepo.findAll();
	}

	@Override
	public List<DealMaster> saveall(List<DealMaster> objList) {
		return dealMasterRepo.saveAll(objList);
	}

	@Override
	public void updatepipeline(String str,String pipeline, String notes) {
		
		String sql = "";
		if(notes.equalsIgnoreCase(""))
		{
			sql = "update dealmaster set pipeline ='" + pipeline +"'  where id in ("+ str +")";
		}else
		{
			sql = "update dealmaster set pipeline ='" + pipeline +"', lossbacktoleadreason= '"+ notes.trim() + "'  where id in ("+ str +")";
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
		
	
}
