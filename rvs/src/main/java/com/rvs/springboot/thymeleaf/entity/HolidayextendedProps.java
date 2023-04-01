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
@Table(name = "holidayextendedProps")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HolidayextendedProps {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int holidayextendedPropsid;
	
	@Column
	private String type;
	@Column
	private String branch;
	@Column
	private String calendar;
	@Column
	private String description;
}
