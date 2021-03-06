package com.rvs.springboot.thymeleaf.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rvs.springboot.thymeleaf.entity.Holiday;
import com.rvs.springboot.thymeleaf.service.HolidayService;
import com.rvs.springboot.thymeleaf.service.LeaveMasterService;

@RestController
public class EventController {

	@Autowired
	HolidayService holidayService;
	@Autowired
	LeaveMasterService leaveMasterService;
	
	@GetMapping("/events")
	public List<Holiday> eventinformation() {

		return holidayService.findAll();
	}
	
	@GetMapping("/eventsandleave")
	public List<Holiday> eventwithLeaveinformation( @RequestParam("sd") String startdate, @RequestParam("ed") String enddate) {

	//{"id":12,"title":"Diwali Holidays","start":"2022-10-24","end":"2022-10-27","allDay":true,"backgroundColor":"#9ACD32","borderColor":"#9ACD32","color":"#40E0D0","description":"State holiday"}

		ArrayList<Holiday> leaveinfoList=new ArrayList<Holiday>();
		
		DateFormat displaydateFormat = new SimpleDateFormat("yyyy-MM-dd");
				
		List<Map<String,Object>> obj= leaveMasterService.findByDates(startdate, enddate);
		
		
		obj.forEach(rowMap -> {

			Holiday holidayObj = new Holiday();

			holidayObj.setId(Integer.parseInt(rowMap.get("id").toString()));
			holidayObj.setAllDay(true);
			holidayObj.setStart(rowMap.get("fromadate").toString());
			
			
		    Date date1 = null;
			try {
				date1 = new SimpleDateFormat("yyyy-MM-dd").parse(rowMap.get("todate").toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}  
		    Calendar c = Calendar.getInstance(); 
			c.setTime(date1); 
			c.add(Calendar.DATE, 1);
			
			
			holidayObj.setEnd(displaydateFormat.format(c.getTime()).toString());
			
			String status=rowMap.get("status").toString().trim();
			
			if(status.equalsIgnoreCase("Approved"))
			{
				holidayObj.setBackgroundColor("#32CD32");
				holidayObj.setBorderColor("#32CD32");
			}else if(status.equalsIgnoreCase("Pending"))
			{
				holidayObj.setBackgroundColor("#FFC125");
				holidayObj.setBorderColor("#FFC125");
			}else
			{
				holidayObj.setBackgroundColor("#FF6A6A");
				holidayObj.setBorderColor("#FF6A6A");
			}
			
			holidayObj.setColor("#FFFFFF");
			
			String tempstr=rowMap.get("leavetype").toString() + " ~"+rowMap.get("status").toString() + " ~"+rowMap.get("notes").toString() + " ~"+rowMap.get("permissionstarttime").toString() + " ~"+rowMap.get("permissionendtime").toString() + " ~"+rowMap.get("halfday").toString() ;
			if(!(rowMap.get("approvername")== null))
			{
				tempstr += rowMap.get("approvername").toString() + " ~";
			}else
			{
				tempstr +=  " ~";
			}
			if(!(rowMap.get("approverejectdate")== null))
			{
				tempstr += rowMap.get("approverejectdate").toString() + " ~";
			}else
			{
				tempstr +=  " ~";
			}
			if(!(rowMap.get("approvercomments")== null))
			{
				tempstr += rowMap.get("approvercomments").toString() + " ~";
			}else
			{
				tempstr += " ~";
			}
			
			holidayObj.setDescription(tempstr);
			holidayObj.setTitle(rowMap.get("empname").toString());
			
			//-----------------------------------------------------
			leaveinfoList.add(holidayObj);

		});
		
		leaveinfoList.addAll(holidayService.findAll());
		
		return leaveinfoList;
	}

}
