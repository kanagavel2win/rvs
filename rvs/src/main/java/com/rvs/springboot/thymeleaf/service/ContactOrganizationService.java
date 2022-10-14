package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.ContactOrganization;

public interface ContactOrganizationService {
	
	public ContactOrganization save(ContactOrganization obj);
	public ContactOrganization findById(Integer id);
	public List<ContactOrganization> findAll();
	public List<ContactOrganization> saveall(List<ContactOrganization> objList);
	
}
