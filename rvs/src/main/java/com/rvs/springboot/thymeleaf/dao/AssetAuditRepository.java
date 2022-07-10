package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.AssetAudit;

public interface AssetAuditRepository extends JpaRepository<AssetAudit,Integer>{

}
