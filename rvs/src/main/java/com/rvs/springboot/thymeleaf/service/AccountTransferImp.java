package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.AccountTransferRepository;
import com.rvs.springboot.thymeleaf.entity.AccountTransfer;

@Service
@Transactional
public class AccountTransferImp implements AccountTransferService {

	@Autowired
	AccountTransferRepository activityRepo;

	
	@Override
	public AccountTransfer save(AccountTransfer obj) {
		return activityRepo.save(obj);
	}

	@Override
	public AccountTransfer findById(Integer id) {
		Optional<AccountTransfer> obj = activityRepo.findById(id);

		AccountTransfer bm = null;

		if (obj.isPresent()) {
			bm = obj.get();
		} else {
			throw new RuntimeException("Did find any records of  id " + id);
		}
		return bm;
	}

	@Override
	public List<AccountTransfer> findAll() {

		return activityRepo.findAll();
	}

	@Override
	public List<AccountTransfer> saveall(List<AccountTransfer> obj) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
