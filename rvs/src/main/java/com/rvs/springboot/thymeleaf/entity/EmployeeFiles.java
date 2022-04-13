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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="employeefiles")

public class EmployeeFiles {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int empFileid;

	@Column  
	private String Photo_Attach;
	@Column  
	private String Resume_Attach;
	@Column  
	private String Certificates_Attach;
	

}
