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
@Table(name = "employeeadvancerepayment")
public class EmployeeAdvanceRepayment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int employeerepaymentid;
	@Column
	String repaydate;
	@Column
	String payperiod;
	@Column
	double amount;
	@Column
	String paidto;
	@Column
	String modeofpayment;
	@Column
	String deductsource;
	@Column
	int employeeid;
	
}
