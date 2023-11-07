package com.rvs.springboot.thymeleaf.pojo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class emppojoPrivillage {

	public static List<Integer> allowBranches;

	public emppojoPrivillage() {
		allowBranches = new ArrayList<>();	
		
	}
	
	
}
