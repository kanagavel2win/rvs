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
public class CalenderFormat {
	
	private int id;
	
	private String title;
	
	private String start;
	
	private String end;
	
	private Boolean allDay;

	private HashMap<String,String> extendedProps;
}
