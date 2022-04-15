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
@Table(name = "employeejobcompensation")
public class EmployeeJobcompensation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int employeejobcompensationid;
	@Column
	String comeffectivedate;
	@Column
	String comPayschedule;
	@Column
	String compaytype;
	@Column
	String compayrate;
	@Column
	String compayratetype;
	@Column
	String comchangereason;
	@Column
	String comcomments;
	@Column
	int employeeid;
}
