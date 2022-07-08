package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.CheckInFiles;

public interface CheckInFilesRepository extends JpaRepository<CheckInFiles,Integer>{

}
