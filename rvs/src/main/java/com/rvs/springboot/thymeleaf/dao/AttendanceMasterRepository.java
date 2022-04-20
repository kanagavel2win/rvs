package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.AttendanceMaster;

public interface AttendanceMasterRepository extends JpaRepository<AttendanceMaster, Integer>  {

}
