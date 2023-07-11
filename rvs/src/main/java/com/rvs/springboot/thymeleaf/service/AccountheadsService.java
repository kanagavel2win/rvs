package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Map;

import com.rvs.springboot.thymeleaf.entity.Accountsheads;

public interface AccountheadsService {
	
	public Accountsheads save(Accountsheads obj);
	public List<Accountsheads> saveall(List<Accountsheads> obj);
	public Accountsheads findById(Integer id);
	public List<Accountsheads> findAll();
	public List<Map<String, Object>> getAccountsReceivable();
	public List<Map<String, Object>> getAccountsPayable();
	public List<Map<String, Object>> getGSTPayable();
	public List<Map<String, Object>> getGSTReceivable();
	public List<Map<String, Object>> getSalesIncome();
	public List<Map<String, Object>> getInterestIncome();
	public List<Map<String, Object>> getOtherIncome();
	public List<Map<String, Object>> getinvoice_receipt_master();
	public List<Map<String, Object>> getbranchpurchase_payment_master();
	public List<Map<String, Object>> getprojectpurchase_payment_master();
	
	
}
