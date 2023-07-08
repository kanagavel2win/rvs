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
@Table(name = "BranchpurchaseMaster")

public class BranchpurchaseMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int branchpurchaseid;

	@Column
	private String branchpurchaseNo;
	@Column
	private String branchpurchaseDate;
	@Column
	private String dueDate;
	@Column
	private String branchpurchaseType;
	@Column
	private String receivable;
	
	@Column
	private String employeeid;
	@Column
	private String supplierid;
	@Column
	private String notes;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "branchpurchaseid")
	@OrderBy("branchpurchaseitemid ASC")
	private List<BranchpurchaseItemMaster> branchpurchaseItemMasterlist;

	@Transient
	private String branchpurchaseDateMMMddyyyy;
	@Transient
	private String dueDateMMMddyyyy;

}
