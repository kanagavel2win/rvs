package com.rvs.springboot.thymeleaf.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.InsuranceMaster;

public interface InsuranceMasterRepository extends JpaRepository<InsuranceMaster,Integer>{

	public List<InsuranceMaster> findBystaffID(String id);
	public List<InsuranceMaster> findByassetNameID(String id);
	
}
