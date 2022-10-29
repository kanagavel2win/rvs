package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.ActivityMaster;

public interface ActivityMasterRepository extends JpaRepository<ActivityMaster,Integer>{

}
