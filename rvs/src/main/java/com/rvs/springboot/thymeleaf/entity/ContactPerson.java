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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "contactpeople")
public class ContactPerson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private int branchid;
	@Column
	private String peoplename;
	@Column
	private String organization;
	@Column
	private String customer_supplier;
	@Column
	private String designation;
	@Column
	private String memberin;
	@Column
	private String followers;
	@Column(columnDefinition = "boolean default false")	
	private Boolean primaryperson;	
	
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
	private List<ContactPersonContact> contactPersonContact;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private List<ContactPersonFiles> contactPersonFiles;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private List<ContactPersonAccNo> ContactPersonAccNo;
	
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
	private String primarymob;
	@Transient
	private String primaryemail;
	@Transient
	private String organizationname;	
}
