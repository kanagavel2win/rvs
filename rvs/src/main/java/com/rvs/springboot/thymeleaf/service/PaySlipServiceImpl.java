package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.PaySlipRepository;
import com.rvs.springboot.thymeleaf.entity.payslip;
import com.rvs.springboot.thymeleaf.entity.payslip;

@Service
public class PaySlipServiceImpl implements PaySlipService {

	@Autowired
	private PaySlipRepository payslipRepository;

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
	public void deleteByPayperiod(String Payperiod) {
		
		payslipRepository.deleteByPayperiod(Payperiod);
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
