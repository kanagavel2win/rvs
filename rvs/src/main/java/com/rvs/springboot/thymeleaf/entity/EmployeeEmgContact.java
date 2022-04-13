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
@Table(name="employeeemgcontact")
public class EmployeeEmgContact {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int empEmgContactid;

	@Column  
	private Boolean Emg_primarycontact;
	@Column  
	private Boolean Emg_InsuranceNominee;
	@Column  
	private String Emg_Name;
	@Column  
	private String Emg_Relation;
	@Column  
	private String Emg_PersonalPhone;
	@Column  
	private String Emg_OtherPhone;
	@Column  
	private String Emg_EmailID;
	@Column  
	private String Emg_Street1;
	@Column  
	private String Emg_Street2;
	@Column  
	private String Emg_Village;
	@Column  
	private String Emg_Taluk;
	@Column  
	private String Emg_City;
	@Column  
	private String Emg_State;
	@Column  
	private String Emg_ZIP;
	@Column  
	private String Emg_Country;

}
