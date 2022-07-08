package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.AssetMasterFiles;

public interface AssetFilesRepository extends JpaRepository<AssetMasterFiles,Integer>{

}
