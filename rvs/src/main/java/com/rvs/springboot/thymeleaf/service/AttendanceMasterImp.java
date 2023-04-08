package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rvs.springboot.thymeleaf.dao.AttendanceMasterRepository;
import com.rvs.springboot.thymeleaf.entity.AttendanceMaster;

@Service
@Transactional
public class AttendanceMasterImp implements AttendanceMasterService {
	
	@Autowired
	private AttendanceMasterRepository attendanceMasterRepository;
	
	@Autowired
	private JdbcTemplate JdbcTemplate;
	
	
	@Override
	public void save(AttendanceMaster obj) {
		attendanceMasterRepository.save(obj);

	}

	@Override
	public AttendanceMaster findById(Integer id) {
		Optional<AttendanceMaster> obj=attendanceMasterRepository.findById(id);
		
		AttendanceMaster bm=null;
		
		if(obj.isPresent())
		{	
			bm=obj.get();
		}else
		{
			throw new RuntimeException("Did find any records of Branch id "+ id);
		}
		return bm;
		
		
	}

	@Override
	public List<AttendanceMaster> findAll() {
		return attendanceMasterRepository.findAll();
	}

	@Override
	public List<AttendanceMaster> findByattendanceDate(String date) {
		return attendanceMasterRepository.findByattendanceDate(date);
	}

	@Override
	public  List<Map<String, Object>> getpayrolldetails(String selectedmonth,String holidaysql, int branch_masterid) {
		
		
		
		/*String sql = "select * from (select b1.*,b2.staff_name, b2.bankacno,b2.bank_name from (SELECT  employeeid, "
				+ "sum(CASE WHEN (attstatus ='P') THEN 1 ELSE 0 END)AS 'P', "
				+ "sum(CASE WHEN (attstatus ='A') THEN 1 ELSE 0 END)AS 'A', "
				+ "sum(CASE WHEN (attstatus ='T') THEN 1 ELSE 0 END)AS 'T', "
				+ "sum(CASE WHEN (attstatus ='HL') THEN 1 ELSE 0 END)AS 'HL' FROM attendancemaster WHERE attendance_date between '"
				+ selectedmonth + "-01 00:00:00' and '" + selectedmonth
				+ "-31 00:00:00' group by employeeid)b1 left join employeemaster b2 on b1.employeeid=b2.emp_masterid"
				+ ")t1 inner join (select c2.employeeid,c2.compayrate,c2.employeejobcompensationid from (SELECT  max(STR_TO_DATE(comeffectivedate,'%Y-%m-%d')) as rowid,e.employeeid FROM employeejobcompensation e where STR_TO_DATE(comeffectivedate,'%Y-%m-%d') <=STR_TO_DATE('"
				+ selectedmonth
				+ "-01','%Y-%m-%d')  group by employeeid) c1 inner join employeejobcompensation c2 on c1.rowid=c2.comeffectivedate and c1.employeeid = c2.employeeid)t2 on t1.employeeid=t2.employeeid";
			*/
		String sql = "select * from (select b1.*,b2.staff_name, b2.bankacno,b2.bank_name from " + 
				"(SELECT  employeeid," + 
				"sum(CASE WHEN (attstatus ='P') THEN 1 ELSE 0 END)AS 'P', " + 
				"sum(CASE WHEN (attstatus ='A') THEN 1 ELSE 0 END)AS 'A', " + 
				"sum(CASE WHEN (attstatus ='T') THEN 1 ELSE 0 END)AS 'T', " + 
				"sum(CASE WHEN (attstatus ='HL') THEN 1 ELSE 0 END)AS 'HL' "+ holidaysql + " FROM attendancemaster WHERE  branch_masterid="+ branch_masterid +" and attendance_date between" + 
				"'"+ selectedmonth + "-01 00:00:00' and '"+ selectedmonth + "-31 00:00:00' group by employeeid)b1 " + 
				" left join employeemaster b2 on b1.employeeid=b2.emp_masterid)t1 " + 
				" inner join (select c2.employeeid,c2.compayrate,c2.employeejobcompensationid from (SELECT  max(STR_TO_DATE(comeffectivedate,'%Y-%m-%d')) as rowid,e.employeeid FROM employeejobcompensation e where STR_TO_DATE(comeffectivedate,'%Y-%m-%d') <=STR_TO_DATE('"+ selectedmonth + "-31','%Y-%m-%d')  group by employeeid) c1 inner join employeejobcompensation c2 on c1.rowid=c2.comeffectivedate and c1.employeeid = c2.employeeid)t2 on t1.employeeid=t2.employeeid" + 
				" left join (select e2.joblocation,e2.employeeid from (SELECT  max(STR_TO_DATE(jobeffectivedate,'%Y-%m-%d')) as jdate,employeeid FROM employeejobinfo e where STR_TO_DATE(jobeffectivedate,'%Y-%m-%d') <=STR_TO_DATE('"+ selectedmonth + "-31','%Y-%m-%d')  group by employeeid) e1 inner join employeejobinfo e2 on e1.jdate=e2.jobeffectivedate and e1.employeeid = e2.employeeid)t3 on t3.employeeid=t2.employeeid order by t2.employeeid";
		//System.out.println(sql);
		List<Map<String, Object>> atm = JdbcTemplate.queryForList(sql);
		
		
		return atm;
	}

	@Override
	public List<Map<String, Object>> getatttendancereport(String monthstr, int prdenddate) {
		
		String sqlfinalQuery= "max(case when `attendance_date` = '"+ monthstr +"-01 00:00:00' then `attstatus` else '-' end) '01',"+
		"max(case when `attendance_date` = '"+ monthstr +"-02 00:00:00' then `attstatus` else '-' end) '02'," + 
		"max(case when `attendance_date` = '"+ monthstr +"-03 00:00:00' then `attstatus` else '-' end) '03'," + 
		"max(case when `attendance_date` = '"+ monthstr +"-04 00:00:00' then `attstatus` else '-' end) '04'," + 
		"max(case when `attendance_date` = '"+ monthstr +"-05 00:00:00' then `attstatus` else '-' end) '05'," + 
		"max(case when `attendance_date` = '"+ monthstr +"-06 00:00:00' then `attstatus` else '-' end) '06'," + 
		"max(case when `attendance_date` = '"+ monthstr +"-07 00:00:00' then `attstatus` else '-' end) '07'," + 
		"max(case when `attendance_date` = '"+ monthstr +"-08 00:00:00' then `attstatus` else '-' end) '08'," + 
		"max(case when `attendance_date` = '"+ monthstr +"-09 00:00:00' then `attstatus` else '-' end) '09'" ;
		for(int i=10; i<=prdenddate ; i++)
		{
			sqlfinalQuery += ", max(case when `attendance_date` = '"+ monthstr +"-"+ i+ " 00:00:00' then `attstatus` else '-' end) '"+ i+ "'";
		}
		
		String sql = "select em.staff_name,t1.* from (select" + 
				"  `employeeid`," + sqlfinalQuery + 
				"from attendancemaster where attendance_date between '"+ monthstr +"-01 00:00:00' and  '"+ monthstr +"-"+ prdenddate +" 00:00:00' group by `employeeid` order by employeeid )t1 inner join employeemaster em on t1.employeeid=em.emp_masterid";
		
		List<Map<String, Object>> atm = JdbcTemplate.queryForList(sql);
		return atm;
	}

	@Override
	public void deleteById(int attendid) {
		
		try
		{
			attendanceMasterRepository.deleteById(attendid);
		}catch(Exception ex)
		{
			
		}
		
	}

}
