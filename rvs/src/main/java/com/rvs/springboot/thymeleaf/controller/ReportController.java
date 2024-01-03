package com.rvs.springboot.thymeleaf.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.rvs.springboot.thymeleaf.service.Reports;

@Controller
public class ReportController {
	@Autowired
	Reports report;
	
	@GetMapping("blog")
	@ResponseBody
    public List<String> blog() {
		return report.getData();
    }
	
	@GetMapping("blogall")
	@ResponseBody
    public List<Map<String,Object>> blogall() {
		return report.getallData();
    }
}
