package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.OrganizationContacts;

public interface ContactOrganizationService {
	
	public OrganizationContacts save(OrganizationContacts obj);
	public OrganizationContacts findById(Integer id);
	public List<OrganizationContacts> findAll();
	public List<OrganizationContacts> saveall(List<OrganizationContacts> objList);
	public List<OrganizationContacts> findbyOrgname(String str);
}
