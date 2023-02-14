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
@Table(name="OrganizationAccNo")

public class OrganizationAccNo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int Accnoid;
	@Column
	private String acno;
	@Column
	private String acname;
	@Column
	private String bankname;
	@Column
	private String branchname;
	@Column
	private String ifsccode;
	}
