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
@Table(name = "InsurancePolicyCover")

public class InsurancePolicyCover {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int InsurancePolicyCoverid;

	
	@Column
	private String Cover;
	@Column
	private String PFrom;
	@Column
	private String PTo;
	@Column
	private String CoverageAmount;
	@Column
	private String PremiumAmount;
	@Transient
	private String PFrom_str;
	@Transient
	private String PTo_str;
	
	@Transient
	private String dueindicatorcolor;
	@Transient
	private String duedateformate;
	

}
