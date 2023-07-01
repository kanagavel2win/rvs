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
@Table(name = "InvoiceReceiptMaster")

public class InvoiceReceiptMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int recepitid;
	
	@Column
	private String recepitNo;
	@Column
	private String recepitDate;
	@Column
	private String invoiceid;
	@Column
	private String depositedto;
	@Column
	private String modeofPayment;
	@Column
	private double amount;
	@Column
	private String notes;
	@Transient
	private String recepitDateMMMddyyyy;
	
	@Transient
	private String invoiceNo;
	
}
