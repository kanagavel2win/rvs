package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.AssetMaster;

public interface AssetMasterRepository extends JpaRepository<AssetMaster,Integer>{

}
