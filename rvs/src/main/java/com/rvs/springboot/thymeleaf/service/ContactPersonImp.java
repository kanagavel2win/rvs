package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.ContactPersonRepository;
import com.rvs.springboot.thymeleaf.entity.ContactPerson;

@Service
public class ContactPersonImp implements ContactPersonService {

	@Autowired
	ContactPersonRepository contactpersonRepo;
	
	@Override
	public ContactPerson save(ContactPerson obj) {
		return contactpersonRepo.save(obj);
	}

	@Override
	public ContactPerson findById(Integer id) {
		Optional<ContactPerson> obj=contactpersonRepo.findById(id);
		
		ContactPerson bm=null;
		
		if(obj.isPresent())
		{	
			bm=obj.get();
		}else
		{
			throw new RuntimeException("Did find any records of contactperson id "+ id);
		}
		return bm;
	}

	@Override
	public List<ContactPerson> findAll() {
		
		return contactpersonRepo.findAll();
	}

	@Override
	public List<ContactPerson> saveall(List<ContactPerson> objList) {
		return contactpersonRepo.saveAll(objList);
	}
		
	
}
