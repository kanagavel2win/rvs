package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.EmployeeAdvanceRepayment;

public interface EmployeeAdvanceRepaymentService {
	
	public EmployeeAdvanceRepayment save(EmployeeAdvanceRepayment obj);
	public EmployeeAdvanceRepayment findById(Integer id);
	public List<EmployeeAdvanceRepayment> findAll();
	public List<EmployeeAdvanceRepayment> findByEmployeeid(Integer id);
	public double findByEmployeeidAndPayperiod(Integer id,String Payperiod);
	public void deleteById(int theId);
	public void deleteByPayperiod(String Payperiod,String empid);
}
