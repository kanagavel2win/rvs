package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.ContactOrganizationRepository;
import com.rvs.springboot.thymeleaf.entity.ContactOrganization;

@Service
public class ContactOrganizationImp implements ContactOrganizationService {

	@Autowired
	ContactOrganizationRepository contactOrganizationRepo;
	
	@Override
	public ContactOrganization save(ContactOrganization obj) {
		return contactOrganizationRepo.save(obj);
	}

	@Override
	public ContactOrganization findById(Integer id) {
		Optional<ContactOrganization> obj=contactOrganizationRepo.findById(id);
		
		ContactOrganization bm=null;
		
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
	public List<ContactOrganization> findAll() {
		
		return contactOrganizationRepo.findAll();
	}

	@Override
	public List<ContactOrganization> saveall(List<ContactOrganization> objList) {
		return contactOrganizationRepo.saveAll(objList);
	}
		
	
}
