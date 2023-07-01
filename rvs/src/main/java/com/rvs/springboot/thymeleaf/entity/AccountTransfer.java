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
@Table(name = "AccountTransfer")

public class AccountTransfer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accounttransferid;
	
	@Column
	private String tDate;
	@Column
	private String tAmount;
	@Column
	private String twithdrawfrom;
	
	@Transient
	private String tDateMMMddyyyy;
	
	@Column
	private String tDepositTo;
	@Column
	private String Notes;

	
}
