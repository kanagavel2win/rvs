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
import org.springframework.web.bind.annotation.ResponseBody;
import com.rvs.springboot.thymeleaf.service.Reports;
import com.rvs.springboot.thymeleaf.pojo.menuactivelist;

@Controller
public class ReportController {
	@Autowired
	Reports report;
	@Autowired
	menuactivelist menuactivelistobj;
	
	@GetMapping("reportprojectplan")
    public String blog(Model themodel,HttpServletRequest request,HttpSession session) {
		 themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist(""));
		 themodel.addAttribute("dataLoginEmpprofiileimg",request.getSession().getAttribute("dataLoginEmpprofiileimg"));
		 themodel.addAttribute("dataLoginEmpID",request.getSession().getAttribute("dataLoginEmpID"));
		 themodel.addAttribute("dataLoginEmpName",request.getSession().getAttribute("dataLoginEmpName"));
		 themodel.addAttribute("dataLoginrole",request.getSession().getAttribute("dataLoginrole"));
		 
		 List<Map<String,Object>> data = report.getallData();
		 themodel.addAttribute("data", data);
		 return "projecrtplan_report";
    }
}
