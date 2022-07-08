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
@Table(name="assetmasterfiles")

public class AssetMasterFiles {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int assetFileid;

	@Column  
	private String Photo_Attach;
	
	@Column  
	private String Files_Attach;
	

}
