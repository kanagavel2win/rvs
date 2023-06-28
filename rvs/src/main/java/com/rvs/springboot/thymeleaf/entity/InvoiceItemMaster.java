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
@Table(name = "InvoiceItemMaster")

public class InvoiceItemMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int invoiceitemid;
	
	@Column
	private String InvoiceItem;
	@Column
	private String Description;
	@Column
	private long Quantity;
	@Column
	private String Unit;
	@Column
	private long Price;
	@Column
	private long Discountper;
	@Column
	private long Discountamt;
	@Column
	private long CGSTper;
	@Column
	private long CGSTamount;
	@Column
	private long SGSTper;
	@Column
	private long SGSTamount;
	@Column
	private long IGSTper;
	@Column
	private long IGSTamount;
	@Column
	private long TaxableAmount;
	
	
}
