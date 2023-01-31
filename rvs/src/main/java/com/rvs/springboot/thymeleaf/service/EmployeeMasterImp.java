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

import com.rvs.springboot.thymeleaf.dao.EmployeeMasterRepository;
import com.rvs.springboot.thymeleaf.entity.EmployeeMaster;

@Service
public class EmployeeMasterImp implements EmployeeMasterService {

	@Autowired
	EmployeeMasterRepository employeeRepo;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public EmployeeMaster save(EmployeeMaster obj) {
		return employeeRepo.save(obj);
	}

	@Override
	public EmployeeMaster findById(Integer id) {
		Optional<EmployeeMaster> obj=employeeRepo.findById(id);
		
		EmployeeMaster bm=null;
		
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
	public List<EmployeeMaster> findAll() {
		
		return employeeRepo.findAll();
	}

	@Override
	public int insertemployeeContact(String dep, String phonenumber, String email, int employeeid) {
		final String sql="INSERT INTO `employee_contact`(`department`, `email`, `phonenumber`, `emp_masterid`) VALUES('"+ dep +"','"+ email +"','"+ phonenumber +"',"+ employeeid +")";
		
		KeyHolder keyHolder =new GeneratedKeyHolder();
		
		jdbcTemplate.update(connection -> {
		    PreparedStatement ps = connection.prepareStatement(sql, 
		                           Statement.RETURN_GENERATED_KEYS);

		    return ps;
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}

	@Override
	public int updateemployeeContact(int id, String dep, String phonenumber, String email) {
		String sql="UPDATE `employee_contact` SET `department`='"+ dep +"',`email`='"+ email +"',`phonenumber`='"+ phonenumber +"' WHERE employeecontactid=" +id ;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int deleteemployeeContact(int id) {
		String sql ="DELETE FROM `employee_contact` WHERE  employeecontactid=" +id ;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int insertemployeeAccountdetails(int acid, String acno, String acname, String bankname, String branchname,
			String ifsccode, int employeeid) {
		String sql="UPDATE `employee_acc_no` SET `acname`='"+ acname + "',`acno`='"+ acno + "',`bankname`='"+ bankname + "',`branchname`='"+ branchname + "',`ifsccode`='"+ ifsccode + "',`branchid`='"+ employeeid + "' WHERE branch_accnoid="+  acid;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int insertemployeeFiles(String DocumentType, String DocumentNo, String FilePath, int employeeid) {
		final String sql="INSERT INTO `employeefiles`(`document_no`, `document_type`, `file_path`, `emp_masterid`) VALUES ('"+ DocumentNo +"','"+ DocumentType  +"','"+ FilePath +"',"+ employeeid +")";
		
		KeyHolder keyHolder =new GeneratedKeyHolder();
		
		jdbcTemplate.update(connection -> {
		    PreparedStatement ps = connection.prepareStatement(sql, 
		                           Statement.RETURN_GENERATED_KEYS);

		    return ps;
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}

	@Override
	public int deleteemployeeFiles(int id) {
		String sql ="DELETE FROM `employeefiles` WHERE  emp_fileid=" +id ;
		return jdbcTemplate.update(sql);
	}

	
	
}
