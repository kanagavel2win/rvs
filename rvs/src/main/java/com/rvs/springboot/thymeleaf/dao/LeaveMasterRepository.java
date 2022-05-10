package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.LeaveMaster;

public interface LeaveMasterRepository extends JpaRepository<LeaveMaster, Integer> {

}
