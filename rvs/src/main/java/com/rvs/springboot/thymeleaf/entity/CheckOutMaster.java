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
@Table(name = "checkoutmaster")

public class CheckOutMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int CheckOutMasterID;
	@Column
	private String BranchID;
	@Column
	private String StaffID;
	@Column
	private String CheckOutDate;
	@Column
	private String CheckOutDateTime;
	@Column
	private String sysdate;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "CheckOutMasterID")
	private List<CheckOut> checkout;
}