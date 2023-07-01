package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.AccountIncomeRepository;
import com.rvs.springboot.thymeleaf.entity.AccountsIncome;

@Service
@Transactional
public class AccountIncomeImp implements AccountIncomeService {

	@Autowired
	AccountIncomeRepository activityRepo;

	
	@Override
	public AccountsIncome save(AccountsIncome obj) {
		return activityRepo.save(obj);
	}

	@Override
	public AccountsIncome findById(Integer id) {
		Optional<AccountsIncome> obj = activityRepo.findById(id);

		AccountsIncome bm = null;

		if (obj.isPresent()) {
			bm = obj.get();
		} else {
			throw new RuntimeException("Did find any records of  id " + id);
		}
		return bm;
	}

	@Override
	public List<AccountsIncome> findAll() {

		return activityRepo.findAll();
	}

	@Override
	public List<AccountsIncome> saveall(List<AccountsIncome> obj) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
