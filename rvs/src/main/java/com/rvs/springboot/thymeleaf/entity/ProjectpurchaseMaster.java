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
@Table(name = "ProjectpurchaseMaster")

public class ProjectpurchaseMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int projectpurchaseid;

	@Column
	private String projectpurchaseNo;
	@Column
	private String projectpurchaseDate;
	@Column
	private String dueDate;
	@Column
	private String projectpurchaseType;
	@Column
	private String receivable;
	
	@Column
	private String employeeid;
	@Column
	private String supplierid;
	@Column
	private String notes;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "projectpurchaseid")
	@OrderBy("projectpurchaseitemid ASC")
	private List<ProjectpurchaseItemMaster> projectpurchaseItemMasterlist;

	@Transient
	private String projectpurchaseDateMMMddyyyy;
	@Transient
	private String dueDateMMMddyyyy;

}
