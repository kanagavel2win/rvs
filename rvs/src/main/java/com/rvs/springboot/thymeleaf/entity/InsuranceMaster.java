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
@Table(name = "InsuranceMaster")

public class InsuranceMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Insuranceid;
	
	@Column
	private String InsuranceTo;
	@Column
	private String StaffID;
	@Column
	private String AssetNameID;	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "Insuranceid")
	private List<InsuranceDetails> InsuranceDetails;

	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "Insuranceid")
	private List<InsuranceClaimHistory> insuranceClaimHistory;
	
	@Transient
	private String staffassetname;
	
	
}
