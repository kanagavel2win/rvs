package com.rvs.springboot.thymeleaf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "employeeIncentive")
public class EmployeeIncentive {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int employeeIncentiveid;
	@Column
	String incentivedate;
	@Column
	double amount;
	@Column
	String comments;
	@Transient
	private String incentivedate_DDMMMYYYY;
	
}
