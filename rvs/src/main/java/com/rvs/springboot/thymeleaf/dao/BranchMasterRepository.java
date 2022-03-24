package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.BranchMaster;

public interface BranchMasterRepository extends JpaRepository<BranchMaster,Integer>{

}
