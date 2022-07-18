package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.InsuranceMaster;

public interface InsuranceMasterRepository extends JpaRepository<InsuranceMaster,Integer>{

}
