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

import com.rvs.springboot.thymeleaf.dao.BranchMasterRepository;
import com.rvs.springboot.thymeleaf.entity.BranchMaster;

@Service
public class BranchMasterImp implements BranchMasterService {

	@Autowired
	BranchMasterRepository branchRepo;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	@Override
	public BranchMaster save(BranchMaster obj) {
	 return	branchRepo.save(obj);
	}

	@Override
	public BranchMaster findById(Integer id) {
		Optional<BranchMaster> obj=branchRepo.findById(id);
		
		BranchMaster bm=null;
		
		if(obj.isPresent())
		{	
			bm=obj.get();
		}else
		{
			throw new RuntimeException("Did find any records of Branch id "+ id);
		}
		return bm;
	}

	@Override
	public List<BranchMaster> findAll() {
		
		return branchRepo.findAll();
	}

	@Override
	public int insertbranchContact(String dep, String phonenumber, String email,int branchid, boolean primary) {

		final String sql="INSERT INTO `branch_contact`(`department`, `email`, `phonenumber`, `id`,primarycontact) VALUES ('"+ dep +"','"+ email +"','"+ phonenumber +"',"+ branchid +","+ primary +")";
		System.out.println(sql);
		KeyHolder keyHolder =new GeneratedKeyHolder();
		
		jdbcTemplate.update(connection -> {
		    PreparedStatement ps = connection.prepareStatement(sql, 
		                           Statement.RETURN_GENERATED_KEYS);

		    return ps;
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}

	@Override
	public int updatebranchContact(int id, String dep, String phonenumber, String email, boolean primary) {
		String sql="UPDATE `branch_contact` SET `department`='"+ dep +"',`email`='"+ email +"',`phonenumber`='"+ phonenumber +"',primarycontact="+ primary + " WHERE branchcontactid=" +id ;
		
		return jdbcTemplate.update(sql);
	}

	@Override
	public int deletebranchContact(int id) {
		String sql ="DELETE FROM `branch_contact` WHERE  branchcontactid=" +id ;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int insertbranchFiles(String DocumentType, String DocumentNo, String FilePath, int branchid) {
		final String sql="INSERT INTO `branch_files`(`document_no`, `document_type`, `file_path`, `id`) VALUES ('"+ DocumentNo +"','"+ DocumentType  +"','"+ FilePath +"',"+ branchid +")";
		
		KeyHolder keyHolder =new GeneratedKeyHolder();
		
		jdbcTemplate.update(connection -> {
		    PreparedStatement ps = connection.prepareStatement(sql, 
		                           Statement.RETURN_GENERATED_KEYS);

		    return ps;
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}

	@Override
	public int deletebranchFiles(int id) {
		String sql ="DELETE FROM `branch_files` WHERE  branchfilesid=" +id ;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int insertbranchAccountdetails(int acid, String acno, String acname, String bankname, String branchname,
			String ifsccode, int branchid) {
		String sql="UPDATE `branch_acc_no` SET `acname`='"+ acname + "',`acno`='"+ acno + "',`bankname`='"+ bankname + "',`branchname`='"+ branchname + "',`ifsccode`='"+ ifsccode + "',`branchid`='"+ branchid + "' WHERE branch_accnoid="+  acid;
		return jdbcTemplate.update(sql);
		
	}
		
	
}
