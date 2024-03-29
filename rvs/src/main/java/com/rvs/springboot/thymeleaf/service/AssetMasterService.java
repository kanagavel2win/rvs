package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.AssetMaster;

public interface AssetMasterService {
	
	public AssetMaster save(AssetMaster obj);
	public AssetMaster findById(Integer id);
	public List<AssetMaster> findAll();
	public void updatetheAssetStatus(String status,int rowid, String updatetime, String StaffID);
	public void updatetheAssetStatus(String status,int rowid, String updatetime, String StaffID,String Condition);
	public List<AssetMaster> findbyStaffID(String StaffID);
	public List<AssetMaster> findByManyassetIds(List<Integer> assetidlist);
	public int getmaxid();
	
	public int insertassetFiles(String FilePath, int assetid);
	public int deleteassetFiles(int id);
	
}
