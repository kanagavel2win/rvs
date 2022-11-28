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
@Table(name = "projectdetailsmaster")
public class ProjectdetailsMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int projectdetailid;
	@Column
	private String projecttype;
	@Column
	private String Quantity;
	@Column
	private String Unit;
	@Column
	private String Price;
	@Column
	private String Amount;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "projectdetailid")
	private List<ProjectTaskMaster> projecttaskMaster;
	
}
