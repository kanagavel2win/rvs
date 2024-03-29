package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.ActivityMasterRepository;
import com.rvs.springboot.thymeleaf.entity.ActivityMaster;

@Service
@Transactional
public class ActivityMasterImp implements ActivityMasterService {

	@Autowired
	ActivityMasterRepository activityRepo;

	@Autowired
	private JdbcTemplate JdbcTemplate;

	@Override
	public ActivityMaster save(ActivityMaster obj) {
		return activityRepo.save(obj);
	}

	@Override
	public ActivityMaster findById(Integer id) {
		Optional<ActivityMaster> obj = activityRepo.findById(id);

		ActivityMaster bm = null;

		if (obj.isPresent()) {
			bm = obj.get();
		} else {
			throw new RuntimeException("Did find any records of Asset id " + id);
		}
		return bm;
	}

	@Override
	public List<ActivityMaster> findAll() {

		return activityRepo.findAll();
	}

	@Override
	public List<Map<String, Object>> gettimelinelist(String mastercategory, String mastercategoryid,String status,String subcategory) {

		if(status.equalsIgnoreCase("Completed")) {
			status=" and status ='Completed'";
		}else {
			status=" and status !='Completed'";
		}
	String sql = "select DATEDIFF(now() , t1.sorteddates)as differdays, TIME_FORMAT(TIMEDIFF(now() , t1.sorteddates), '%H:%i') as differtime, MINUTE(TIMEDIFF(now() , t1.sorteddates)) as differmins,HOUR(TIMEDIFF(now() , t1.sorteddates)) differhr,DATE_FORMAT(t1.sorteddates,'%d-%m-%Y') as newsorteddates,t1.* from (SELECT (case when (startdate IS NULL or startdate ='')  then  STR_TO_DATE(createdtime,'%d-%m-%Y %T') else STR_TO_DATE(concat(startdate,' ',starttime),'%Y-%m-%d %T') end) sorteddates,actmaster.* FROM activitymaster as actmaster where mastercategory='"
				+ mastercategory + "' and mastercategoryid=" + mastercategoryid +" and activitycategory='"+ subcategory +"' "+ status
				+ " order by (case when (startdate IS NULL or startdate ='')  then  STR_TO_DATE(createdtime,'%d-%m-%Y %T') else  STR_TO_DATE(concat(startdate,' ',starttime),'%Y-%m-%d %T') end ) desc)t1  order by t1.sorteddates DESC";
		
		//String sql=" SELECT concat(now(),'') as currentdatetime,concat((case when (startdate IS NULL or startdate ='')  then  STR_TO_DATE(createdtime,'%d-%m-%Y %T') else STR_TO_DATE(concat(startdate,' ',starttime),'%Y-%m-%d %T') end),'') sorteddates,actmaster.* FROM activitymaster as actmaster where mastercategory='"+ mastercategory + "' and mastercategoryid=" + mastercategoryid + status +" order by (case when (startdate IS NULL or startdate ='')  then  STR_TO_DATE(createdtime,'%d-%m-%Y %T') else STR_TO_DATE(concat(startdate,' ',starttime),'%Y-%m-%d %T') end ) desc";
			
		//System.out.println(sql);
		List<Map<String, Object>> result = JdbcTemplate.queryForList(sql);
		return result;
	}

	@Override
	public List<Map<String, Object>> nextactivity(String mastercategory, String mastercategoryid) {
		//String sql = "SELECT * FROM activitymaster WHERE activitycategory ='Activity' and STR_TO_DATE(duedate,'%Y-%m-%d') >= date_format(curdate(),'%Y-%m-%d') and  mastercategory='"+ mastercategory + "' and mastercategoryid in(" + mastercategoryid +") limit 1";
		String sql ="SELECT act.*, COALESCE(DATEDIFF(STR_TO_DATE(enddate,'%Y-%m-%d'),date_format(curdate(),'%Y-%m-%d')),0) as taskdatecount FROM activitymaster act WHERE status <> 'Completed' and activitycategory ='Activity'  and  mastercategory='"+ mastercategory + "' and mastercategoryid in(" + mastercategoryid +") order by STR_TO_DATE(enddate,'%Y-%m-%d') desc limit 1";
		//System.out.println(sql);
		List<Map<String, Object>> result = JdbcTemplate.queryForList(sql);
		return result;
		
	}

	@Override
	public List<ActivityMaster> saveall(List<ActivityMaster> obj) {
		return activityRepo.saveAll(obj);
	}

	@Override
	public List<ActivityMaster> findByMastercategoryAndMastercategoryid(String mastercategory,
			String mastercategoryid) {

		return activityRepo.findByMastercategoryAndMastercategoryid(mastercategory,mastercategoryid);
	}

	@Override
	public void deletebyid(int id) {
		activityRepo.deleteById(id);
		
	}

	@Override
	public List<Map<String, Object>> historypendingactivity(String mastercategory, String mastercategoryid) {
		String sql = "SELECT * FROM activitymaster WHERE activitycategory ='Activity' and STR_TO_DATE(duedate,'%Y-%m-%d') <= date_format(curdate(),'%Y-%m-%d') and status <> 'Completed' and  mastercategory='"+ mastercategory + "' and mastercategoryid in(" + mastercategoryid +") ";
		//System.out.println(sql);
		List<Map<String, Object>> result = JdbcTemplate.queryForList(sql);
		return result;
	}

}
