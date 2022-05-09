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
@Table(name ="holiday")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Holiday {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column
	private String title;
	@Column
	private String start;
	@Column
	private String end;
	@Column
	private Boolean allDay;
	@Column
	private String backgroundColor;
	@Column
	private String borderColor;
	@Column
	private String color;
	@Column
	private String description;
	
	
}
