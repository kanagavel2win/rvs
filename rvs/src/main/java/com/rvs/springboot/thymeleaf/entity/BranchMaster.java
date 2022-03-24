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

@Entity
@Getter @Setter @NoArgsConstructor  @AllArgsConstructor
@Table(name="BranchMaster")

public class BranchMaster {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column
	private String BRANCH_NAME;
	@Column
	private String B_TYPE;
	@Column
	private String COMES_UNDER;
	@Column
	private String BRANCH_ADDRESS;
	@Column
	private String CITY;
	@Column
	private String STATE;
	@Column
	private String COUNTRY;
	@Column
	private String ZIP_CODE;
	@Column
	private String GST;
	@Column
	private String TIN;
	@Column
	private String PAN;
	@Column
	private String BRANCH_IN_CHARGE;
	@Column
	private String IN_CHARGE_CONTACT_DETAILS;
	@Column
	private String OFFICE_PHONE_NUMBER;
	@Column
	private String BRANCH_OFFICE_EMAIL_ID;
	@Column
	private String STATED_DATE;
	@Column
	private String CURRENT_STATUS;
	@Column
	private String CLOSED_DATE;
	
}
