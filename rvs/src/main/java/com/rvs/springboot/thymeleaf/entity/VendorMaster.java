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
@Table(name = "vendormaster")
public class VendorMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int vendormasterid;
	@Column
	private String Name;
	@Column
	private String AssetType;
	
	@Column
	private String Address_Street1;
	@Column
	private String Address_Street2;
	@Column
	private String Address_Village;
	@Column
	private String Address_Taluk;
	@Column
	private String Address_City;
	@Column
	private String Address_State;
	@Column
	private String Address_ZIP;
	@Column
	private String Address_Country;
	
	@Column
	private String GST;
	@Column
	private String TAN;
	@Column
	private String PAN;
	
	@Column
	private String Website;
	
	@Column
	private String BankACNo;
	@Column
	private String BankName;
	@Column
	private String BranchName;
	@Column
	private String BankIFSCCode;

	@Column
	private String Notes;

	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "vendormasterid")
	private List<VendorEmgContact> vendorEmgContact;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "vendormasterid")
	private List<VendorFiles> vendorFiles;

	
}
