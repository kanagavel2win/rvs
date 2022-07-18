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
@Table(name = "InsuranceMaster")

public class InsuranceMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Insuranceid;

	@Column
	private String VendorName;
	@Column
	private String InsuranceTo;
	@Column
	private String StaffID;
	@Column
	private String AssetNameID;
	@Column
	private String PolicyName;
	@Column
	private String PolicyNo;
	@Column
	private String PFrom;
	@Column
	private String PTo;
	@Column
	private String Premium;
	@Column
	private String GST;
	@Column
	private String Total;
	@Column
	private String PACover;
	@Column
	private String HospitalCover;
	@Column
	private String InsuredValue;
	@Column
	private String IDV;
	@Column
	private String NCB;
	@Column
	private String Notes;
	@Column
	private String Nominee;

}
