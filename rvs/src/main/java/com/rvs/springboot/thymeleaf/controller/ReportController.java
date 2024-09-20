package com.rvs.springboot.thymeleaf.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.rvs.springboot.thymeleaf.service.Reports;
import com.rvs.springboot.thymeleaf.pojo.menuactivelist;
import com.rvs.springboot.thymeleaf.dao.CheckInMasterRepository;

@Controller
public class ReportController {
	@Autowired
	Reports report;
	@Autowired
	menuactivelist menuactivelistobj;
	@Autowired
	CheckInMasterRepository checkinMasterRepository;


	
	@GetMapping("reportprojectplan")
    public String reportprojectplan(Model themodel,HttpServletRequest request,HttpSession session) {
		 themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist(""));	 
		 List<Map<String,Object>> data = report.getallData();
		 themodel.addAttribute("data", data);
		 return "projecrtplan_report";
    }


	@GetMapping("reprintcheckin")
	public String reprintcheckin(Model themodel,HttpServletRequest request,HttpSession session,@RequestParam(name = "id") int id) {
		return "reprintcheckin";
	}

}
