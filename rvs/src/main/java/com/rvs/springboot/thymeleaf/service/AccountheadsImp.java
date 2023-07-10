package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.AccountHeadsRepository;
import com.rvs.springboot.thymeleaf.entity.Accountsheads;

@Service
@Transactional
public class AccountheadsImp implements AccountheadsService {

	@Autowired
	AccountHeadsRepository activityRepo;

	
	@Override
	public Accountsheads save(Accountsheads obj) {
		return activityRepo.save(obj);
	}

	@Override
	public Accountsheads findById(Integer id) {
		Optional<Accountsheads> obj = activityRepo.findById(id);

		Accountsheads bm = null;

		if (obj.isPresent()) {
			bm = obj.get();
		} else {
			throw new RuntimeException("Did find any records of  id " + id);
		}
		return bm;
	}

	@Override
	public List<Accountsheads> findAll() {

		return activityRepo.findAll();
	}

	@Override
	public List<Accountsheads> saveall(List<Accountsheads> obj) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
