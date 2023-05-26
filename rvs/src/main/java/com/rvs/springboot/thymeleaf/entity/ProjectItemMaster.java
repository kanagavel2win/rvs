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
@Table(name = "projectItemmaster")
public class ProjectItemMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int projectitemid;
	@Column
	private String projecttype;
	@Column
	private String Quantity;
	@Column
	private String Unit;
	@Column
	private int Taxpercentage;
	@Column
	private int Taxamt;
	@Column
	private int Discountpercentage;
	@Column
	private int Discountvalue;
	
	@Column
	private String Price;
	@Column
	private String Amount;
	
}
