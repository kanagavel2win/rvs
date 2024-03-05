package com.rvs.springboot.thymeleaf.pojo;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
		
		
		switch (menutitle)
		{
		case "index":
		
			activemenus.put("index", true);
		break;	
		case "admin_hr_employeelist":
		
			activemenus.put("Administration", true);
			activemenus.put("HR Management", true);
			activemenus.put("Employee", true);
			break;	
		case "admin_branchlist":
		
			
			activemenus.put("Administration", true);
			activemenus.put("Branches", true);
			break;
			
		case "admin_hr_Hire":
		
			activemenus.put("Administration", true);
			activemenus.put("HR Management", true);
			activemenus.put("Hire", true);
			break;
		case "admin_hr_Insurance":
		
			activemenus.put("Administration", true);
			activemenus.put("HR Management", true);
			activemenus.put("Insurance", true);
			break;
		case "admin_hr_Attendance_Daily Attendance":
		
			activemenus.put("Administration", true);
			activemenus.put("HR Management", true);
			activemenus.put("Attendance", true);
			activemenus.put("Daily Attendance", true);	
			break;
		case "admin_hr_Attendance_Attendance Report":
		
			activemenus.put("Administration", true);
			activemenus.put("HR Management", true);
			activemenus.put("Attendance", true);
			activemenus.put("Attendance Report", true);	
			break;
		case "admin_hr_Attendance_Holiday":
		
			activemenus.put("Administration", true);
			activemenus.put("HR Management", true);
			activemenus.put("Attendance", true);
			activemenus.put("Holiday", true);						
		case "admin_hr_Attendance_Leave Approval":
		
			activemenus.put("Administration", true);
			activemenus.put("HR Management", true);
			activemenus.put("Attendance", true);
			activemenus.put("Leave Approval", true);						
		case "admin_hr_Attendance_Leave History":
		
			activemenus.put("Administration", true);
			activemenus.put("HR Management", true);
			activemenus.put("Attendance", true);
			activemenus.put("Leave History", true);
			break;
		case "admin_hr_Payroll":
		
			activemenus.put("Administration", true);
			activemenus.put("HR Management", true);
			activemenus.put("Payroll", true);
			break;
		case "admin_AssetManagement" :
		
			activemenus.put("Administration", true);
			activemenus.put("AssetManagement", true);
			break;
					
		case "contact_People":
		
			activemenus.put("Contact", true);
			activemenus.put("People", true);
			break;
					
		case "contact_Organization":
		
			activemenus.put("Contact", true);
			activemenus.put("Organization", true);
			break;
					
		case "lead":
		
			activemenus.put("WorkItem", true);
			activemenus.put("lead", true);
			break;	
					
		case "deal":
		
			activemenus.put("WorkItem", true);
			activemenus.put("deal", true);
			break;	
					
		case "project":
		
			activemenus.put("WorkItem", true);
			activemenus.put("project", true);
			break;	
					
		case "projectplan":
		
			activemenus.put("WorkItem", true);
			activemenus.put("projectplan", true);
			break;	
					
		case "projecttemplatelist":
		
			activemenus.put("WorkItem", true);
			activemenus.put("project", true);
			break;
					
		case "Insurance":
		
			activemenus.put("Administration", true);
			activemenus.put("HR Management", true);
			activemenus.put("Insurance", true);
			break;
		case "Invoice":
		
			activemenus.put("Invoice", true);
		break;
		case "AccountTransfer":
		
			activemenus.put("AccountTransfer", true);
		break;	
		case "Income":
		
			activemenus.put("Income", true);
		break;	
		case "Asset List":
		
			activemenus.put("Administration", true);
			activemenus.put("AssetManagement", true);
			activemenus.put("Asset List", true);
			break;
			
		case "Check Out":
		
			activemenus.put("Administration", true);
			activemenus.put("AssetManagement", true);
			activemenus.put("Check Out", true);
			break;	
		case "Check In":
		
			activemenus.put("Administration", true);
			activemenus.put("AssetManagement", true);
			activemenus.put("Check In", true);
			break;
		case "assetaudit":
		
			activemenus.put("Administration", true);
			activemenus.put("AssetManagement", true);
			activemenus.put("assetaudit", true);
			break;
		case "accountsMain":
		
			activemenus.put("Accounts", true);
			activemenus.put("accountsMain", true);
			break;
		case "PendingPayments": 
		
			activemenus.put("Accounts", true);
			activemenus.put("PendingPayments", true);
		
			break;
			
		case "accountInvoicels" :
			activemenus.put("Accounts", true);
			activemenus.put("accInvoice", true);
			
			break;
		}
			
		return activemenus;
	}
	
}
