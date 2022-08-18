package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.AssetService;

public interface AssetServiceService {
	
	public AssetService save(AssetService obj);
	public AssetService findById(Integer id);
	public List<AssetService> findAll();
	public List<AssetService> saveall(List<AssetService> objList);
	public void deleteByid(int id);
	//public List<AssetService> findByAssetId(String assetid);
}
