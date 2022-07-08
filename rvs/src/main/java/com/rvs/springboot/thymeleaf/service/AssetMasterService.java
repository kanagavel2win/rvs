package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.AssetMaster;

public interface AssetMasterService {
	
	public AssetMaster save(AssetMaster obj);
	public AssetMaster findById(Integer id);
	public List<AssetMaster> findAll();
	public void updatetheAssetStatus(String status,int rowid);
}
