package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.ProjectMaster;

public interface ProjectMasterRepository extends JpaRepository<ProjectMaster,Integer>{

}
