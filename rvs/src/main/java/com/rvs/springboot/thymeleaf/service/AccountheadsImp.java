package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.AccountHeadsRepository;
import com.rvs.springboot.thymeleaf.entity.Accountsheads;

@Service
@Transactional
public class AccountheadsImp implements AccountheadsService {

	@Autowired
	AccountHeadsRepository activityRepo;
	@Autowired
	private JdbcTemplate JdbcTemplate;
	
	
	@Override
	public Accountsheads save(Accountsheads obj) {
		return activityRepo.save(obj);
	}

	@Override
	public Accountsheads findById(Integer id) {
		Optional<Accountsheads> obj = activityRepo.findById(id);

		Accountsheads bm = null;

		if (obj.isPresent()) {
			bm = obj.get();
		} else {
			throw new RuntimeException("Did find any records of  id " + id);
		}
		return bm;
	}

	@Override
	public List<Accountsheads> findAll() {

		return activityRepo.findAll();
	}

	@Override
	public List<Accountsheads> saveall(List<Accountsheads> obj) {
		
		return activityRepo.saveAll(obj);
	}

	@Override
	public List<Map<String, Object>> getAccountsReceivable() {
		String sql = "SELECT COALESCE(sum(taxable_amount),0) as taxable_amount  FROM rvsland_cms.invoice_item_master;";
		List<Map<String, Object>> op = JdbcTemplate.queryForList(sql);
		return op;
	}

	@Override
	public List<Map<String, Object>> getAccountsPayable() {
		String sql = "select COALESCE(sum(totalamount_amount),0) as totalamount_amount from (SELECT sum(totalamount_amount) as totalamount_amount  FROM rvsland_cms.branchpurchase_item_master union all SELECT sum(totalamount_amount)as totalamount_amount FROM rvsland_cms.projectpurchase_item_master)as t1;";
		List<Map<String, Object>> op = JdbcTemplate.queryForList(sql);
		return op;
	}

	@Override
	public List<Map<String, Object>> getGSTPayable() {
		String sql = "SELECT COALESCE((sum(totalamount_amount) -sum(taxable_amount)),0) as taxamount  FROM rvsland_cms.invoice_item_master";
		List<Map<String, Object>> op = JdbcTemplate.queryForList(sql);
		return op;
	}

	@Override
	public List<Map<String, Object>> getGSTReceivable() {
		String sql = "select COALESCE(sum(taxamount),0) as taxamount from (SELECT (sum(totalamount_amount) -sum(taxable_amount)) as taxamount  FROM rvsland_cms.branchpurchase_item_master union all SELECT (sum(totalamount_amount) -sum(taxable_amount)) as taxamount FROM rvsland_cms.projectpurchase_item_master)as t1;";
		List<Map<String, Object>> op = JdbcTemplate.queryForList(sql);
		return op;
	}

	@Override
	public List<Map<String, Object>> getSalesIncome() {
		String sql = "SELECT COALESCE(sum(totalamount_amount),0) as totalamount_amount  FROM rvsland_cms.invoice_item_master;";
		List<Map<String, Object>> op = JdbcTemplate.queryForList(sql);
		return op;
	}

	@Override
	public List<Map<String, Object>> getInterestIncome() {
		String sql = "SELECT COALESCE(sum(iamount),0) as income FROM rvsland_cms.accounts_income where icategory=\"Interest Income\" ; ";
		List<Map<String, Object>> op = JdbcTemplate.queryForList(sql);
		return op;
	}

	@Override
	public List<Map<String, Object>> getOtherIncome() {
		String sql = "SELECT COALESCE(sum(iamount),0) as income FROM rvsland_cms.accounts_income where icategory<>\"Interest Income\" ; ";
		List<Map<String, Object>> op = JdbcTemplate.queryForList(sql);
		return op;
	}

	@Override
	public List<Map<String, Object>> getinvoice_receipt_master() {
		String sql = "SELECT COALESCE(sum(amount),0) as amount FROM rvsland_cms.invoice_receipt_master;";
		List<Map<String, Object>> op = JdbcTemplate.queryForList(sql);
		return op;
	}

	@Override
	public List<Map<String, Object>> getbranchpurchase_payment_master() {
		String sql = "SELECT COALESCE(sum(amount),0) as amount FROM rvsland_cms.branchpurchase_payment_master;";
		List<Map<String, Object>> op = JdbcTemplate.queryForList(sql);
		return op;
	}

	@Override
	public List<Map<String, Object>> getprojectpurchase_payment_master() {
		String sql = "SELECT COALESCE(sum(amount),0) as amount  FROM rvsland_cms.projectpurchase_payment_master;";
		List<Map<String, Object>> op = JdbcTemplate.queryForList(sql);
		return op;
	}

	@Override
	public List<Map<String, Object>> getaccounttransferdeposit(int searchid) {
		String sql = " SELECT COALESCE(sum(t_amount),0) as t_amount FROM rvsland_cms.account_transfer where t_deposit_to ='"+ searchid +"'";
		List<Map<String, Object>> op = JdbcTemplate.queryForList(sql);
		return op;
	}

	@Override
	public List<Map<String, Object>> getaccounttransferwithdraw(int searchid) {
		String sql = " SELECT COALESCE(sum(t_amount),0) as t_amount FROM rvsland_cms.account_transfer where twithdrawfrom ='"+ searchid +"'";
		List<Map<String, Object>> op = JdbcTemplate.queryForList(sql);
		return op;
	}

	@Override
	public List<Map<String, Object>> getaccountincomedeposit(int searchid) {
		String sql = "SELECT COALESCE(sum(iamount),0) as amount FROM rvsland_cms.accounts_income where idepositto='"+ searchid +"'";
		List<Map<String, Object>> op = JdbcTemplate.queryForList(sql);
		return op;
	}

	@Override
	public List<Map<String, Object>> getaccountincomewithdraw(int searchid) {
		String sql = "SELECT COALESCE(sum(iamount),0) as amount FROM rvsland_cms.accounts_income where ifrom ='"+ searchid +"'";
		List<Map<String, Object>> op = JdbcTemplate.queryForList(sql);
		return op;
	}

	@Override
	public List<Map<String, Object>> getbranchpurchase_payment_master(int searchid) {
		String sql = "SELECT COALESCE(sum(amount),0) as amount FROM rvsland_cms.branchpurchase_payment_master where depitedfrom ='"+ searchid +"'";
		List<Map<String, Object>> op = JdbcTemplate.queryForList(sql);
		return op;
	}

	@Override
	public List<Map<String, Object>> getinvoice_receipt_master(int searchid) {
		String sql = "SELECT COALESCE(sum(amount),0) as amount FROM rvsland_cms.invoice_receipt_master where depositedto ='"+ searchid +"'";
		List<Map<String, Object>> op = JdbcTemplate.queryForList(sql);
		return op;
	}

	@Override
	public List<Map<String, Object>> getprojectpurchase_payment_master(int searchid) {
		String sql = "SELECT COALESCE(sum(amount),0) as amount FROM rvsland_cms.projectpurchase_payment_master where depitedfrom ='"+ searchid +"'";
		List<Map<String, Object>> op = JdbcTemplate.queryForList(sql);
		return op;
	}

	@Override
	public List<Map<String, Object>> getproject_expense_category(int searchid) {
		String sql = "SELECT COALESCE(sum(total),0) as amount FROM rvsland_cms.project_expense where category ='"+ searchid +"'";
		List<Map<String, Object>> op = JdbcTemplate.queryForList(sql);
		return op;
	}

	@Override
	public List<Map<String, Object>> getbranchexpense_item_master_byexpenseItem(int searchid) {
		String sql = "SELECT COALESCE(sum(totalamount_amount),0) as amount FROM rvsland_cms.branchexpense_item_master where branchexpense_item ='"+ searchid +"'";
		List<Map<String, Object>> op = JdbcTemplate.queryForList(sql);
		return op;
	}

	@Override
	public List<Map<String, Object>> getbranchpurchase_item_master_category(int searchid) {
		String sql = "SELECT COALESCE(sum(totalamount_amount),0) as amount FROM rvsland_cms.branchpurchase_item_master where branchpurchase_item ='"+ searchid +"'";
		List<Map<String, Object>> op = JdbcTemplate.queryForList(sql);
		return op;
	}

	@Override
	public List<Map<String, Object>> getprojectpurchase_item_master_category(int searchid) {
		String sql = "SELECT COALESCE(sum(totalamount_amount),0) as amount FROM rvsland_cms.projectpurchase_item_master where projectpurchase_item ='"+ searchid +"'";
		List<Map<String, Object>> op = JdbcTemplate.queryForList(sql);
		return op;
	}

	@Override
	public List<Map<String, Object>> getproject_expensewithdraw(int searchid) {
		String sql = "SELECT COALESCE(sum(total),0) as amount FROM rvsland_cms.project_expense where depitedfrom ='"+ searchid +"'";
		List<Map<String, Object>> op = JdbcTemplate.queryForList(sql);
		return op;
	}
	
	

	

}
