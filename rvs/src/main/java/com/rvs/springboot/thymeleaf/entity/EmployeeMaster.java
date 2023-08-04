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
	private String ShoeSize;
	@Column
	private String PantSize;
	@Column
	private String Address_Street1;
	@Column
	private String Address_Street2;
	@Column
	private String Address_Landmark;
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
	private String RecruitmentSource;
	@Column
	private String RecruInstituteConsultantName;
	
	
	@Column
	private String Notes;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "empMasterid")
	@OrderBy("FromYear ASC")
	private List<EmployeeEducation> employeeEducation;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "empMasterid")
	@OrderBy("empEmgContactid ASC")
	private List<EmployeeEmgContact> employeeEmgContact;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "empMasterid")
	@OrderBy("empFileid ASC")
	private List<EmployeeFiles> employeeFiles;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "empMasterid")
	@OrderBy("empLanguid ASC")
	private List<EmployeeLanguage> employeeLanguage;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "empMasterid")
	@OrderBy("expFromyear ASC")
	private List<EmployeeExperience> employeeExperience;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "empMasterid")
	@OrderBy("employeecontactid ASC")
	private List<EmployeeContact> employeeContact;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "empMasterid")
	private List<EmployeeAccNo> employeeAccNo;
	
	@Transient
	private String dobMMformat;
	
	@Transient
	private String t_emp_img;
	@Transient
	private String t_primary_contactno;
	@Transient
	private String t_primary_email;
	@Transient
	private String t_branch_name;
	@Transient
	private String t_branch_id;
	@Transient
	private String t_position;
	@Transient
	private String t_salary;
	@Transient
	private String t_joindateMMformat;
	@Transient
	private String t_joindatetimeline;
	@Transient
	private String t_currentStatus;
}
