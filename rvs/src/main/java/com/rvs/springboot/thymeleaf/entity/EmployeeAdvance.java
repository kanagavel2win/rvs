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
@Table(name = "employeeadvance")
public class EmployeeAdvance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int employeeadvanceid;
	@Column
	String advancedate;
	@Column
	String purpose;
	@Column
	double amount;
	@Column
	String paidfrom;
	@Column
	String modeofpayment;
	@Column
	String repaymentmonths;
	@Column
	String repaymentcomments;
	@Transient
	private String advancedate_DDMMMYYYY;
	
}
