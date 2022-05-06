package com.rvs.springboot.thymeleaf.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.AttendanceMaster;

public interface AttendanceMasterRepository extends JpaRepository<AttendanceMaster, Integer>  {

	List<AttendanceMaster> findByattendanceDate(String date);

}
