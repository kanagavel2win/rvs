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
@Table(name = "ProjectExpense")

public class ProjectExpense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int prjExpenseid;
	
	@Column
	private String prjExpenseDate;
	@Column
	private String prjreceiptno;	
	@Column
	private String staff;
	@Column
	private String category;
	@Column
	private String modelofTravel;
	@Column
	private String vehicle;
	@Column
	private String Description;
	@Column
	private String unit;
	@Column
	private double Quantity;
	@Column
	private double Amount;
	@Column
	private double Total;
	@Column
	private String Notes;
	@Column
	private String modeofPayment;
	@Column
	private String depitedfrom;
	
	@Transient
	private String prjExpenseDateMMMddyyyy;
	@Transient
	private String staffname;
	@Transient
	private String vehilename;

	@Transient
	private String category_name;
}