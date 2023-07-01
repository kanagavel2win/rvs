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
@Table(name = "AccountsIncome")

public class AccountsIncome {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accountIncomeid;
	
	@Column
	private String ifrom;
	@Column
	private String idepositto;
	@Column
	private String refNo;
	@Column
	private String idate;
	@Column
	private String icategory;
	@Column
	private String idescription;
	@Column
	private String iamount;
	@Transient
	private String idateMMMddyyyy;
	
}
