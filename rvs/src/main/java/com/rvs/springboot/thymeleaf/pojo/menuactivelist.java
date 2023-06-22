package com.rvs.springboot.thymeleaf.pojo;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class menuactivelist {
	
	

	public HashMap<String,Boolean> activemenus = new HashMap<>();
	
	public HashMap<String,Boolean> getactivemenulist(String menutitle) {
		activemenus.clear();
		
		
		
		if(menutitle.equalsIgnoreCase("index"))
		{
			activemenus.put("index", true);
			
		}else if(menutitle.equalsIgnoreCase("admin_hr_employeelist"))
		{
			activemenus.put("Administration", true);
			activemenus.put("HR Management", true);
			activemenus.put("Employee", true);
			
		}else if(menutitle.equalsIgnoreCase("admin_branchlist"))
		{
			
			activemenus.put("Administration", true);
			activemenus.put("Branches", true);
			
			
		}else if(menutitle.equalsIgnoreCase("admin_hr_Hire"))
		{
			activemenus.put("Administration", true);
			activemenus.put("HR Management", true);
			activemenus.put("Hire", true);
			
		}else if(menutitle.equalsIgnoreCase("admin_hr_Insurance"))
		{
			activemenus.put("Administration", true);
			activemenus.put("HR Management", true);
			activemenus.put("Insurance", true);
			
		}else if(menutitle.equalsIgnoreCase("admin_hr_Attendance_Daily Attendance"))
		{
			activemenus.put("Administration", true);
			activemenus.put("HR Management", true);
			activemenus.put("Attendance", true);
			activemenus.put("Daily Attendance", true);						
		}else if(menutitle.equalsIgnoreCase("admin_hr_Attendance_Attendance Report"))
		{
			activemenus.put("Administration", true);
			activemenus.put("HR Management", true);
			activemenus.put("Attendance", true);
			activemenus.put("Attendance Report", true);						
		}else if(menutitle.equalsIgnoreCase("admin_hr_Attendance_Holiday"))
		{
			activemenus.put("Administration", true);
			activemenus.put("HR Management", true);
			activemenus.put("Attendance", true);
			activemenus.put("Holiday", true);						
		}else if(menutitle.equalsIgnoreCase("admin_hr_Attendance_Leave Approval"))
		{
			activemenus.put("Administration", true);
			activemenus.put("HR Management", true);
			activemenus.put("Attendance", true);
			activemenus.put("Leave Approval", true);						
		}else if(menutitle.equalsIgnoreCase("admin_hr_Attendance_Leave History"))
		{
			activemenus.put("Administration", true);
			activemenus.put("HR Management", true);
			activemenus.put("Attendance", true);
			activemenus.put("Leave History", true);						
		}else if(menutitle.equalsIgnoreCase("admin_hr_Payroll"))
		{
			activemenus.put("Administration", true);
			activemenus.put("HR Management", true);
			activemenus.put("Payroll", true);
					
		}else if(menutitle.equalsIgnoreCase("admin_AssetManagement"))
		{
			activemenus.put("Administration", true);
			activemenus.put("AssetManagement", true);
			
					
		}else if(menutitle.equalsIgnoreCase("contact_People"))
		{
			activemenus.put("Contact", true);
			activemenus.put("People", true);
			
					
		}else if(menutitle.equalsIgnoreCase("contact_Organization"))
		{
			activemenus.put("Contact", true);
			activemenus.put("Organization", true);
			
					
		}else if(menutitle.equalsIgnoreCase("lead"))
		{
			activemenus.put("WorkItem", true);
			activemenus.put("lead", true);
			
					
		}else if(menutitle.equalsIgnoreCase("deal"))
		{
			activemenus.put("WorkItem", true);
			activemenus.put("deal", true);
			
					
		}else if(menutitle.equalsIgnoreCase("project"))
		{
			activemenus.put("WorkItem", true);
			activemenus.put("project", true);
			
					
		}else if(menutitle.equalsIgnoreCase("projecttemplatelist"))
		{
			activemenus.put("WorkItem", true);
			activemenus.put("project", true);
			
					
		}else if(menutitle.equalsIgnoreCase("Insurance"))
		{
			activemenus.put("Administration", true);
			activemenus.put("HR Management", true);
			activemenus.put("Insurance", true);
		}else if(menutitle.equalsIgnoreCase("Invoice"))
		{
			activemenus.put("Invoice", true);
			
		}
		
		
		
		
		
		return activemenus;
	}
	
}
