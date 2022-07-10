package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.AssetAuditRepository;
import com.rvs.springboot.thymeleaf.entity.AssetAudit;

@Service
public class AssetAuditImp implements AssetAuditService {

	@Autowired
	AssetAuditRepository assetauditRepo;
	
	@Override
	public AssetAudit save(AssetAudit obj) {
		return assetauditRepo.save(obj);
	}

	@Override
	public AssetAudit findById(Integer id) {
		Optional<AssetAudit> obj=assetauditRepo.findById(id);
		
		AssetAudit bm=null;
		
		if(obj.isPresent())
		{	
			bm=obj.get();
		}else
		{
			throw new RuntimeException("Did find any records of assetaudit id "+ id);
		}
		return bm;
	}

	@Override
	public List<AssetAudit> findAll() {
		
		return assetauditRepo.findAll();
	}

	@Override
	public List<AssetAudit> saveall(List<AssetAudit> objList) {
		return assetauditRepo.saveAll(objList);
	}
		
	
}
