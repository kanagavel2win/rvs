package com.rvs.springboot.thymeleaf.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor  @AllArgsConstructor @ToString
@Table(name="BranchMaster")

public class BranchMaster {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column
	private String BRANCH_NAME;
	@Column
	private String BranchCode;
	@Column
	private String B_TYPE;
	@Column
	private String COMES_UNDER;
	@Column
	private String BRANCH_ADDRESSLINE1;
	@Column
	private String BRANCH_ADDRESSLINE2;
	@Column
	private String BRANCH_ADDRESSLandMark;
	@Column
	private String BRANCH_ADDRESSVillage;

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
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private Set<BranchContact> branchContact;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private Set<BranchFiles> branchFiles;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "branchid")
	private List<BranchAccNo> branchAccNo;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "branchid")
	private List<BranchEffective> branchEffective;
	
	
	@Transient
	private String BRANCH_IN_CHARGE_img;
	@Transient
	private String BRANCH_IN_CHARGE_name;	
	@Transient
	private String startdateMMformat;
	@Transient
	private String startdatatimeline;
	@Transient
	private String BRANCH_Type_2w;
	@Transient
	private String COMES_UNDER_name;
	
}
