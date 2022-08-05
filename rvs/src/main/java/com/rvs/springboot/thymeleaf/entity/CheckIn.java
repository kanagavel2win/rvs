package com.rvs.springboot.thymeleaf.entity;

import java.util.Set;

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
@Table(name = "checkin")

public class CheckIn {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int CheckInID;
	@Column
	private String Checkoutid;
	@Column
	private String StaffID;
	@Column
	private String CheckInDate;
	@Column
	private String AssetId;
	@Column
	private String Status;
	@Column
	private String ACondition;
	@Column
	private String Comments;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "CheckInID")
	private Set<CheckInFiles> checkInFiles;
	
}
