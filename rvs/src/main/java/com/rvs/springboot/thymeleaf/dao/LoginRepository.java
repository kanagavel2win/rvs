package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rvs.springboot.thymeleaf.entity.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
	Login findByEmpid(String empid);
}
