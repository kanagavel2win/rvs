package com.rvs.springboot.thymeleaf.entity;

import java.util.List;

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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "InsuranceDetails")

public class InsuranceDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int InsuranceDetailsid;

	@Column
	private String VendorName;	
	@Column
	private String PolicyName;
	@Column
	private String PolicyNo;
	@Column
	private String Premium;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "InsuranceDetailsid")
	private List<InsurancePolicyCover> insurancePolicyCover;
	
	@Column
	private String NameofInsured;
	@Column
	private String companyPaysPerc;
	@Column
	private String employeePaysPerc;
	
	@Column
	private String Notes;
	@Column
	private String Nominee;
	@Column
	private String doc_Attach;
	@Column
	private String status;
	
	@Transient
	private String VendorNamestr;
	@Transient
	private String timestr;
		
	@Transient
	private String Nominee_name_str;
	
	

}
