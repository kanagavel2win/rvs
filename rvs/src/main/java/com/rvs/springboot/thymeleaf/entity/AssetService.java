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
@Table(name = "assetservice")

public class AssetService {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int AssetServiceID;
	@Column
	private String assetId;
	@Column
	private String ServiceItem;
	@Column
	private String DueOneTime;
	@Column
	private String RepeatEveryno;
	@Column
	private String RepeatEverytype;
	@Column
	private String Beginning;
	@Column
	private String ShowReminderno;
	@Column
	private String ShowRemindertype;
	@Column
	private String Notes;
		
}
