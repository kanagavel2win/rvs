package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.EmployeeAdvanceRepaymentRepository;
import com.rvs.springboot.thymeleaf.entity.EmployeeAdvanceRepayment;

@Service
public class EmployeeAdvanceRepaymentImp implements EmployeeAdvanceRepaymentService {

	@Autowired
	EmployeeAdvanceRepaymentRepository employeeRepo;

	@Autowired
	JdbcTemplate jdbctemplate;

	@Override
	public EmployeeAdvanceRepayment save(EmployeeAdvanceRepayment obj) {
		return employeeRepo.save(obj);
	}

	@Override
	public EmployeeAdvanceRepayment findById(Integer id) {
		Optional<EmployeeAdvanceRepayment> obj = employeeRepo.findById(id);

		EmployeeAdvanceRepayment bm = null;

		if (obj.isPresent()) {
			bm = obj.get();
		} else {
			throw new RuntimeException("Did find any records of Branch id " + id);
		}
		return bm;
	}

	@Override
	public List<EmployeeAdvanceRepayment> findAll() {

		return employeeRepo.findAll();
	}

	@Override
	public List<EmployeeAdvanceRepayment> findByEmployeeid(Integer id) {
		// TODO Auto-generated method stub
		return employeeRepo.findByEmployeeid(id);
	}

	@Override
	public void deleteById(int theId) {
		employeeRepo.deleteById(theId);

	}

	@Override
	public void deleteByPayperiod(String Payperiod, String employeeid) {

		String sql = "DELETE FROM `employeeadvancerepayment` WHERE payperiod='" + Payperiod + "' and employeeid = '"
				+ employeeid + "'";
		jdbctemplate.update(sql);

	}

	@Override
	public double findByEmployeeidAndPayperiod(Integer id, String Payperiod) {

		String sql = "SELECT COALESCE(sum(amount),0) as totalamount FROM employeeadvancerepayment where payperiod='"
				+ Payperiod + "' and employeeid=" + id;
		List<Map<String, Object>> op = jdbctemplate.queryForList(sql);
		return (double) op.get(0).get("totalamount");

	}
}
