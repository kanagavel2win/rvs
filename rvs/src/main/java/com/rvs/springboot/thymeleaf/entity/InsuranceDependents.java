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
@Table(name = "InsuranceDependents")

public class InsuranceDependents {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int insuranceDependentsid;

	@Column
	private String dependent_name;	
	@Column
	private String dob;
	@Column
	private String gender;
	@Column
	private String relationship;
	@Column
	private String IDNumber;
	@Column
	private String IDfiles;
	@Column
	private String phonenumber;
	
	@Transient
	private String dob_MMMddyyyy;
	

}
