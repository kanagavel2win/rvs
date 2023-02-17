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
@Table(name = "OrganizationContacts")
public class OrganizationContacts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private int branchid;
	@Column
	private String orgname;	
	@Column
	private String customer_supplier;
	@Column
	private String website;
	@Column
	private String industry_type;
	@Column
	private String followers;
	
	@Column
	private String addressStreet1;
	@Column
	private String addressStreet2;
	@Column
	private String addressLankmark;
	@Column
	private String addressVillage;
	@Column
	private String addressCity;
	@Column
	private String addressState;
	@Column
	private String addressZIP;
	@Column
	private String addressCountry;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private List<OrganizationContact> organizationContact;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private List<OrganizationFiles> organizationFiles;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private List<OrganizationAccNo> organizationAccNo;
	
	@Transient
	private String branchName;
	@Transient
	private String branchCode;
	@Transient
	private String followername;
	@Transient
	private String followerprimarymob;
	@Transient
	private String followerimg;
	
	@Transient
	private List<ContactPerson> cplist;
	
}
