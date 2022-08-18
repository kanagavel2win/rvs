package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rvs.springboot.thymeleaf.dao.AssetServiceRepository;
import com.rvs.springboot.thymeleaf.entity.AssetService;

@Service
@Transactional
public class AssetServiceImp implements AssetServiceService {

	@Autowired
	AssetServiceRepository assetserviceRepo;
	
	@Override
	public AssetService save(AssetService obj) {
		return assetserviceRepo.save(obj);
	}

	@Override
	public AssetService findById(Integer id) {
		Optional<AssetService> obj=assetserviceRepo.findById(id);
		
		AssetService bm=null;
		
		if(obj.isPresent())
		{	
			bm=obj.get();
		}else
		{
			throw new RuntimeException("Did find any records of assetservice id "+ id);
		}
		return bm;
	}

	@Override
	public List<AssetService> findAll() {
		
		return assetserviceRepo.findAll();
	}

	@Override
	public List<AssetService> saveall(List<AssetService> objList) {
		return assetserviceRepo.saveAll(objList);
	}

	@Override
	public void deleteByid(int id) {
		assetserviceRepo.deleteById(id);
		
	}

	
		
	
}
