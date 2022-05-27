package com.rvs.springboot.thymeleaf.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rvs.springboot.thymeleaf.entity.EmployeeMaster;
import com.rvs.springboot.thymeleaf.entity.LeaveMaster;
import com.rvs.springboot.thymeleaf.service.EmployeeMasterService;
import com.rvs.springboot.thymeleaf.service.LeaveMasterService;

@Controller
@RequestMapping("/rvsemp")
public class EmployeeController {

	@Autowired
	HomeController homeController;
	
	@Autowired
	LeaveMasterService leaveMasterService;
	
	@Autowired
	EmployeeMasterService employeeMasterService;
	
	@ModelAttribute
	public void addAttributes(Model themodel, HttpSession session, HttpServletRequest request) {
		homeController.addAttributes(themodel, session, request);
	}
	
	@GetMapping("index")
	public String index(Model themodel) {
		return "employee/index";

	}
	
	@GetMapping
	public String index1(Model themodel) {
		return "employee/index";

	}
	@GetMapping("leaverequest")
	public String leaverequest(Model theModel ,  HttpSession session, HttpServletRequest request) {
		int empid=Integer.parseInt(request.getSession().getAttribute("dataLoginEmpID").toString());
		LeaveMaster leavemaster = new LeaveMaster();
		List<LeaveMaster> leaveMasterlist = leaveMasterService.findByEmpid(empid);
		Collections.sort(leaveMasterlist, Collections.reverseOrder());
		
		theModel.addAttribute("leavemaster", leavemaster);
		theModel.addAttribute("leaveMasterlist", leaveMasterlist);
		return "employee/leaverequest";
	}

	@PostMapping("leaverequest")
	public String leaverequestsave(Model theModel, @ModelAttribute("leavemaster") LeaveMaster leaveMasterobj,  HttpSession session, HttpServletRequest request) {

		int empid=Integer.parseInt(request.getSession().getAttribute("dataLoginEmpID").toString());
		leaveMasterobj.setStatus("Pending");
		leaveMasterobj.setEmpid(empid);
		LeaveMaster leaveMasterobjtemp = leaveMasterService.save(leaveMasterobj);
		
		List<LeaveMaster> leaveMasterlist = leaveMasterService.findByEmpid(empid);
		Collections.sort(leaveMasterlist, Collections.reverseOrder());
		
		theModel.addAttribute("leavemaster", leaveMasterobjtemp);
		theModel.addAttribute("leaveMasterlist", leaveMasterlist);
		theModel.addAttribute("save", "save");
		return "employee/leaverequest";
	}
	
	@GetMapping("leavereqlist")
	public String leavereqlist(Model theModel ,  HttpSession session, HttpServletRequest request) {
		
		int empid=Integer.parseInt(request.getSession().getAttribute("dataLoginEmpID").toString());
		List<LeaveMaster> leaveMasterlist = leaveMasterService.findByEmpid(empid);
		Collections.sort(leaveMasterlist, Collections.reverseOrder());
		theModel.addAttribute("leaveMasterlist", leaveMasterlist);
		return "employee/leaverequesthistory";
	}


}
