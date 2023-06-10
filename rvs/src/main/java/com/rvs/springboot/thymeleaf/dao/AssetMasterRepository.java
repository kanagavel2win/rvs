package com.rvs.springboot.thymeleaf.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rvs.springboot.thymeleaf.entity.AssetMaster;

public interface AssetMasterRepository extends JpaRepository<AssetMaster,Integer>{

	@Query("select am from AssetMaster am where am.staffID = :StaffID")
	public List<AssetMaster> findByStaffID(@Param("StaffID")  String StaffID);
	
	@Query("select am from AssetMaster am where am.AssetId in :assetidlist")
	public List<AssetMaster> findByManyassetIds(@Param("assetidlist")  List<Integer> assetidlist);
	
	
}
