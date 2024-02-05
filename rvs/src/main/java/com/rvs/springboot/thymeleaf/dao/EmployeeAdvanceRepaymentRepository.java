package com.rvs.springboot.thymeleaf.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.rvs.springboot.thymeleaf.entity.EmployeeAdvanceRepayment;

public interface EmployeeAdvanceRepaymentRepository extends JpaRepository<EmployeeAdvanceRepayment, Integer> {

	List<EmployeeAdvanceRepayment> findByEmployeeid(Integer id);

	public List<EmployeeAdvanceRepayment> findByPayperiod(String Payperiod);

	@Transactional
	@Modifying
	public void deleteByPayperiod(String Payperiod);
}
