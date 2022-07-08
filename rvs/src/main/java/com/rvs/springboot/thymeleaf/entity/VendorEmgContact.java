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
@Table(name="vendoremgcontact")
public class VendorEmgContact {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int venEmgContactid;

	@Column  
	private Boolean Emg_primarycontact;
	
	@Column  
	private String Emg_Name;
	@Column  
	private String Designation;
	@Column  
	private String Emg_PersonalPhone;
	@Column  
	private String Emg_OtherPhone;
	@Column  
	private String Emg_EmailID;
	@Column  
	private String Landlineno;
	
}
