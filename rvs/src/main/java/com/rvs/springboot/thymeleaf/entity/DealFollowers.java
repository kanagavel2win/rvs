package com.rvs.springboot.thymeleaf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor  @AllArgsConstructor @ToString
@Table(name="dealFollowers")

public class DealFollowers {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int dealfollowerid;

	@Column
	private int empid;

	@Transient
	private String Followername;
	
	@Transient
	private String Followerimg;
	
	}
