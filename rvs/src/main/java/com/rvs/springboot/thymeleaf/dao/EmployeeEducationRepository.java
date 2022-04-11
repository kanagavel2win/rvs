package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.BranchMaster;
import com.rvs.springboot.thymeleaf.entity.EmployeeEducation;

public interface EmployeeEducationRepository extends JpaRepository<EmployeeEducation,Integer>{

}
