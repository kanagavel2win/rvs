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
@Table(name="leavemaster")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class LeaveMaster {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	@Column
	int empid;
	@Column
	String fromadate;
	@Column
	String todate;
	@Column
	String leavetype;
	@Column
	String notes;
	@Column
	String status;
	
}
