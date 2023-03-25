package com.rvs.springboot.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/docs")
public class DocsContoller {

	private static String parentpath ="docs/";
	
	@GetMapping("branch")
	public String brancgdoc(Model themodel)
	{
		return parentpath+"branch";
	}
	
	@GetMapping("add")
	public String adddoc(Model themodel)
	{
		return parentpath+"branch";
	}
}
