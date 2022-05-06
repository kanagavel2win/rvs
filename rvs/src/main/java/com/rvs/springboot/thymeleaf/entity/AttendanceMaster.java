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
@Getter @Setter @NoArgsConstructor  @AllArgsConstructor @ToString
@Table(name="attendancemaster")
public class AttendanceMaster {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int attendancemasterid;
	
	@Column
	private String attendanceDate;
		
	@Column
	private int branchMasterid;
		
	@Column
	private int employeeid;
	
	@Column
	private String notes;
	
	@Column
	private String attstatus;

}
