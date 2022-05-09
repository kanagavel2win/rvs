package com.rvs.springboot.thymeleaf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rvs.springboot.thymeleaf.entity.Holiday;
import com.rvs.springboot.thymeleaf.service.HolidayService;

@RestController
public class EventController {

	@Autowired
	HolidayService holidayService;

	@GetMapping("/events")
	public List<Holiday> eventinformation() {

		return holidayService.findAll();
	}

}
