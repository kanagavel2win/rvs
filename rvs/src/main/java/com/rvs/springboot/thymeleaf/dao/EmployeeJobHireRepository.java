package com.rvs.springboot.thymeleaf.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.EmployeeJobHire;

public interface EmployeeJobHireRepository extends JpaRepository<EmployeeJobHire,Integer>{

	List<EmployeeJobHire> findByEmployeeid(Integer id);

}
