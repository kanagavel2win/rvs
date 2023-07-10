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
@Table(name = "BranchexpenseMaster")

public class BranchexpenseMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int branchexpenseid;

	@Column
	private String branchexpenseNo;
	@Column
	private String branchexpenseDate;
	@Column
	private String employeeid;
	@Column
	private String supplierid;
	@Column
	private String depitedfrom;
	@Column
	private String modeofPayment;
	@Column
	private String notes;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "branchexpenseid")
	@OrderBy("branchexpenseitemid ASC")
	private List<BranchexpenseItemMaster> branchexpenseItemMasterlist;

	@Transient
	private String branchexpenseDateMMMddyyyy;
	
	@Transient
	private String employee_name;
	
	@Transient
	private String supplier_name;
	


}
