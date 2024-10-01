package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Map;

import com.rvs.springboot.thymeleaf.entity.AttendanceMaster;

public interface AttendanceMasterService {

	public void save(AttendanceMaster obj);
	public AttendanceMaster findById(Integer id);
	public List<AttendanceMaster> findAll();
	public List<AttendanceMaster>  findByattendanceDate(String date);
	public List<Map<String, Object>> getpayrolldetails(String selectedmonth, String holidaysql, int branch_masterid);
	
	public List<Map<String, Object>> getatttendancereport(String monthstr, int prdenddate, int branchid);
	public List<Map<String, Object>> getatttendancereport_AllBranch(String monthstr, int prdenddate);
	public void deleteById(int attendid);
	
	public int checkAttendanceisthereforFurtureDate(int empid,String effdate);
	
	public int getWorkingDayscountExceptsundays(String startdate ,String enddate);
	public int getHolidayCount(String startdate ,String enddate,String branch);
	public  List<Map<String, Object>> getPerformancerpt(String startdate ,String enddate, String branchsql);
}
