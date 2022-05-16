package com.rvs.springboot.thymeleaf.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Setter @Getter @ToString @NoArgsConstructor @AllArgsConstructor

public class HireMaster {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	@Column
	String posttitle;
	@Column
	String 	job_status;
	@Column
	String hiring_lead;
	@Column
	String department;
	@Column
	String employment_type;
	@Column
	String minimumexperience;
	@Column
	String jobdescription;
	@Column
	String location;
	@Column
	String state;
	@Column
	String compensation;
	@Column
	Boolean resume;
	@Column
	Boolean address;
	@Column
	Boolean dateavailable;
	@Column
	Boolean desiredsalary;
	@Column
	String createddate;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	@OrderBy("questionid ASC")
	private Set<HireMasterQuestions> hireMasterQuestions = new HashSet<HireMasterQuestions>();

}
