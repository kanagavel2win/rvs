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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "projecttemplateactivitymaster")
public class ProjectTemplateActivityMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int projectactivityid;
	@Column
	private String activitytype;
	@Column
	private String activitytitle;
	@Column
	private String notes;
	@Column
	private int daysfromprojectstartdate;
	@Column
	private int activityorder;
	
	@Column
	private String activityfollowers;
	
	
	@Transient
	private String tempphaseid;
	
}
