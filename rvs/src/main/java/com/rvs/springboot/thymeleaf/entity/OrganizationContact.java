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
@Getter @Setter @NoArgsConstructor  @AllArgsConstructor @ToString
@Table(name="OrganizationContact")

public class OrganizationContact {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int contactid;
	@Column
	private int branchid;
	@Column
	private String Department;
	@Column
	private String Phonenumber;
	@Column
	private String Email;
	@Column(columnDefinition = "boolean default false")	
	private Boolean primarycontact;	
	
	}
