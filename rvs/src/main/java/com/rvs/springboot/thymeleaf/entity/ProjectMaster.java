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
@Table(name = "projectmaster")
public class ProjectMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String Title;
	@Column
	private String startdate;
	@Column
	private String enddate;
	@Column
	private String pipeline;
	@Column
	private String followers;
	@Column
	private String label;
	@Column
	private String description;
	@Column
	private String branch;
	@Column
	private String assigntouser;
	@Column
	private String createddate;
	@Column
	private String dealid;
	@Column
	private String ContactPerson;
	@Column
	private String Organization;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "projectId")
	private List<ProjectdetailsMaster> projectdetailMaster;


	
	
	
	
}
