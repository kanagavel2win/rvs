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
@Table(name = "contactpeople")
public class ContactPerson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String peoplename;
	@Column
	private String organization;
	@Column
	private String phonework;
	@Column
	private String phonepersonal;
	@Column
	private String phoneothers;
	@Column
	private String emailwork;
	@Column
	private String emailpersonal;
	@Column
	private String emailothers;
	@Column
	private String memberin;
	@Column
	private String typeofindustry;
	@Column
	private String addressStreet1;
	@Column
	private String addressStreet2;
	@Column
	private String addressVillage;
	@Column
	private String addressTaluk;
	@Column
	private String addressCity;
	@Column
	private String addressState;
	@Column
	private String addressZIP;
	@Column
	private String addressCountry;
	@Column
	private String followers;

}
