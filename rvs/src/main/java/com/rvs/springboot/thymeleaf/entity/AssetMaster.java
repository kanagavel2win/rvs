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
@Table(name = "assetmaster")

public class AssetMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int AssetId;
	
	@Column
	private String AssetName;
	@Column
	private String AssetNumber;
	@Column
	private String AssetType;
	@Column
	private String Manufacturer;
	@Column
	private String Brand;
	@Column
	private String Model;
	@Column
	private String SerialNumber;
	@Column
	private String ACondition;
	@Column
	private String Status;
	@Column
	private String Location;
	@Column
	private String Vendor;
	@Column
	private String PurchasePrice;
	@Column
	private String Purchased;
	@Column
	private String PurchaseOrderNo;
	@Column
	private String WarrantyEnd;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "AssetId")
	private Set<AssetMasterFiles> assetMasterFiles;
	
}
