package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.PaySlipRepository;
import com.rvs.springboot.thymeleaf.entity.payslip;

@Service
public class PaySlipServiceImpl implements PaySlipService {

	@Autowired
	private PaySlipRepository payslipRepository;

	@Autowired
	JdbcTemplate jdbctemplate;
	
	@Override
	public List<payslip> findByEmpid(String Employeeid) {
			return payslipRepository.findByEmployeeid(Employeeid);
	}

	@Override
	public payslip save(payslip obj) {
		return payslipRepository.save(obj);
	}

	@Override
	public List<payslip> findByPayperiod(String Payperiod) {
		
		return payslipRepository.findByPayperiod(Payperiod);
	}

	@Override
	public void deleteByPayperiod(String Payperiod,String branchid) {
		
		//payslipRepository.deleteByPayperiod(Payperiod);
		if(branchid.equalsIgnoreCase("0"))
		{
			String sql ="DELETE FROM `payslip` WHERE payperiod='" +Payperiod + "'" ;
			jdbctemplate.update(sql);
		}else
		{
			String sql ="DELETE FROM `payslip` WHERE payperiod='" +Payperiod + "' and branchid = '" + branchid +"'" ;
			jdbctemplate.update(sql);
		}
		
	}

	@Override
	public payslip findById(Integer id) {
		
		Optional<payslip> obj=payslipRepository.findById(id);
		
		payslip bm=null;
		
		if(obj.isPresent())
		{	
			bm=obj.get();
		}else
		{
			throw new RuntimeException("Did find any records of Payslip id "+ id);
		}
		return bm;
		
		
	}




	

}
