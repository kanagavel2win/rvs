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
@Table(name = "InvoiceMaster")

public class InvoiceMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int invoiceid;
	
	@Column
	private String invoiceNo;
	@Column
	private String invoiceDate;
	@Column
	private String dueDate;
	@Column
	private String dueType;
	@Transient
	private String invoiceDateMMMddyyyy;
	@Transient
	private String dueDateMMMddyyyy;
	
	@Column
	private String invoiceType;
	@Column
	private String receivable;
	@Column
	private String GSTCode;
	@Column
	private String rvsaddress;
	@Column
	private String invoiceaddressline1;
	@Column
	private String invoiceaddressline2;
	@Column
	private String invoiceaddresscity;
	@Column
	private String invoiceaddressState;
	@Column
	private String invoiceaddresspincode;
	@Column
	private String invoiceMobileno;
	@Column
	private String invoiceEmail;
	@Column
	private String invoiceGSTNo;
	@Column
	private String billaddressline1;
	@Column
	private String billaddressline2;
	@Column
	private String billcity;
	@Column
	private String billstate;
	@Column
	private String billpincode;
	@Column
	private String billMobileno;
	@Column
	private String billEmail;
	@Column
	private String billGSTNo;
	
	@Column
	private String notes;
	
	
	@OneToMany(cascade =  CascadeType.ALL)
	@JoinColumn(name ="invoiceid")
	@OrderBy("invoiceitemid ASC")
	private List<InvoiceItemMaster> invoiceItemMasterlist;
	
}
