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
import javax.persistence.OrderBy;
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
@Table(name = "dealmaster")
public class DealMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	//@Column
	//private String ContactPerson;
	@Column
	private String Organization;
	@Column
	private String Title;
	@Column
	private String Source;
	@Column
	private String Reference;
	@Column
	private String purpose;
	@Column
	private String expectedclosingdate;
	@Column
	private String pipeline;
	@Column
	private String notes;
	
	@Column
	int branch;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private List<DealContact> dealContact;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private List<DealFollowers> dealFollowers;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	@OrderBy("Fileid ASC")
	private List<DealFiles> dealFiles;
	
	
	@Column
	private String addressline1;
	@Column
	private String addressline2;
	@Column
	private String lankmark;
	@Column
	private String taluk;
	@Column
	private String district;
	@Column
	private String state;
	@Column
	private String pincode;	
	@Column
	private String lanlong;
	
	@Column
	private String createddate;
	@Column
	private String lossbacktoleadreason;
	@Column
	private int leadid;
	@Column
	private int dealvalue;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "dealId")
	@OrderBy("dealprojectid ASC")
	private List<DealProjectMaster> dealProjectMaster;
	
	
	@Transient
	private String nextactivity;
	@Transient
	private String OrganizationName;
	@Transient
	private String ContactPersonName;
	@Transient
	private String ReferenceName;
	@Transient
	private String createddateMMddYYY;
	@Transient
	private String expectedclosingdateMMddYYY;
	@Transient
	private String branchname;
	@Transient
	private String dealfollowerids;
	
}
