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
@Table(name = "employeejobempstatus")
public class EmployeeJobempstatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	int employeejobempstatusid;
	@Column
	String empstatus_employmentstatus;
	@Column
	String empstatus_terminationtype;
	@Column
	String empstatus_terminationreason;
	@Column
	String empstatus_rehire;
	@Column
	String empstatus_effectivedate;
	@Column
	String empstatus_remarks;
	@Column
	int employeeid;
}
