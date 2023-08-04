package com.rvs.springboot.thymeleaf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name="employeefiles")

public class EmployeeFiles {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int empFileid;

	@Column
	private String DocumentType;
	@Column
	private String DocumentNo;
	@Column
	private String FilePath;	
	@Transient
	private String FilePath_trim;

}
