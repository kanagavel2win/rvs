package com.rvs.springboot.thymeleaf.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rvs.springboot.thymeleaf.dao.AttendanceMasterRepository;
import com.rvs.springboot.thymeleaf.entity.AttendanceMaster;
import com.rvs.springboot.thymeleaf.entity.AttendanceMaster;
import com.rvs.springboot.thymeleaf.pojo.emppojoPrivillage;

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
		Optional<AttendanceMaster> obj = attendanceMasterRepository.findById(id);

		AttendanceMaster bm = null;

		if (obj.isPresent()) {
			// ----- Object Validation------------------
			Optional<AttendanceMaster> privillageObject = Optional.ofNullable(privillageValidation(obj.get()));
			if (privillageObject.isPresent()) {
				bm = privillageObject.get();
			}
		} else {
			throw new RuntimeException("Did find any records of Branch id " + id);
		}
		return bm;

	}

	@Override
	public List<AttendanceMaster> findAll() {

		List<AttendanceMaster> ls = new ArrayList<>();
		for (AttendanceMaster as : attendanceMasterRepository.findAll()) {
			// ----- Object Validation------------------
			Optional<AttendanceMaster> privillageObject = Optional.ofNullable(privillageValidation(as));
			if (privillageObject.isPresent()) {
				ls.add(privillageObject.get());
			}
		}
		return ls;

	}

	@Override
	public List<AttendanceMaster> findByattendanceDate(String date) {
		return attendanceMasterRepository.findByattendanceDate(date);
	}

	@Override
	public List<Map<String, Object>> getpayrolldetails(String selectedmonth, String holidaysql, int branch_masterid) {

		/*
		 * String sql =
		 * "select * from (select b1.*,b2.staff_name, b2.bankacno,b2.bank_name from (SELECT  employeeid, "
		 * + "sum(CASE WHEN (attstatus ='P') THEN 1 ELSE 0 END)AS 'P', " +
		 * "sum(CASE WHEN (attstatus ='A') THEN 1 ELSE 0 END)AS 'A', " +
		 * "sum(CASE WHEN (attstatus ='T') THEN 1 ELSE 0 END)AS 'T', " +
		 * "sum(CASE WHEN (attstatus ='HL') THEN 1 ELSE 0 END)AS 'HL' FROM attendancemaster WHERE attendance_date between '"
		 * + selectedmonth + "-01 00:00:00' and '" + selectedmonth +
		 * "-31 00:00:00' group by employeeid)b1 left join employeemaster b2 on b1.employeeid=b2.emp_masterid"
		 * +
		 * ")t1 inner join (select c2.employeeid,c2.compayrate,c2.employeejobcompensationid from (SELECT  max(STR_TO_DATE(comeffectivedate,'%Y-%m-%d')) as rowid,e.employeeid FROM employeejobcompensation e where STR_TO_DATE(comeffectivedate,'%Y-%m-%d') <=STR_TO_DATE('"
		 * + selectedmonth +
		 * "-01','%Y-%m-%d')  group by employeeid) c1 inner join employeejobcompensation c2 on c1.rowid=c2.comeffectivedate and c1.employeeid = c2.employeeid)t2 on t1.employeeid=t2.employeeid"
		 * ;
		 */
		if (branch_masterid == 0) {
			
			String sql = "select * from (select b1.*,b2.staff_name, b2.bankacno,b2.bank_name from " + "(SELECT  employeeid,branch_masterid,"
					+ "sum(CASE WHEN (attstatus ='P') THEN 1 ELSE 0 END)AS 'P', "
					+ "sum(CASE WHEN (attstatus ='A') THEN 1 ELSE 0 END)AS 'A', "
					+ "sum(CASE WHEN (attstatus ='SL') THEN 1 ELSE 0 END)AS 'SL', "
					+ "sum(CASE WHEN (attstatus ='T') THEN 1 ELSE 0 END)AS 'T', "
					+ "sum(CASE WHEN (attstatus ='HL') THEN 1 ELSE 0 END)AS 'HL' " + holidaysql
					+ " FROM attendancemaster WHERE  attendance_date between"
					+ "'" + selectedmonth + "-01 00:00:00' and '" + selectedmonth + "-31 00:00:00' group by employeeid,branch_masterid)b1 "
					+ " left join employeemaster b2 on b1.employeeid=b2.emp_masterid)t1 "
					+ " inner join (select c2.employeeid,c2.compayrate,c2.employeejobcompensationid from (SELECT  max(STR_TO_DATE(comeffectivedate,'%Y-%m-%d')) as rowid,e.employeeid FROM employeejobcompensation e where STR_TO_DATE(comeffectivedate,'%Y-%m-%d') <=STR_TO_DATE('"
					+ selectedmonth
					+ "-31','%Y-%m-%d')  group by employeeid) c1 inner join employeejobcompensation c2 on c1.rowid=c2.comeffectivedate and c1.employeeid = c2.employeeid)t2 on t1.employeeid=t2.employeeid"
					+ " left join (select e2.joblocation,e2.employeeid from (SELECT  max(STR_TO_DATE(jobeffectivedate,'%Y-%m-%d')) as jdate,employeeid FROM employeejobinfo e where STR_TO_DATE(jobeffectivedate,'%Y-%m-%d') <=STR_TO_DATE('"
					+ selectedmonth
					+ "-31','%Y-%m-%d')  group by employeeid) e1 inner join employeejobinfo e2 on e1.jdate=e2.jobeffectivedate and e1.employeeid = e2.employeeid)t3 on t3.employeeid=t2.employeeid order by t2.employeeid";
			System.out.println(sql);
			List<Map<String, Object>> atm = JdbcTemplate.queryForList(sql);

			return atm;
			}else if (emppojoPrivillage.allowBranches.contains(branch_masterid)) {
			
		String sql = "select * from (select b1.*,b2.staff_name, b2.bankacno,b2.bank_name from " + "(SELECT  employeeid,branch_masterid,"
				+ "sum(CASE WHEN (attstatus ='P') THEN 1 ELSE 0 END)AS 'P', "
				+ "sum(CASE WHEN (attstatus ='A') THEN 1 ELSE 0 END)AS 'A', "
				+ "sum(CASE WHEN (attstatus ='SL') THEN 1 ELSE 0 END)AS 'SL', "
				+ "sum(CASE WHEN (attstatus ='T') THEN 1 ELSE 0 END)AS 'T', "
				+ "sum(CASE WHEN (attstatus ='HL') THEN 1 ELSE 0 END)AS 'HL' " + holidaysql
				+ " FROM attendancemaster WHERE  branch_masterid=" + branch_masterid + " and attendance_date between"
				+ "'" + selectedmonth + "-01 00:00:00' and '" + selectedmonth + "-31 00:00:00' group by employeeid,branch_masterid)b1 "
				+ " left join employeemaster b2 on b1.employeeid=b2.emp_masterid)t1 "
				+ " inner join (select c2.employeeid,c2.compayrate,c2.employeejobcompensationid from (SELECT  max(STR_TO_DATE(comeffectivedate,'%Y-%m-%d')) as rowid,e.employeeid FROM employeejobcompensation e where STR_TO_DATE(comeffectivedate,'%Y-%m-%d') <=STR_TO_DATE('"
				+ selectedmonth
				+ "-31','%Y-%m-%d')  group by employeeid) c1 inner join employeejobcompensation c2 on c1.rowid=c2.comeffectivedate and c1.employeeid = c2.employeeid)t2 on t1.employeeid=t2.employeeid"
				+ " left join (select e2.joblocation,e2.employeeid from (SELECT  max(STR_TO_DATE(jobeffectivedate,'%Y-%m-%d')) as jdate,employeeid FROM employeejobinfo e where STR_TO_DATE(jobeffectivedate,'%Y-%m-%d') <=STR_TO_DATE('"
				+ selectedmonth
				+ "-31','%Y-%m-%d')  group by employeeid) e1 inner join employeejobinfo e2 on e1.jdate=e2.jobeffectivedate and e1.employeeid = e2.employeeid)t3 on t3.employeeid=t2.employeeid order by t2.employeeid";
		System.out.println(sql);
		List<Map<String, Object>> atm = JdbcTemplate.queryForList(sql);

		return atm;
		} else {
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> getatttendancereport(String monthstr, int prdenddate, int branchid) {
		if (emppojoPrivillage.allowBranches.contains(branchid)) {
			String sqlfinalQuery = "max(case when `attendance_date` = '" + monthstr
					+ "-01 00:00:00' then `attstatus` else '-' end) '01'," + "max(case when `attendance_date` = '"
					+ monthstr + "-02 00:00:00' then `attstatus` else '-' end) '02',"
					+ "max(case when `attendance_date` = '" + monthstr
					+ "-03 00:00:00' then `attstatus` else '-' end) '03'," + "max(case when `attendance_date` = '"
					+ monthstr + "-04 00:00:00' then `attstatus` else '-' end) '04',"
					+ "max(case when `attendance_date` = '" + monthstr
					+ "-05 00:00:00' then `attstatus` else '-' end) '05'," + "max(case when `attendance_date` = '"
					+ monthstr + "-06 00:00:00' then `attstatus` else '-' end) '06',"
					+ "max(case when `attendance_date` = '" + monthstr
					+ "-07 00:00:00' then `attstatus` else '-' end) '07'," + "max(case when `attendance_date` = '"
					+ monthstr + "-08 00:00:00' then `attstatus` else '-' end) '08',"
					+ "max(case when `attendance_date` = '" + monthstr
					+ "-09 00:00:00' then `attstatus` else '-' end) '09'";
			for (int i = 10; i <= prdenddate; i++) {
				sqlfinalQuery += ", max(case when `attendance_date` = '" + monthstr + "-" + i
						+ " 00:00:00' then `attstatus` else '-' end) '" + i + "'";
			}

			String sql = "select em.staff_name,t1.* from (select" + "  `employeeid`," + sqlfinalQuery
					+ " from attendancemaster where branch_masterid=" + branchid + " and attendance_date between '"
					+ monthstr + "-01 00:00:00' and  '" + monthstr + "-" + prdenddate
					+ " 00:00:00' group by `employeeid` order by employeeid )t1 inner join employeemaster em on t1.employeeid=em.emp_masterid";

			//System.out.println(sql);
			List<Map<String, Object>> atm = JdbcTemplate.queryForList(sql);
			return atm;
		} else {
			return null;
		}
	}

	@Override
	public void deleteById(int attendid) {

		try {
			attendanceMasterRepository.deleteById(attendid);
		} catch (Exception ex) {

		}

	}

	private AttendanceMaster privillageValidation(AttendanceMaster obj) {

		if (emppojoPrivillage.allowBranches.contains(obj.getBranchMasterid())) {
			return obj;
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getatttendancereport_AllBranch(String monthstr, int prdenddate) {
		
			String sqlfinalQuery = "max(case when `attendance_date` = '" + monthstr
					+ "-01 00:00:00' then `attstatus` else '-' end) '01'," + "max(case when `attendance_date` = '"
					+ monthstr + "-02 00:00:00' then `attstatus` else '-' end) '02',"
					+ "max(case when `attendance_date` = '" + monthstr
					+ "-03 00:00:00' then `attstatus` else '-' end) '03'," + "max(case when `attendance_date` = '"
					+ monthstr + "-04 00:00:00' then `attstatus` else '-' end) '04',"
					+ "max(case when `attendance_date` = '" + monthstr
					+ "-05 00:00:00' then `attstatus` else '-' end) '05'," + "max(case when `attendance_date` = '"
					+ monthstr + "-06 00:00:00' then `attstatus` else '-' end) '06',"
					+ "max(case when `attendance_date` = '" + monthstr
					+ "-07 00:00:00' then `attstatus` else '-' end) '07'," + "max(case when `attendance_date` = '"
					+ monthstr + "-08 00:00:00' then `attstatus` else '-' end) '08',"
					+ "max(case when `attendance_date` = '" + monthstr
					+ "-09 00:00:00' then `attstatus` else '-' end) '09'";
			for (int i = 10; i <= prdenddate; i++) {
				sqlfinalQuery += ", max(case when `attendance_date` = '" + monthstr + "-" + i
						+ " 00:00:00' then `attstatus` else '-' end) '" + i + "'";
			}

			String sql = "select em.staff_name,t1.* from (select" + "  `employeeid`," + sqlfinalQuery
					+ " from attendancemaster where attendance_date between '"
					+ monthstr + "-01 00:00:00' and  '" + monthstr + "-" + prdenddate
					+ " 00:00:00' group by `employeeid` order by employeeid )t1 inner join employeemaster em on t1.employeeid=em.emp_masterid";

			System.out.println(sql);
			List<Map<String, Object>> atm = JdbcTemplate.queryForList(sql);
			return atm;
		
	}

	@Override
	public int checkAttendanceisthereforFurtureDate(int empid, String effdate) {
		String sql = "select count(employeeid) as acount from attendancemaster where attendance_date >= '"
				+ effdate + "-01 00:00:00' and employeeid ="+ empid ;
		return  JdbcTemplate.queryForObject(sql,Integer.class);
	}

	@Override
	public int getWorkingDayscountExceptsundays(String startdate, String enddate) {
		String sql = "SELECT COUNT(*) AS count_days"
				+ " FROM (SELECT DATE_ADD('"+ startdate +"', INTERVAL n DAY) AS date  FROM (   SELECT @rownum := @rownum + 1 AS n         FROM (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4               UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) t1,             (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4               UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) t2,             (SELECT @rownum := -1) r        ) AS numbers    ) AS date_range    WHERE date BETWEEN '"+ startdate +"' AND '"+ enddate +"'      AND DAYOFWEEK(date) != 1 "; 
		//System.out.println(sql);
		return  JdbcTemplate.queryForObject(sql,Integer.class);
	}

	@Override
	public int getHolidayCount(String startdate, String enddate,String branch) {
		String sql = "SELECT count(*) holidays FROM holiday  hd  inner join holidayextended_props hex on hex.holidayextended_propsid =hd.holidayid  where hex.branch ='"+ branch +"' and hd.start >= '"+ startdate +" 12:00:00'  and  hd.end <='"+ enddate +" 12:00:00' ";
		//System.out.println(sql);
		return  JdbcTemplate.queryForObject(sql,Integer.class);
	}

	@Override
	public List<Map<String, Object>> getPerformancerpt(String startdate, String enddate, String branchsql) {
		
		String sql = "SELECT am.employeeid,am.branch_masterid, bm.branch_name,em.staff_name, sum(case when am.attstatus='P' then 1 else 0 end) as P,sum(case when am.attstatus='A' then 1 else 0 end) as A,sum(case when am.attstatus='SL' then 1 else 0 end) as SL,sum(case when am.attstatus='T' then 1 else 0 end) as T,sum(case when am.attstatus='HL' then 1 else 0 end) as HL FROM attendancemaster am inner join branch_master bm on am.branch_masterid = bm.id  inner join employeemaster em on am.employeeid = em.emp_masterid  where "+ branchsql +" am.attendance_date between '"+ startdate +" 00:00:00'  and '"+ enddate +" 00:00:00' group by am.employeeid,am.branch_masterid order by P desc;";
		
		//System.out.println(sql);
		List<Map<String, Object>> atm = JdbcTemplate.queryForList(sql);
		return atm;
	}

}
