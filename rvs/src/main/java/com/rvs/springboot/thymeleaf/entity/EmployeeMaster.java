package com.rvs.springboot.thymeleaf.entity;

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

import org.springframework.beans.factory.annotation.Value;

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
@Table(name = "employeemaster")
public class EmployeeMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int empMasterid;
	@Column
	private String Empid;
	@Column
	private String StaffName;
	@Column
	private String Gender;
	@Column
	private String MaritalStatus;
	@Column
	private String Father_HusbandName;
	@Column
	private String DateofBirth;
	@Column
	private String BloodGroup;
	@Column
	private String Height;
	@Column
	private String Weight;
	@Column
	private String AadharNo;
	@Column
	private String PANNo;
	@Column
	private String DLNo;
	@Column
	private String Accommodation;
	@Column
	private String ShirtSize;
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
	private String Contact_WorkPhone;
	@Column
	private String Contact_PersonalPhone;
	@Column
	private String Contact_OtherPhone;
	@Column
	private String Contact_EmailID;
	@Column
	private String Contact_WorkEmail;
	@Column
	private String Contact_PersonalEmail;
	@Column
	private String RecruitmentSource;
	@Column
	private String RecruInstituteConsultantName;
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
	@JoinColumn(name = "empMasterid")
	private Set<EmployeeEducation> employeeEducation;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "empMasterid")
	private Set<EmployeeEmgContact> employeeEmgContact;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "empMasterid")
	private Set<EmployeeFiles> employeeFiles;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "empMasterid")
	private Set<EmployeeLanguage> employeeLanguage;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "empMasterid")
	private Set<EmployeeExperience> employeeExperience;
}
