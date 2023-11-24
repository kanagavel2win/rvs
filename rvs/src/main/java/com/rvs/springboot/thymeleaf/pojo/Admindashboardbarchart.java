package com.rvs.springboot.thymeleaf.pojo;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter @Setter @ToString 
@AllArgsConstructor @NoArgsConstructor
public class Admindashboardbarchart {

	List<Integer> receipt;
	List<Integer> expense;
	List<String> xaxis;
}
