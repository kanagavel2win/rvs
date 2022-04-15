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
@Table(name = "employeejobinfo")
public class EmployeeJobinfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int employeejobinfoid;
	@Column
	String jobeffectivedate;
	@Column
	String joblocation;
	@Column
	String jobdeparment;
	@Column
	String jobtitle;
	@Column
	String jobreportsto;
	@Column
	int employeeid;
}
