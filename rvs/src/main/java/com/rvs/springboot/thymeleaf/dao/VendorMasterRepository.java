package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.VendorMaster;

public interface VendorMasterRepository extends JpaRepository<VendorMaster,Integer>{

}
