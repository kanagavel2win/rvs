package com.rvs.springboot.thymeleaf.service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
			sql = "update dealmaster set pipeline ='" + pipeline +"', lossbacktodealreason= '"+ notes.trim() + "'  where id in ("+ str +")";
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
	public int insertFiles(String DocumentType, String DocumentNo, String FilePath, int dealid, String createddate) {
		final String sql="INSERT INTO `dealfiles`(`document_no`, `document_type`, `file_path`, `id`, createddate) VALUES ('"+ DocumentNo +"','"+ DocumentType  +"','"+ FilePath +"',"+ dealid +",'"+ createddate + "')";
		
		KeyHolder keyHolder =new GeneratedKeyHolder();
		
		JdbcTemplate.update(connection -> {
		    PreparedStatement ps = connection.prepareStatement(sql, 
		                           Statement.RETURN_GENERATED_KEYS);

		    return ps;
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}

	@Override
	public int deleteFiles(int id) {
		String sql ="DELETE FROM `dealfiles` WHERE  fileid=" +id ;
		return JdbcTemplate.update(sql);
	}		
	
	@Override
	public int insertContact(int contactpersonid, int dealid) {
		final String sql = "INSERT INTO deal_contact(`contact_person`, `id`) VALUES ("
				+ contactpersonid + "," + dealid + ")";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		JdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			return ps;
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}

	@Override
	public int deleteContact(int contactpersonid, int dealid) {
		String sql ="DELETE FROM `deal_contact` WHERE  contact_person=" +contactpersonid + " and id="+ dealid ;
		return JdbcTemplate.update(sql);
	}
	
}
