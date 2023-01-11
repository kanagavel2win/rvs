package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.BranchContact;

public interface BranchContactRepository extends JpaRepository<BranchContact,Integer>{

	
}
