package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.LeaveMasterRepository;
import com.rvs.springboot.thymeleaf.entity.LeaveMaster;

@Service
@Transactional
public class LeaveMasterImp implements LeaveMasterService {

	@Autowired
	LeaveMasterRepository LeaveMasterrespository;

	@Autowired
	JdbcTemplate jdbctemplate;

	@Override
	public LeaveMaster save(LeaveMaster obj) {
		return LeaveMasterrespository.save(obj);
	}

	@Override
	public LeaveMaster findById(Integer id) {

		Optional<LeaveMaster> obj = LeaveMasterrespository.findById(id);

		LeaveMaster LeaveMasterobj = null;

		if (obj.isPresent()) {
			LeaveMasterobj = obj.get();

		} else {
			throw new RuntimeException("Did find any records of Branch id " + id);
		}

		return LeaveMasterobj;

	}

	@Override
	public List<LeaveMaster> findAll() {
		return LeaveMasterrespository.findAll();
	}

	@Override
	public void deleteByid(int id) {
		LeaveMasterrespository.deleteById(id);
	}

	@Override
	public List<LeaveMaster> findByEmpid(Integer id) {
		return LeaveMasterrespository.findByEmpid(id);
	}

	@Override
	public List<Map<String, Object>> findByDates(String startdate, String enddate) {

		String sql = "SELECT lm.*,COALESCE(em.staff_name,'') as empname ,COALESCE(appem.staff_name,'') as approvername FROM leavemaster as lm  left  join employeemaster as em on em.emp_masterid = lm.empid left join  employeemaster  as appem on appem.emp_masterid = lm.approver WHERE "
				+ "STR_TO_DATE(lm.fromadate,'%Y-%m-%d') >= '" + startdate
				+ "' and  STR_TO_DATE(lm.todate,'%Y-%m-%d') <= '" + enddate + "'";

		List<Map<String, Object>> lmhs = jdbctemplate.queryForList(sql);

		return lmhs;
	}

}
