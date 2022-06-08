package com.rvs.springboot.thymeleaf.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.rvs.springboot.thymeleaf.entity.payslip;

@Repository
public interface PaySlipRepository extends JpaRepository<payslip, Integer> {
	public List<payslip> findByEmployeeid(String Employeeid);
	public List<payslip> findByPayperiod(String Payperiod);
	
	@Transactional
	@Modifying
	public void deleteByPayperiod(String Payperiod);
}
