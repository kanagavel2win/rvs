package com.rvs.springboot.thymeleaf.entity;

import java.util.Date;
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
import javax.persistence.Transient;

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
@Table(name = "activitymaster")

public class ActivityMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int activityId;
	@Column
	private String mastercategory;
	@Column
	private String mastercategoryid;
	@Column
	private String activitycategory;
	@Column
	private String activitytitle;
	@Column
	private String activitytype;
	@Column
	private String duedate;
	@Column
	private String startdate;
	@Column
	private String starttime;
	@Column
	private String enddate;
	@Column
	private String endtime;
	@Column
	private String location;
	@Column
	private String description;
	@Column
	private String notes;
	
	@Column(length = 65555)
	private String htmlnotes;
	@Column
	private String activityfollowers;	
	@Column
	private String status;	
	@Column
	private String createdtime;
		
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "activityId")
	private List<ActivityMasterGuest> activityMasterGuest;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "activityId")
	private List<ActivityMasterTeam> activityMasterTeam;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "activityId")
	private List<ActivityMasterFiles> activityMasterFiles;

	
	@Transient
	private String followerimg;
	
	@Transient
	private String startdatestrformate;
	
	@Transient
	private Date startdatestrformateorginial;
	
	@Transient
	private String activityMasterTeamimg;
	
	
}
