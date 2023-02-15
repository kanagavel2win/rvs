package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.ContactPerson;

public interface ContactPersonService {
	
	public ContactPerson save(ContactPerson obj);
	public ContactPerson findById(Integer id);
	public List<ContactPerson> findAll();
	public List<ContactPerson> saveall(List<ContactPerson> objList);
	public int insertContact(String dep,String phonenumber , String email, int id, boolean primary);
	public int updateContact(int id, String dep,String phonenumber , String email, boolean primary);
	public int deleteContact(int id);
	
	public int insertAccountdetails(int acid, String acno,String acname , String bankname,String name,String ifsccode, int id);
	public int insertFiles(String DocumentType,String DocumentNo , String FilePath, int id);
	public int deleteFiles(int id);
}
