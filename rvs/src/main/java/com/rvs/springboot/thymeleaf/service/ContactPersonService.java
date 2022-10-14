package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.ContactPerson;

public interface ContactPersonService {
	
	public ContactPerson save(ContactPerson obj);
	public ContactPerson findById(Integer id);
	public List<ContactPerson> findAll();
	public List<ContactPerson> saveall(List<ContactPerson> objList);
	
}
