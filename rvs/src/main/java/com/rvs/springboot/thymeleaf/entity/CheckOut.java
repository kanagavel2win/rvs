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
@Table(name = "checkout")

public class CheckOut {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int CheckOutID;
	@Column
	private String StaffID;
	@Column
	private String CheckOutDate;
	@Column
	private String AssetId;
	@Column
	private String Status;
	@Column
	private String Location;
	@Column
	private String Comments;
	
	
	
}
