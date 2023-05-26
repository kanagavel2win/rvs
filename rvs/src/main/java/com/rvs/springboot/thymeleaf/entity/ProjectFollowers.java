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
@Table(name="projectFollowers")

public class ProjectFollowers {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int projectfollowerid;

	@Column
	private int empid;

	@Transient
	private String Followername;
	
	@Transient
	private String Followerimg;
	
	}
