package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.AssetAudit;

public interface AssetAuditService {
	
	public AssetAudit save(AssetAudit obj);
	public AssetAudit findById(Integer id);
	public List<AssetAudit> findAll();
	public List<AssetAudit> saveall(List<AssetAudit> objList);
	
}
