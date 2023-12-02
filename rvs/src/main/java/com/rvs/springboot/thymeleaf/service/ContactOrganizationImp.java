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

import com.rvs.springboot.thymeleaf.dao.ContactOrganizationRepository;
import com.rvs.springboot.thymeleaf.entity.OrganizationContacts;
import com.rvs.springboot.thymeleaf.entity.OrganizationContacts;
import com.rvs.springboot.thymeleaf.pojo.emppojoPrivillage;

@Service
public class ContactOrganizationImp implements ContactOrganizationService {

	@Autowired
	ContactOrganizationRepository contactOrganizationRepo;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	
	@Override
	public OrganizationContacts save(OrganizationContacts obj) {
		return contactOrganizationRepo.save(obj);
	}

	@Override
	public OrganizationContacts findById(Integer id) {
		Optional<OrganizationContacts> obj=contactOrganizationRepo.findById(id);
		
		OrganizationContacts bm=null;
		
		if(obj.isPresent())
		{	//-----  Object Validation------------------
			Optional<OrganizationContacts> privillageObject=	Optional.ofNullable(privillageValidation(obj.get()));
			if (privillageObject.isPresent()) {
				bm=privillageObject.get();
			}

		}else
		{
			throw new RuntimeException("Did find any records of contactOrganization id "+ id);
		}
		return bm;
	}

	@Override
	public List<OrganizationContacts> findAll() {
		
		 List<OrganizationContacts> ls = new ArrayList<>();
		 for(OrganizationContacts as : contactOrganizationRepo.findAll())
		 {
			//-----  Object Validation------------------
				Optional<OrganizationContacts> privillageObject=	Optional.ofNullable(privillageValidation(as));
				if (privillageObject.isPresent()) {
					ls.add(privillageObject.get());
				}
		 }
		 
		return ls;
	}

	@Override
	public List<OrganizationContacts> saveall(List<OrganizationContacts> objList) {
		return contactOrganizationRepo.saveAll(objList);
	}

	@Override
	public List<OrganizationContacts> findbyOrgname(String str) {

		return contactOrganizationRepo.findByOrgname(str);
	}
		
	@Override
	public int insertContact(String dep, String phonenumber, String email,int contactid, boolean primary) {

		final String sql="INSERT INTO `organization_contact`(`department`, `email`, `phonenumber`, `id`,primarycontact) VALUES ('"+ dep +"','"+ email +"','"+ phonenumber +"',"+ contactid +","+ primary +")";
		
		KeyHolder keyHolder =new GeneratedKeyHolder();
		
		jdbcTemplate.update(connection -> {
		    PreparedStatement ps = connection.prepareStatement(sql, 
		                           Statement.RETURN_GENERATED_KEYS);

		    return ps;
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}

	@Override
	public int updateContact(int id, String dep, String phonenumber, String email, boolean primary) {
		String sql="UPDATE `organization_contact` SET `department`='"+ dep +"',`email`='"+ email +"',`phonenumber`='"+ phonenumber +"',primarycontact="+ primary + " WHERE contactid=" +id ;
		
		return jdbcTemplate.update(sql);
	}

	@Override
	public int deleteContact(int id) {
		String sql ="DELETE FROM `organization_contact` WHERE  contactid=" +id ;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int insertFiles(String DocumentType, String DocumentNo, String FilePath, int contactid) {
		final String sql="INSERT INTO `organization_files`(`document_no`, `document_type`, `file_path`, `id`) VALUES ('"+ DocumentNo +"','"+ DocumentType  +"','"+ FilePath +"',"+ contactid +")";
		
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
		String sql ="DELETE FROM `organization_files` WHERE  filesid=" +id ;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int insertAccountdetails(int acid, String acno, String acname, String bankname, String branchname,
			String ifsccode, int contactid) {
		String sql="UPDATE `organization_acc_no` SET `acname`='"+ acname + "',`acno`='"+ acno + "',`bankname`='"+ bankname + "',`branchname`='"+ branchname + "',`ifsccode`='"+ ifsccode + "',`id`='"+ contactid + "' WHERE accnoid="+  acid;
		return jdbcTemplate.update(sql);
		
	}
		
	private OrganizationContacts privillageValidation(OrganizationContacts obj) {
		
		 return obj;
		/*
		 * if(emppojoPrivillage.allowBranches.contains(obj.getBranchid())) { return obj;
		 * } return null;
		 */
	}
}
