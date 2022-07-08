package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.VendorMasterRepository;
import com.rvs.springboot.thymeleaf.entity.VendorMaster;

@Service
public class VendorMasterImp implements VendorMasterService {

	@Autowired
	VendorMasterRepository VendorRepo;
	
	@Override
	public VendorMaster save(VendorMaster obj) {
		return VendorRepo.save(obj);
	}

	@Override
	public VendorMaster findById(Integer id) {
		Optional<VendorMaster> obj=VendorRepo.findById(id);
		
		VendorMaster bm=null;
		
		if(obj.isPresent())
		{	
			bm=obj.get();
		}else
		{
			throw new RuntimeException("Did find any records of Vendor id "+ id);
		}
		return bm;
	}

	@Override
	public List<VendorMaster> findAll() {
		
		return VendorRepo.findAll();
	}

	
	
}
