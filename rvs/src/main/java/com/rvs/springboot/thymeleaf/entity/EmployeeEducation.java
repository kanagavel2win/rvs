package com.rvs.springboot.thymeleaf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString 
@Table(name="EmployeeEducation")
public class EmployeeEducation {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int empEduid;
	
	@Column  
	private String College_Institution;
	@Column  
	private String Degree;
	@Column  
	private String MajorSpecialization;
	@Column  
	private String Percentage_GPA;
	@Column  
	private String FromYear;
	@Column  
	private String ToYear;

	
}
