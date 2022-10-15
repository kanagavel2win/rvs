package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.LeadMaster;

public interface LeadMasterRepository extends JpaRepository<LeadMaster,Integer>{

}
