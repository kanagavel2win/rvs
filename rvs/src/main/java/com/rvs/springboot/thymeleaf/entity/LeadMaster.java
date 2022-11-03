package com.rvs.springboot.thymeleaf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Value;

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
@Table(name = "leadmaster")
public class LeadMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String ContactPerson;
	@Column
	private String Organization;
	@Column
	private String Title;
	@Column
	private String Source;
	@Column
	private String Reference;
	@Column
	private String Label;
	@Column
	private String notes;
	@Column
	private String follower;
	@Column
	private String createddate;
	@Column
	private boolean movedtolead;
	
	@Column
	private boolean backfromdeal;
}
