package com.rvs.springboot.thymeleaf.service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.LeadMasterRepository;
import com.rvs.springboot.thymeleaf.entity.LeadMaster;
import com.rvs.springboot.thymeleaf.entity.LeadMaster;
import com.rvs.springboot.thymeleaf.entity.LeadMaster;
import com.rvs.springboot.thymeleaf.pojo.emppojoPrivillage;

@Service
public class LeadMasterImp implements LeadMasterService {

	@Autowired
	LeadMasterRepository leadMasterRepo;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public LeadMaster save(LeadMaster obj) {
		return leadMasterRepo.save(obj);
	}

	@Override
	public LeadMaster findById(Integer id) {
		Optional<LeadMaster> obj = leadMasterRepo.findById(id);

		LeadMaster bm = null;

		if (obj.isPresent()) {
			
			
			// ----- Object Validation------------------
			Optional<LeadMaster> privillageObject = Optional.ofNullable(privillageValidation(obj.get()));
			if (privillageObject.isPresent()) {
				bm = privillageObject.get();
			}
			
		} else {
			throw new RuntimeException("Did find any records of leadMaster id " + id);
		}
		return bm;
	}

	@Override
	public List<LeadMaster> findAll() {

		List<LeadMaster> ls = new ArrayList<>();
		for (LeadMaster as : leadMasterRepo.findAll()) {
			// ----- Object Validation------------------
			Optional<LeadMaster> privillageObject = Optional.ofNullable(privillageValidation(as));
			if (privillageObject.isPresent()) {
				ls.add(privillageObject.get());
			}
		}

		return ls;
	}

	@Override
	public List<LeadMaster> saveall(List<LeadMaster> objList) {
		return leadMasterRepo.saveAll(objList);
	}

	@Override
	public int insertContact(int contactpersonid, int leadid) {
		final String sql = "INSERT INTO lead_contact(`contact_person`, `id`) VALUES ("
				+ contactpersonid + "," + leadid + ")";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			return ps;
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}
	
	@Override
	public int insertFiles(String DocumentType, String DocumentNo, String FilePath, int leadid, String createddate) {
		final String sql="INSERT INTO `leadfiles`(`document_no`, `document_type`, `file_path`, `id`,createddate) VALUES ('"+ DocumentNo +"','"+ DocumentType  +"','"+ FilePath +"',"+ leadid +",'"+ createddate +"')";
		
		KeyHolder keyHolder =new GeneratedKeyHolder();
		
		jdbcTemplate.update(connection -> {
		    PreparedStatement ps = connection.prepareStatement(sql, 
		                           Statement.RETURN_GENERATED_KEYS);

		    return ps;
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}

	@Override
	public int deleteFiles(int id) {
		String sql ="DELETE FROM `leadfiles` WHERE  fileid=" +id ;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int deleteContact(int contactpersonid, int leadid) {
		String sql ="DELETE FROM `lead_contact` WHERE  contact_person=" +contactpersonid + " and id="+ leadid ;
		return jdbcTemplate.update(sql);
	}

	private LeadMaster privillageValidation(LeadMaster obj) {

		if (emppojoPrivillage.allowBranches.contains(obj.getBranch())) {
			return obj;
		}
		return null;
	}
}
