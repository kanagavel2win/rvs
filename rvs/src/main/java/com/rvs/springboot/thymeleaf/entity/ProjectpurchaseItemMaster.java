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
@Table(name = "ProjectpurchaseItemMaster")

public class ProjectpurchaseItemMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int projectpurchaseitemid;
	
	@Column
	private String ProjectpurchaseItem;
	@Column
	private String Description;
	@Column
	private double Quantity;
	@Column
	private String Unit;
	@Column
	private double Price;
	@Column
	private double Discountper;
	@Column
	private double Discountamt;
	@Column
	private double CGSTper;
	@Column
	private double CGSTamount;
	@Column
	private double SGSTper;
	@Column
	private double SGSTamount;
	@Column
	private double IGSTper;
	@Column
	private double IGSTamount;
	@Column
	private double TaxableAmount;
	
	@Column
	private double totalamountAmount;
	
	@Transient
	private String projectpurchaseItem_name;
}
