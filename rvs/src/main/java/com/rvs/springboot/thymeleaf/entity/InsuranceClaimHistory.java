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
@Table(name = "InsuranceClaimHistory")

public class InsuranceClaimHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int insuranceClaimid;

	@Column
	private String policyNo;
	@Column
	private String claimDate;
	@Column
	private String claimAmount;
	@Column
	private String reason;

	@Transient
	private String claimDateMMMddYYYFormate;
}
