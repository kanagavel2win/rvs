package com.rvs.springboot.thymeleaf.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.EmployeePrivillage;

public interface EmployeePrivillageRepository extends JpaRepository<EmployeePrivillage,Integer>{

	public List<EmployeePrivillage> findByEmployeeid(int empid);
	public void deleteByEmployeeid(int empid);
}
