package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.BranchFiles;

public interface BranchFilesRepository extends JpaRepository<BranchFiles,Integer>{

	
}
