package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.ContactOrganizationRepository;
import com.rvs.springboot.thymeleaf.entity.OrganizationContacts;

@Service
public class ContactOrganizationImp implements ContactOrganizationService {

	@Autowired
	ContactOrganizationRepository contactOrganizationRepo;
	
	
	
	@Override
	public OrganizationContacts save(OrganizationContacts obj) {
		return contactOrganizationRepo.save(obj);
	}

	@Override
	public OrganizationContacts findById(Integer id) {
		Optional<OrganizationContacts> obj=contactOrganizationRepo.findById(id);
		
		OrganizationContacts bm=null;
		
		if(obj.isPresent())
		{	
			bm=obj.get();
		}else
		{
			throw new RuntimeException("Did find any records of contactOrganization id "+ id);
		}
		return bm;
	}

	@Override
	public List<OrganizationContacts> findAll() {
		
		return contactOrganizationRepo.findAll();
	}

	@Override
	public List<OrganizationContacts> saveall(List<OrganizationContacts> objList) {
		return contactOrganizationRepo.saveAll(objList);
	}

	@Override
	public List<OrganizationContacts> findbyOrgname(String str) {

		return contactOrganizationRepo.findByOrgname(str);
	}
		
	
}
