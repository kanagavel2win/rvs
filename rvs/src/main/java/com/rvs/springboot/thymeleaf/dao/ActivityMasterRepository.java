package com.rvs.springboot.thymeleaf.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.ActivityMaster;

public interface ActivityMasterRepository extends JpaRepository<ActivityMaster,Integer>{

	List<ActivityMaster> findByMastercategoryAndMastercategoryid(String mastercategory, String mastercategoryid);

}
