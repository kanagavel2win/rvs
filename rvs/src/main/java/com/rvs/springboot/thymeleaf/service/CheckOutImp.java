package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.CheckOutRepository;
import com.rvs.springboot.thymeleaf.entity.CheckOut;

@Service
public class CheckOutImp implements CheckOutService {

	@Autowired
	CheckOutRepository checkoutRepo;
	
	@Override
	public CheckOut save(CheckOut obj) {
		return checkoutRepo.save(obj);
	}

	@Override
	public CheckOut findById(Integer id) {
		Optional<CheckOut> obj=checkoutRepo.findById(id);
		
		CheckOut bm=null;
		
		if(obj.isPresent())
		{	
			bm=obj.get();
		}else
		{
			throw new RuntimeException("Did find any records of checkout id "+ id);
		}
		return bm;
	}

	@Override
	public List<CheckOut> findAll() {
		
		return checkoutRepo.findAll();
	}

	@Override
	public List<CheckOut> saveall(List<CheckOut> listcheckout) {
		return checkoutRepo.saveAll(listcheckout);
	}
		
	
}
