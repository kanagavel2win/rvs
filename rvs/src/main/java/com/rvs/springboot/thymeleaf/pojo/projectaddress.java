package com.rvs.springboot.thymeleaf.pojo;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter @Setter @ToString 
@AllArgsConstructor @NoArgsConstructor
public class projectaddress {

	private String addressline1;
	private String addressline2;
	private String district;
	private String state;
	private String pincode;	
	private String gst;
}
