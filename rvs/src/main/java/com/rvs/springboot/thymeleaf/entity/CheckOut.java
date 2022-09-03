package com.rvs.springboot.thymeleaf.entity;

import java.util.Set;

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
@Table(name = "checkout")

public class CheckOut {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int CheckOutID;
	@Column
	private String BranchID;
	@Column
	private String StaffID;
	@Column
	private String CheckOutDate;
	@Column
	private String vendor;
	@Column
	private String Status;
	@Column
	private String WhichLocation;
	@Column
	private String AssetId;
	@Column
	private String acondition;
	@Column
	private String Comments;
	@Column
	private String sysdate;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "CheckOutID")
	private Set<CheckOutFiles> checkoutFiles;
}
