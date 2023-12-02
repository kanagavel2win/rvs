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

import com.rvs.springboot.thymeleaf.dao.ContactPersonRepository;
import com.rvs.springboot.thymeleaf.entity.ContactPerson;
import com.rvs.springboot.thymeleaf.entity.OrganizationContacts;
import com.rvs.springboot.thymeleaf.pojo.emppojoPrivillage;

@Service
public class ContactPersonImp implements ContactPersonService {

	@Autowired
	ContactPersonRepository contactpersonRepo;
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public ContactPerson save(ContactPerson obj) {
		return contactpersonRepo.save(obj);
	}

	@Override
	public ContactPerson findById(Integer id) {
		Optional<ContactPerson> obj = contactpersonRepo.findById(id);

		ContactPerson bm = null;

		if (obj.isPresent()) {
			//-----  Object Validation------------------
			Optional<ContactPerson> privillageObject=	Optional.ofNullable(privillageValidation(obj.get()));
			if (privillageObject.isPresent()) {
				bm=privillageObject.get();
			}
		} else {
			throw new RuntimeException("Did find any records of contactperson id " + id);
		}
		return bm;
	}

	@Override
	public List<ContactPerson> findAll() {

		List<ContactPerson> ls = new ArrayList<>();
		 for(ContactPerson as : contactpersonRepo.findAll())
		 {
			//-----  Object Validation------------------
				Optional<ContactPerson> privillageObject=Optional.ofNullable(privillageValidation(as));
				if (privillageObject.isPresent()) {
					ls.add(privillageObject.get());
				}
		 }
		 
		return ls;
		
	}

	@Override
	public List<ContactPerson> saveall(List<ContactPerson> objList) {
		return contactpersonRepo.saveAll(objList);
	}

	@Override
	public int insertContact(String dep, String phonenumber, String email, int contactid, boolean primary) {

		final String sql = "INSERT INTO `contact_person_contact`(`department`, `email`, `phonenumber`, `id`,primarycontact) VALUES ('"
				+ dep + "','" + email + "','" + phonenumber + "'," + contactid + "," + primary + ")";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			return ps;
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}

	@Override
	public int updateContact(int id, String dep, String phonenumber, String email, boolean primary) {
		String sql = "UPDATE `contact_person_contact` SET `department`='" + dep + "',`email`='" + email
				+ "',`phonenumber`='" + phonenumber + "',primarycontact=" + primary + " WHERE contactid=" + id;

		return jdbcTemplate.update(sql);
	}

	@Override
	public int deleteContact(int id) {
		String sql = "DELETE FROM `contact_person_contact` WHERE  contactid=" + id;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int insertFiles(String DocumentType, String DocumentNo, String FilePath, int contactid) {
		final String sql = "INSERT INTO `contact_person_files`(`document_no`, `document_type`, `file_path`, `id`) VALUES ('"
				+ DocumentNo + "','" + DocumentType + "','" + FilePath + "'," + contactid + ")";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			return ps;
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}

	@Override
	public int deleteFiles(int id) {
		String sql = "DELETE FROM `contact_person_files` WHERE  filesid=" + id;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int insertAccountdetails(int acid, String acno, String acname, String bankname, String branchname,
			String ifsccode, int contactid) {
		String sql = "UPDATE `contact_person_acc_no` SET `acname`='" + acname + "',`acno`='" + acno + "',`bankname`='"
				+ bankname + "',`branchname`='" + branchname + "',`ifsccode`='" + ifsccode + "',`id`='" + contactid
				+ "' WHERE accnoid=" + acid;
		return jdbcTemplate.update(sql);

	}

	@Override
	public List<ContactPerson> contactpersonlistbyorgname(String organizationname) {

		List<ContactPerson> cpls = contactpersonRepo.findByOrganization(organizationname);
		return cpls;
	}

	private ContactPerson privillageValidation(ContactPerson obj) {
		return obj;
		/*
		 * if (emppojoPrivillage.allowBranches.contains(obj.getBranchid())) { return
		 * obj; } return null;
		 */
	}
}
