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
public class donutchart {

	String labels;
	long series;
}
