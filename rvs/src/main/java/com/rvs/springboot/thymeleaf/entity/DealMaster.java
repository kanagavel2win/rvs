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
@Table(name = "dealmaster")
public class DealMaster {

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
	private String purpose;
	@Column
	private String expectedclosingdate;
	@Column
	private String pipeline;
	@Column
	
	private String notes;
	@Column
	private String follower;
	@Column
	
	private String state;
	@Column
	
	private String district;
	@Column
	
	private String taluk;
	@Column
	
	private String village;
	@Column
	
	private String lankmark;
	@Column
	
	private String lanlong;
	@Column
	private String createddate;
	@Column
	private String lossbacktoleadreason;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "activityId")
	private List<ActivityMasterGuest> activityMasterGuest;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "activityId")
	private List<DealProjectMaster> dealProjectMaster;
	
	
}
